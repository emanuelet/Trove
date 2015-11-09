package com.etapps.trovenla;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.etapps.trovenla.activities.DetailActivity;
import com.etapps.trovenla.activities.SettingsActivity;
import com.etapps.trovenla.api.TroveApi;
import com.etapps.trovenla.api.TroveRest;
import com.etapps.trovenla.data.SuggestionProvider;
import com.etapps.trovenla.db.Book;
import com.etapps.trovenla.db.Library;
import com.etapps.trovenla.fragments.DetailFragment;
import com.etapps.trovenla.fragments.ResultsFragment;
import com.etapps.trovenla.models.Contributor;
import com.etapps.trovenla.models.Libraries;
import com.etapps.trovenla.models.queries.Books;
import com.etapps.trovenla.models.queries.Holding;
import com.etapps.trovenla.models.queries.Work;
import com.etapps.trovenla.utils.Constants;
import com.etapps.trovenla.utils.PrefsUtils;
import com.etapps.trovenla.utils.Results;
import com.etapps.trovenla.utils.Utility;

import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity implements ResultsFragment.Callback {
    public static boolean libFetched = false;
    private final String LOG_TAG = MainActivity.class.getSimpleName();

    private boolean mTwoPane;
    private SearchView searchView;
    private Context mContext;
    private TroveApi rest;
    private Realm realm;
    private Results results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        if (findViewById(R.id.detail_container) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_container, new DetailFragment())
                        .commit();
            }
        } else {
            mTwoPane = false;
        }
        handleIntent(getIntent());

        ButterKnife.bind(this);
        rest = TroveRest.getAdapter(mContext, TroveApi.class);
        realm = Realm.getInstance(mContext);
        results = new Results(realm);

        ResultsFragment resultsFragment = ((ResultsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_forecast));

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
            if (libFetched) {
                searchView.requestFocus();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
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
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    SuggestionProvider.AUTHORITY, SuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            startSearch(query);
        }
        return false;
    }

    private void startSearch(String query) {
        //I first clear the book results table
        realm.beginTransaction();
        realm.clear(Book.class);
        realm.commitTransaction();
        rest.getBooks(
                Constants.KEY, Constants.FORMAT, Utility.getResultsNr(mContext), query, Constants.BOOKS, Constants.HOLDINGS,
                new Callback<Books>() {
                    @Override
                    public void success(Books books, Response response) {
                        results.addAll(books);
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
    }

    @Override
    public void onItemSelected(String title) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle args = new Bundle();
            args.putString(DetailActivity.TROVE_KEY, title);
            args.putBoolean(DetailActivity.TWO_PANE, true);
            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, DetailActivity.class)
                    .putExtra(DetailActivity.TROVE_KEY, title);
            startActivity(intent);
        }
    }
}