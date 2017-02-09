package com.etapps.trovenla.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.etapps.trovenla.R;
import com.etapps.trovenla.adapters.NavSpinnerAdapter;
import com.etapps.trovenla.api.TroveApi;
import com.etapps.trovenla.api.TroveRest;
import com.etapps.trovenla.db.Book;
import com.etapps.trovenla.fragments.BookDetailFragment;
import com.etapps.trovenla.fragments.BookListFragment;
import com.etapps.trovenla.models.Suggestion;
import com.etapps.trovenla.models.libraries.Libraries;
import com.etapps.trovenla.models.queries.Books;
import com.etapps.trovenla.utils.Constants;
import com.etapps.trovenla.utils.PrefsUtils;
import com.etapps.trovenla.utils.Results;
import com.etapps.trovenla.utils.Utility;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * An activity representing a list of Results. This activity
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
        implements BookListFragment.Callbacks {

    private static final String TAG = "Book List";
    public static final String BOOKS = "Books";
    public static final String ARTICLES = "Articles";
    public static final String PICTURES = "Pictures";

    @Bind(R.id.spinner_nav)
    Spinner nav;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private SearchView searchView;
    private Realm realm;
    private TroveApi rest;
    private Context mContext;
    private boolean isFetching;
    private Results results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_app_bar);

        mContext = getApplicationContext();

        ButterKnife.bind(this);

        initToolbar();

        rest = TroveRest.getAdapter(mContext, TroveApi.class);
        realm = Realm.getInstance(mContext);
        results = new Results(realm);

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
            rest.getLibraries(Constants.KEY, Constants.FORMAT, Constants.RECLEVEL, new Callback<Libraries>() {
                @Override
                public void success(Libraries libraries, Response response) {
                    results.addLibraries(libraries);
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

            PrefsUtils.firstStart(mContext);
        }
        // TODO: If exposing deep links into your app, handle intents here.
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ArrayList<String> list = new ArrayList<String>();
        list.add(BOOKS);
        list.add(ARTICLES);
        list.add(PICTURES);

        final NavSpinnerAdapter spinAdapter = new NavSpinnerAdapter(mContext, list);

        nav.setAdapter(spinAdapter);

        nav.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                switch (item) {
                    case BOOKS:
                        break;
                    case ARTICLES:
                        break;
                    case PICTURES:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
        }
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
//            if (libFetched) {
            searchView.requestFocus();
//            }
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
                    Suggestion s = realm.createObject(Suggestion.class);
                    s.setQuery(query);
                }
            });
            startSearch(query);
        }
        return false;
    }

    private void startSearch(String query) {
        if (!isFetching) {
            //I first clear the book results table
            isFetching = true;
            realm.beginTransaction();
            realm.clear(Book.class);
            realm.commitTransaction();
            rest.getContent(Constants.KEY, Constants.FORMAT, Utility.getResultsNr(mContext), query, Constants.BOOKS, Constants.HOLDINGS,
                    new Callback<Books>() {
                        @Override
                        public void success(Books books, Response response) {
                            results.addAll(books);
                            isFetching = false;
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            isFetching = false;
                        }
                    });
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
}
