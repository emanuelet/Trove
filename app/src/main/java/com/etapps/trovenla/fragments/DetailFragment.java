/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.etapps.trovenla.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etapps.trovenla.R;
import com.etapps.trovenla.Trove;
import com.etapps.trovenla.activities.DetailActivity;
import com.etapps.trovenla.adapters.LibrariesAdapter;
import com.etapps.trovenla.db.Book;
import com.etapps.trovenla.db.Library;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {

    private static final String LOG_TAG = DetailFragment.class.getSimpleName();
    private static final String SHARE_HASHTAG = " #Trove";
    @Bind(R.id.detail_year_textview)
    TextView mYearView;
    @Bind(R.id.detail_year_textview_sublabel)
    TextView mYearViewSub;
    @Bind(R.id.detail_versions_textview)
    TextView mVersionsView;
    @Bind(R.id.detail_versions_textview_sublabel)
    TextView mVersionsViewSub;
    @Bind(R.id.detail_borrow_libraries_header)
    TextView mBorrowHeaderView;
    @Bind(R.id.dv1)
    View mDv1;
    @Bind(R.id.dv2)
    View mDv2;
    @Bind(R.id.libraries)
    RecyclerView mLibraries;
    private ShareActionProvider mShareActionProvider;
    private String mUrl;
    private String mUrlBuy;
    private String mKeyStr;
    private View rootView;
    private Context mContext;
    private Realm realm;
    private LibrariesAdapter adapter;
    private Book book;
    private ActionBar appbar;

    public DetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        realm = Realm.getInstance(mContext);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments != null) {
            mKeyStr = arguments.getString(DetailActivity.TROVE_KEY);
        }

        rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, rootView);

        book = realm.where(Book.class)
                .equalTo("id", mKeyStr)
                .findFirst();

        populateView();
        return rootView;
    }

    private void populateView() {
        mUrl = book.getUrl();
        String mUrlBorrow = mUrl + "?q=+&borrow=true";
        mUrlBuy = mUrl + "?q=+buy=true";
        String year = book.getIssued();
        if (year.equals("")) {
            mYearViewSub.setVisibility(View.GONE);
            mDv1.setVisibility(View.GONE);
        }
        mYearView.setText(year);

        long holdings = book.getHoldingsCount();
        if (holdings != 0) {
            mBorrowHeaderView.setText("Available to borrow at (" + holdings + "): ");
        } else {
            mBorrowHeaderView.setText("No Book available to borrow");
        }
        long versions = book.getVersionCount();
        if (versions != 0 && versions != 1) {
            mVersionsView.setText("" + versions);
        } else if (versions != 1) {
            mVersionsView.setText("");
            mVersionsViewSub.setVisibility(View.GONE);
            mDv2.setVisibility(View.GONE);
        } else mVersionsView.setText("No versions available");

        // If onCreateOptionsMenu has already happened, we need to update the share intent now.
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareBookLink());
        }
    }

    @OnClick(R.id.detail_buy_btn)
    void buybook() {
        Uri uri = Uri.parse(mUrlBuy);
        goToUrl(uri);
    }

    private void initList() {
        mLibraries.setLayoutManager(new LinearLayoutManager(mContext));
        RealmResults<Library> books = realm.where(Library.class).findAll();
        adapter = new LibrariesAdapter(mContext, books);
        mLibraries.setAdapter(adapter);
        adapter.SetOnItemClickListener(new LibrariesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Library item = adapter.getItematPosition(position);
                Uri uri = Uri.parse(item.getUrl());
                goToUrl(uri);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_go) {
            Uri uri = Uri.parse(mUrl);
            goToUrl(uri);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToUrl(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle arguments = getArguments();
        appbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (arguments != null && arguments.containsKey(DetailActivity.TWO_PANE)) {
            if (!arguments.containsKey(DetailActivity.TWO_PANE)) {
                appbar = ((DetailActivity) getActivity()).getSupportActionBar();
//                appbar.setTitle(title);
//                appbar.setSubtitle(author);
            }
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
        if (mUrl != null) {
            mShareActionProvider.setShareIntent(shareBookLink());
        }
    }

    private Intent shareBookLink() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mUrl + SHARE_HASHTAG);
        return shareIntent;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle arguments = getArguments();
    }


    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(String url, int position);
    }
}
