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
package com.etapps.trovenla;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.etapps.trovenla.adapters.LibrariesAdapter;
import com.etapps.trovenla.data.BookContract.BooksEntry;
import com.etapps.trovenla.data.BookContract.HoldingsEntry;
import com.etapps.trovenla.data.BookContract.LibrariesEntry;
import com.etapps.trovenla.tasks.GetCoverTask;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int COL_LIB_ID = 0;
    public static final int COL_LIB_NAME = 1;
    //public static final int COL_LIB_CITY = 3;
    public static final int COL_LIB_URL = 2;
    private static final String LOG_TAG = DetailFragment.class.getSimpleName();
    private static final String SHARE_HASHTAG = " #Trove";
    private static final int DETAIL_LOADER = 0;
    private static final String[] COLUMNS = {
            BooksEntry._ID,
            BooksEntry.COLUMN_TROVE_KEY,
            BooksEntry.COLUMN_BOOK_TITLE,
            BooksEntry.COLUMN_BOOK_AUTHOR,
            BooksEntry.COLUMN_BOOK_YEAR,
            BooksEntry.COLUMN_URL,
            BooksEntry.COLUMN_BOOK_HOLDINGS,
            BooksEntry.COLUMN_BOOK_VERSIONS
    };
    private static final int LIBRARIES_LOADER = 1;
    private static final String[] LIBRARIES_COLUMNS = {
            //LibrariesEntry.TABLE_NAME + "." + LibrariesEntry._ID,
            HoldingsEntry.TABLE_NAME + "." + HoldingsEntry._ID,
            LibrariesEntry.TABLE_NAME + "." + LibrariesEntry.COLUMN_LIBRARY_NAME,
            HoldingsEntry.TABLE_NAME + "." + HoldingsEntry.COLUMN_URL
    };
    private ShareActionProvider mShareActionProvider;

    private String mUrl;
    private String mUrlBuy;
    private String mKeyStr;

    private TextView mYearView;
    private TextView mYearViewSub;
    private TextView mVersionsView;
    private TextView mVersionsViewSub;
    private TextView mBorrowHeaderView;

    private View mDv1;
    private View mDv2;

    private GetCoverTask task;

    private View rootView;

    private LibrariesAdapter mLibrariesAdapter;
    private ListView mListView;

    public DetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //outState.putString(LOCATION_KEY, mLocation);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create an object for subclass of AsyncTask
        //task = new GetCoverTask();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments != null) {
            mKeyStr = arguments.getString(DetailActivity.TROVE_KEY);
        }

        mLibrariesAdapter = new LibrariesAdapter(getActivity(), null, 0);
        rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ImageButton mBtn_GoTo = (ImageButton) rootView.findViewById(R.id.action_go);
        ImageButton mBtn_Buy = (ImageButton) rootView.findViewById(R.id.detail_buy_btn);
        mYearView = (TextView) rootView.findViewById(R.id.detail_year_textview);
        mDv1 = (View) rootView.findViewById(R.id.dv1);
        mDv2 = (View) rootView.findViewById(R.id.dv2);
        mYearViewSub = (TextView) rootView.findViewById(R.id.detail_year_textview_sublabel);
        mVersionsView = (TextView) rootView.findViewById(R.id.detail_versions_textview);
        mVersionsViewSub = (TextView) rootView.findViewById(R.id.detail_versions_textview_sublabel);
        mBorrowHeaderView = (TextView) rootView.findViewById(R.id.detail_borrow_libraries_header);
        mListView = (ListView) rootView.findViewById(R.id.detail_borrow_libraries_listview);
        mListView.setAdapter(mLibrariesAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = mLibrariesAdapter.getCursor();
                if (cursor != null && cursor.moveToPosition(position)) {
                    ((DetailFragment.Callback) getActivity())
                            .onItemSelected(cursor.getString(COL_LIB_URL), position);
                }
            }
        });
        mBtn_Buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(mUrlBuy);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_go) {
            Uri uri = Uri.parse(mUrl);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(DetailActivity.TROVE_KEY)) {
            getLoaderManager().restartLoader(DETAIL_LOADER, null, this);
            getLoaderManager().restartLoader(LIBRARIES_LOADER, null, this);
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
            mShareActionProvider.setShareIntent(createShareForecastIntent());
        }
    }

    private Intent createShareForecastIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mUrl + SHARE_HASHTAG);
        return shareIntent;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if (savedInstanceState != null) {
//            //mLocation = savedInstanceState.getString(LOCATION_KEY);
//        }

        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(DetailActivity.TROVE_KEY)) {

            getLoaderManager().initLoader(DETAIL_LOADER, null, this);
            getLoaderManager().initLoader(LIBRARIES_LOADER, null, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        switch (id) {
            case 0:
                Uri keyUri = BooksEntry.buildBooksEntryfromId(mKeyStr);
                return new CursorLoader(
                        getActivity(),
                        keyUri,
                        COLUMNS,
                        null,
                        null,
                        null
                );
            case 1:
                Uri libkeyUri = HoldingsEntry.buildLibrariesfromId(mKeyStr);
                return new CursorLoader(
                        getActivity(),
                        libkeyUri,
                        LIBRARIES_COLUMNS,
                        null,
                        null,
                        null
                );
            default:
                return null;
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data != null && data.moveToFirst() && loader.getId() == 0) {
            //I retrieve all the data from selection of the content provider
            String title = data.getString(data.getColumnIndex(
                    BooksEntry.COLUMN_BOOK_TITLE));
            ((ActionBarActivity)getActivity()).getSupportActionBar().setTitle(title);
            String author = data.getString(data.getColumnIndex(
                    BooksEntry.COLUMN_BOOK_AUTHOR));
            String year = data.getString(data.getColumnIndex(
                    BooksEntry.COLUMN_BOOK_YEAR));
            String url = data.getString(data.getColumnIndex(
                    BooksEntry.COLUMN_URL));
            String holdings = data.getString(data.getColumnIndex(
                    BooksEntry.COLUMN_BOOK_HOLDINGS));
            String versions = data.getString(data.getColumnIndex(
                    BooksEntry.COLUMN_BOOK_VERSIONS));

            //if the title text is too long I shorten it otherwise it can occupy all the space
            if (title.length() >= 75) {
                title = title.substring(0, 75) + "...";
            }

            //TODO: find a workaround to retrieve the correct thumbnail for the cover
            String URL = "http://www.youthedesigner.com/wp-content/uploads/2010/03/beautiful-book-covers-67.jpg";
            // Execute the task
            //task.execute(URL);

            //and I set the views on that data
            Bundle arguments = getArguments();
            if (arguments != null && arguments.containsKey(DetailActivity.TWO_PANE)) {
                if (!arguments.containsKey(DetailActivity.TWO_PANE)) {
                    ((DetailActivity) getActivity()).getSupportActionBar().setTitle(
                            title);
                    ((DetailActivity) getActivity()).getSupportActionBar().setSubtitle(
                            author);
                }
            }


            if (year.equals("")) {
                mYearViewSub.setVisibility(View.GONE);
                mDv1.setVisibility(View.GONE);
            }
            mYearView.setText(year);
            mUrl = url;
            String mUrlBorrow = url + "?q=+&borrow=true";
            mUrlBuy = url + "?q=+buy=true";
            if (holdings != null && !holdings.equals("0")) {
                mBorrowHeaderView.setText("Available to borrow at (" + holdings + "): ");
            } else {
                mBorrowHeaderView.setText("No Book available to borrow");
            }
            if (versions != null && !versions.equals("0") && !versions.equals("1")) {
                mVersionsView.setText(versions);
            } else if (versions != null && versions.equals("1")) {
                mVersionsView.setText("");
                mVersionsViewSub.setVisibility(View.GONE);
                mDv2.setVisibility(View.GONE);
            } else mVersionsView.setText("No versions available");

            // If onCreateOptionsMenu has already happened, we need to update the share intent now.
            if (mShareActionProvider != null) {
                mShareActionProvider.setShareIntent(createShareForecastIntent());
            }
        }
        if (data != null && data.moveToFirst() && loader.getId() == 1) {
            int c = data.getCount();
            //Log.v(LOG_TAG, "libraries count: " + c);
            if (c > 0) {
                mLibrariesAdapter.swapCursor(data);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (loader.getId() == 1) {
            mLibrariesAdapter.swapCursor(null);
        }
    }

    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(String url, int position);
    }
}
