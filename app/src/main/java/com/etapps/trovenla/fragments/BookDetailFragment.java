package com.etapps.trovenla.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
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
        // TODO maybe use custom tabs ?
        Timber.d(url);
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(mContext, "The url is incorrect", Toast.LENGTH_SHORT).show();
        } else {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
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
        if (book.getUrl() != null) {
            mShareActionProvider.setShareIntent(shareBookLink());
        }
    }

    private Intent shareBookLink() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, book.getUrl() + SHARE_HASHTAG);
        return shareIntent;
    }
}
