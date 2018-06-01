package com.etapps.trovenla.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.etapps.trovenla.R;
import com.etapps.trovenla.db.Book;
import com.etapps.trovenla.fragments.BookDetailFragment;
import com.etapps.trovenla.utils.Constants;
import com.google.firebase.analytics.FirebaseAnalytics;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import timber.log.Timber;

/**
 * An activity representing a single Book detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link BookListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link BookDetailFragment}.
 */
public class BookDetailActivity extends AppCompatActivity {

    private String mUrl;
    private FirebaseAnalytics mFirebaseAnalytics;
    private BookDetailActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        ButterKnife.bind(this);

        mContext= this;

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle arguments = new Bundle();
        arguments.putString(Constants.TROVE_KEY,
                getIntent().getStringExtra(Constants.TROVE_KEY));
        Realm realm = Realm.getDefaultInstance();
        Book mBook = realm.where(Book.class)
                .equalTo("id", getIntent().getStringExtra(Constants.TROVE_KEY))
                .findFirst();
        mUrl = mBook.getTroveUrl();
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, mBook.getId());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, mBook.getTitle());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            BookDetailFragment fragment = new BookDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.book_detail_container, fragment)
                    .commit();
        }
    }

    @OnClick(R.id.fab)
    public void buyBook() {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(mContext, Uri.parse(mUrl + "?q=+buy=true"));
        Bundle params = new Bundle();
        params.putString("url", mUrl);
        mFirebaseAnalytics.logEvent("buy_book", params);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, BookListActivity.class));
            return true;
        }
        Timber.d("menu "+id);
        return super.onOptionsItemSelected(item);
    }
}
