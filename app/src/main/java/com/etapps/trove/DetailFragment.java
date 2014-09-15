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
package com.etapps.trove;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.etapps.trove.data.BookContract.WeatherEntry;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, OnClickListener {

    private static final String LOG_TAG = DetailFragment.class.getSimpleName();

    private static final String SHARE_HASHTAG = " #Trove";
    private static final int DETAIL_LOADER = 0;
    private static final String[] COLUMNS = {
            WeatherEntry._ID,
            WeatherEntry.COLUMN_TROVE_KEY,
            WeatherEntry.COLUMN_BOOK_TITLE,
            WeatherEntry.COLUMN_BOOK_AUTHOR,
            WeatherEntry.COLUMN_BOOK_YEAR,
            WeatherEntry.COLUMN_URL,
            WeatherEntry.COLUMN_BOOK_HOLDINGS,
            WeatherEntry.COLUMN_BOOK_VERSIONS
    };
    private ShareActionProvider mShareActionProvider;
    private String mUrl;
    private String mUrlBorrow;
    private String mUrlBuy;
    private String mKeyStr;
    private ImageButton mBtn_GoTo;
    private ImageView mIconView;
    private TextView mTitleView;
    private TextView mAuthorView;
    private TextView mYearView;
    private TextView mVersionsView;

    private Button mBtn_Borrow;
    private Button mBtn_Buy;
    private GetCoverTask task;

    private View rootView;

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
        task = new GetCoverTask();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments != null) {
            mKeyStr = arguments.getString(DetailActivity.TROVE_KEY);
        }

        if (savedInstanceState != null) {
            //mLocation = savedInstanceState.getString(LOCATION_KEY);
        }


        rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        mBtn_GoTo = (ImageButton) rootView.findViewById(R.id.btn_goto);
        mBtn_Borrow = (Button) rootView.findViewById(R.id.btn_borrow);
        mBtn_Buy = (Button) rootView.findViewById(R.id.btn_buy);
        mBtn_GoTo.setOnClickListener(this);
        mBtn_Borrow.setOnClickListener(this);
        mBtn_Buy.setOnClickListener(this);
        mIconView = (ImageView) rootView.findViewById(R.id.detail_cover);
        mTitleView = (TextView) rootView.findViewById(R.id.detail_title_textview);
        mAuthorView = (TextView) rootView.findViewById(R.id.detail_author_textview);
        mYearView = (TextView) rootView.findViewById(R.id.detail_year_textview);
        mVersionsView = (TextView) rootView.findViewById(R.id.detail_versions_textview);
        return rootView;
    }

    @Override
    public void onResume() {
        Log.v(LOG_TAG, "onResume");
        super.onResume();
        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(DetailActivity.TROVE_KEY)) {
            getLoaderManager().restartLoader(DETAIL_LOADER, null, this);
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
        if (savedInstanceState != null) {
            //mLocation = savedInstanceState.getString(LOCATION_KEY);
        }

        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(DetailActivity.TROVE_KEY)) {
            getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri keyUri = WeatherEntry.buildBooksEntryfromId(mKeyStr);
        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return new CursorLoader(
                getActivity(),
                keyUri,
                COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data != null && data.moveToFirst() && mTitleView != null) {
            //mIconView.setImageResource(Utility.getArtResourceForWeatherCondition(weatherId));


            //I retrieve all the data from selection of the content provider
            String title = data.getString(data.getColumnIndex(
                    WeatherEntry.COLUMN_BOOK_TITLE));
            String author = data.getString(data.getColumnIndex(
                    WeatherEntry.COLUMN_BOOK_AUTHOR));
            String year = data.getString(data.getColumnIndex(
                    WeatherEntry.COLUMN_BOOK_YEAR));
            String url = data.getString(data.getColumnIndex(
                    WeatherEntry.COLUMN_URL));
            String holdings = data.getString(data.getColumnIndex(
                    WeatherEntry.COLUMN_BOOK_HOLDINGS));
            String versions = data.getString(data.getColumnIndex(
                    WeatherEntry.COLUMN_BOOK_VERSIONS));

            //if the title text is too long I shorten it otherwise it can occupy all the space
            if (title.length() >= 60) {
                title = title.substring(0, 60) + "...";
            }

            //TODO: find a workaround to retrieve the correct thumbnail for the cover
            String URL = "http://www.youthedesigner.com/wp-content/uploads/2010/03/beautiful-book-covers-67.jpg";
            // Execute the task
            //task.execute(URL);

            //and I set the views on that data
            mTitleView.setText(title);
            mAuthorView.setText(author);
            mYearView.setText(year);
            mUrl = url;
            mUrlBorrow = url + "?q=+&borrow=true";
            mUrlBuy = url + "?q=+buy=true";
            if (holdings != null) {
                mBtn_Borrow.setText("Borrow (" + holdings + ")");
            }
            if (versions != null) {
                mVersionsView.setText(versions + " versions available");
            }

            // For accessibility, add a content description to the icon field
            mIconView.setContentDescription(title + " by " + author);


            // If onCreateOptionsMenu has already happened, we need to update the share intent now.
            if (mShareActionProvider != null) {
                mShareActionProvider.setShareIntent(createShareForecastIntent());
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    public void onClick(View v) {
        if (v == mBtn_GoTo) {
            Uri uri = Uri.parse(mUrl);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
        if (v == mBtn_Borrow) {
            Uri uri = Uri.parse(mUrlBorrow);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
        if (v == mBtn_Buy) {
            Uri uri = Uri.parse(mUrlBuy);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    private class GetCoverTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            //add here the retrieve of the additional data?
            return map;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            mIconView.setImageBitmap(result);
        }

        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
    }
}
