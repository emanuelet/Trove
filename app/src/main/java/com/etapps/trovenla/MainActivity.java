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
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.etapps.trovenla.activities.DetailActivity;
import com.etapps.trovenla.activities.SettingsActivity;
import com.etapps.trovenla.data.SuggestionProvider;
import com.etapps.trovenla.tasks.FetchLibrariesTask;
import com.etapps.trovenla.tasks.FetchResultsTask;


public class MainActivity extends AppCompatActivity implements ResultsFragment.Callback {
    public static boolean libFetched = false;
    private final String LOG_TAG = MainActivity.class.getSimpleName();

    private boolean mTwoPane;
    private SearchView searchView;

    private TextView mHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
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
        mHeader = (TextView) findViewById(R.id.list_header);
        TextView mEmpty = (TextView) findViewById(R.id.empty);
        ResultsFragment resultsFragment = ((ResultsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_forecast));
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean firstStart = settings.getBoolean("firstStart", true);

        if (firstStart) {
            new FetchLibrariesTask(this).execute();

            mHeader.setVisibility(View.INVISIBLE);
            mEmpty.setText("To Start Searching hit the Search button above.");
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("firstStart", false);
            editor.apply();
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
            new FetchResultsTask(this).execute(query);
            if (mHeader.getVisibility() == View.INVISIBLE) {
                mHeader.setVisibility(View.VISIBLE);
            }
        }
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    SuggestionProvider.AUTHORITY, SuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            new FetchResultsTask(this).execute(query);
            if (mHeader.getVisibility() == View.INVISIBLE) {
                mHeader.setVisibility(View.VISIBLE);
            }
        }
        return false;
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