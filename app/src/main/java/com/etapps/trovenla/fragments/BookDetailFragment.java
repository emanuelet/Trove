package com.etapps.trovenla.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.ShareActionProvider;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.etapps.trovenla.R;
import com.etapps.trovenla.activities.BookDetailActivity;
import com.etapps.trovenla.activities.BookListActivity;
import com.etapps.trovenla.adapters.LibrariesAdapter;
import com.etapps.trovenla.db.Book;
import com.etapps.trovenla.db.Library;
import com.etapps.trovenla.utils.Constants;
import com.google.firebase.analytics.FirebaseAnalytics;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;
import timber.log.Timber;

/**
 * A fragment representing a single Book detail screen.
 * This fragment is either contained in a {@link BookListActivity}
 * in two-pane mode (on tablets) or a {@link BookDetailActivity}
 * on handsets.
 */
public class BookDetailFragment extends Fragment {
    private static final String SHARE_HASHTAG = " #Trove";

    private Context mContext;
    private Realm realm;
    private String mKeyStr;
    private Book book;

    @BindView(R.id.libraries)
    RecyclerView mLibraries;
    @BindView(R.id.detail_title)
    TextView mTitle;
    @BindView(R.id.detail_author)
    TextView mAuthor;
    @BindView(R.id.detail_date)
    TextView mDate;

    private LibrariesAdapter adapter;
    private ShareActionProvider mShareActionProvider;
    private FirebaseAnalytics mFirebaseAnalytics;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BookDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
        realm = Realm.getDefaultInstance();

        if (getArguments().containsKey(Constants.TROVE_KEY)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mKeyStr = getArguments().getString(Constants.TROVE_KEY);

            book = realm.where(Book.class)
                    .equalTo("id", mKeyStr)
                    .findFirst();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_book_detail, container, false);

        ButterKnife.bind(this, rootView);

        // Show the dummy content as text in a TextView.
        if (mKeyStr != null) {
            populateView();

            initList(mKeyStr);
            initObjs();
        }

        return rootView;
    }

    private void initObjs() {
        mTitle.setText(book.getTitle());
        mAuthor.setText(book.getContributor());
        mDate.setText(book.getIssued());
    }

    private void populateView() {
        // If onCreateOptionsMenu has already happened, we need to update the share intent now.
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareBookLink());
        }
    }

    private void initList(String mKeyStr) {
        mLibraries.setLayoutManager(new LinearLayoutManager(mContext));
        RealmList<Library> libraries = realm.where(Book.class)
                .equalTo("id", mKeyStr)
                .findFirst().getLibraries();

        adapter = new LibrariesAdapter(mContext, libraries.where()
                .isNotNull("name").findAll());
        mLibraries.setAdapter(adapter);
        adapter.SetOnItemClickListener(new LibrariesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Library item = adapter.getItematPosition(position);

                goToUrl(item.getUrlHolding());
            }
        });
    }

    private void goToUrl(String url) {
        Timber.d(url);
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(mContext, "The url is incorrect", Toast.LENGTH_SHORT).show();
        } else {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(mContext, Uri.parse(url));
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, book.getTitle());
            bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "library-book");
            bundle.putString(FirebaseAnalytics.Param.ITEM_LOCATION_ID, url);
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_go) {
            goToUrl(book.getTroveUrl());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.detailfragment, menu);

        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.action_share);

        // Get the provider and hold onto it to set/change the share intent.
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        // If onLoadFinished happens before this, we can go ahead and set the share intent now.
        if (book.getTroveUrl() != null) {
            mShareActionProvider.setShareIntent(shareBookLink());
        }
    }

    private Intent shareBookLink() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, book.getTroveUrl() + SHARE_HASHTAG);
        return shareIntent;
    }
}
