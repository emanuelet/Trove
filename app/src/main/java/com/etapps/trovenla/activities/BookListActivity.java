package com.etapps.trovenla.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.etapps.trovenla.R;
import com.etapps.trovenla.adapters.ViewPagerAdapter;
import com.etapps.trovenla.api.TroveApi;
import com.etapps.trovenla.api.TroveRest;
import com.etapps.trovenla.fragments.BookDetailFragment;
import com.etapps.trovenla.fragments.BookListFragment;
import com.etapps.trovenla.fragments.NewspapersListFragment;
import com.etapps.trovenla.fragments.PicturesListFragment;
import com.etapps.trovenla.models.Suggestion;
import com.etapps.trovenla.models.libraries.Libraries;
import com.etapps.trovenla.models.newspapers.Newspaper;
import com.etapps.trovenla.models.queries.Books;
import com.etapps.trovenla.utils.Constants;
import com.etapps.trovenla.utils.DbTranslator;
import com.etapps.trovenla.utils.PrefsUtils;
import com.etapps.trovenla.utils.Utility;
import com.google.firebase.analytics.FirebaseAnalytics;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * An activity representing a list of DbTranslator. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link BookDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link BookListFragment} and the item details
 * (if present) is a {@link BookDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link BookListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class BookListActivity extends AppCompatActivity
        implements BookListFragment.Callbacks, NewspapersListFragment.Callbacks, PicturesListFragment.Callbacks {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.loading)
    ProgressBar loading;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private FirebaseAnalytics mFirebaseAnalytics;
    private boolean mTwoPane;
    private SearchView searchView;
    private Realm realm;
    private Context mContext;
    private boolean isFetching;
    private DbTranslator dbTranslator;
    private TroveApi api;
    private String searchType = Constants.BOOKS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_app_bar);

        mContext = getApplicationContext();

        ButterKnife.bind(this);

        initToolbar();

        api = TroveRest.getAdapter(TroveApi.class);
        realm = Realm.getDefaultInstance();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, null);
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);

        dbTranslator = new DbTranslator(realm);

        if (findViewById(R.id.book_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
//            ((BookListFragment) getSupportFragmentManager()
//                    .findFragmentById(R.id.book_list));
        }

        if (PrefsUtils.isFirstStart(mContext)) {
            Call<Libraries> call = api.getLibraries(Constants.KEY, Constants.FORMAT, Constants.RECLEVEL);
            call.enqueue(new retrofit2.Callback<Libraries>() {
                @Override
                public void onResponse(Call<Libraries> call, retrofit2.Response<Libraries> response) {
                    if (response.isSuccessful()) {
                        dbTranslator.addLibraries(response.body());
                        PrefsUtils.firstStart(mContext);
                    } else {
                        Timber.e(response.message());
                    }
                }

                @Override
                public void onFailure(Call<Libraries> call, Throwable t) {
                    Timber.e(t);
                }
            });
        }
        // TODO: If exposing deep links into your app, handle intents here.

        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BookListFragment());
        adapter.addFragment(new NewspapersListFragment());
        adapter.addFragment(new PicturesListFragment());
        viewPager.setAdapter(adapter);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_books:
                                viewPager.setCurrentItem(0);
                                searchType = Constants.BOOKS;
                                break;
                            case R.id.action_newspapers:
                                viewPager.setCurrentItem(1);
                                searchType = Constants.NEWSPAPERS;
                                break;
                            case R.id.action_pictures:
                                viewPager.setCurrentItem(2);
                                searchType = Constants.PICTURES;
                                break;
                        }
                        return true;
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
        searchView.setIconifiedByDefault(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        if (id == R.id.action_search) {
            searchView.requestFocus();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }


    public boolean handleIntent(Intent intent) {
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchView.setQuery(query, false);
            startSearch(query);
        }
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            final String query = intent.getStringExtra(SearchManager.QUERY);
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Suggestion s = new Suggestion();
                    s.setQuery(query);
                }
            });
            startSearch(query);
        }
        return false;
    }

    private void startSearch(String query) {
        if (!isFetching) {
            //I first clear the book dbTranslator table
            isFetching = true;
            loading.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.GONE);
            switch (searchType) {
                case Constants.BOOKS:
                    Call<Books> call = api.getContent(Constants.KEY, Constants.FORMAT, Utility.getResultsNr(mContext), query, Constants.BOOK, Constants.HOLDINGS);
                    call.enqueue(new retrofit2.Callback<Books>() {
                        @Override
                        public void onResponse(Call<Books> call, retrofit2.Response<Books> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getResponse().getZone().get(0).getRecords().getWork() != null) {
                                    Timber.d(response.raw().request().url().toString());
                                    dbTranslator.addBooks(response.body());
                                } else {
                                    Toast.makeText(BookListActivity.this, "The query returned no results.", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Timber.e(response.message());
                            }
                            loading.setVisibility(View.GONE);
                            viewPager.setVisibility(View.VISIBLE);
                            isFetching = false;
                        }

                        @Override
                        public void onFailure(Call<Books> call, Throwable t) {
                            Timber.e(t);
                            loading.setVisibility(View.GONE);
                            viewPager.setVisibility(View.VISIBLE);
                            Toast.makeText(BookListActivity.this, "We encountered an error", Toast.LENGTH_SHORT).show();
                            isFetching = false;
                        }
                    });
                    break;
                case Constants.NEWSPAPERS:
                    Call<Newspaper> call2 = api.getNewspapers(Constants.KEY, Constants.FORMAT, Utility.getResultsNr(mContext), query, Constants.NEWSPAPER, Constants.ARTICLE);
                    call2.enqueue(new Callback<Newspaper>() {
                        @Override
                        public void onResponse(Call<Newspaper> call, Response<Newspaper> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getResponse().getZone().get(0).getRecords().getArticle() != null) {
                                    Timber.d(response.raw().request().url().toString());
                                    dbTranslator.addNewspapers(response.body());
                                } else {
                                    Toast.makeText(BookListActivity.this, "The query returned no results.", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Timber.e(response.message());
                            }
                            loading.setVisibility(View.GONE);
                            viewPager.setVisibility(View.VISIBLE);
                            isFetching = false;
                        }

                        @Override
                        public void onFailure(Call<Newspaper> call, Throwable t) {
                            Timber.e(t);
                            loading.setVisibility(View.GONE);
                            viewPager.setVisibility(View.VISIBLE);
                            Toast.makeText(BookListActivity.this, "We encountered an error", Toast.LENGTH_SHORT).show();
                            isFetching = false;
                        }
                    });
                    break;
                case Constants.PICTURES:
                    Call<Books> call3 = api.getPictures(Constants.KEY, Constants.FORMAT, Utility.getResultsNr(mContext), query, Constants.PICTURE, Constants.PHOTOGRAPH);
                    call3.enqueue(new Callback<Books>() {
                        @Override
                        public void onResponse(Call<Books> call, Response<Books> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getResponse().getZone().get(0).getRecords().getWork() != null) {
                                    Timber.d(response.raw().request().url().toString());
                                    dbTranslator.addPictures(response.body());
                                } else {
                                    Toast.makeText(BookListActivity.this, "The query returned no results.", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Timber.e(response.message());
                            }
                            loading.setVisibility(View.GONE);
                            viewPager.setVisibility(View.VISIBLE);
                            isFetching = false;
                        }

                        @Override
                        public void onFailure(Call<Books> call, Throwable t) {
                            Timber.e(t);
                            loading.setVisibility(View.GONE);
                            viewPager.setVisibility(View.VISIBLE);
                            Toast.makeText(BookListActivity.this, "We encountered an error", Toast.LENGTH_SHORT).show();
                            isFetching = false;
                        }
                    });
                    break;
            }
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.SEARCH_TERM, query);
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH, bundle);
        }
    }

    /**
     * Callback method from {@link BookListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(Constants.TROVE_KEY, id);
            BookDetailFragment fragment = new BookDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.book_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, BookDetailActivity.class);
            detailIntent.putExtra(Constants.TROVE_KEY, id);
            startActivity(detailIntent);
        }
    }

    @Override
    public void onArticleSelected(String id) {
        Intent detailIntent = new Intent(this, NewspapersArticleActivity.class);
        detailIntent.putExtra(Constants.TROVE_KEY, id);
        startActivity(detailIntent);
    }

    @Override
    public void onPictureSelected(String id) {

    }
}
