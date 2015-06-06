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
package com.etapps.trovenla.tasks;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.etapps.trovenla.utils.Utility;
import com.etapps.trovenla.data.BookContract.BooksEntry;
import com.etapps.trovenla.data.BookContract.HoldingsEntry;
import com.etapps.trovenla.data.BookContract.LibrariesEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

public class FetchResultsTask extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = FetchResultsTask.class.getSimpleName();
    private final Context mContext;
    private ProgressDialog dialog;

    public FetchResultsTask(Context context) {
        mContext = context;
    }


    private void checkLibraries() {
        // retrieve the cursor with the holdings entries not in the libraries table as well

        String selection = HoldingsEntry.COLUMN_NUC + " NOT IN (select nuc from libraries)";

        Cursor cursor = mContext.getContentResolver().query(
                HoldingsEntry.CONTENT_URI,
                new String[]{HoldingsEntry.COLUMN_NUC},
                selection,
                null,
                "ASC");

        if (!cursor.moveToFirst()) {
            Log.v(LOG_TAG, "there is no missing libraries ");
        } else {
            try {
                addLibraries(cursor);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void addLibraries(Cursor nucEntries) throws JSONException {
        String format = "json";
        String key = "dd539bfbq0hec6pq";

        final String FORECAST_BASE_URL =
                "http://api.trove.nla.gov.au/contributor/";
        final String KEY_PARAM = "key";
        final String FORMAT_PARAM = "encoding";

        final String TRV_CONTRIBUTOR = "contributor";
        final String TRV_NAME = "name";

        String nuc;
        String baseUrl;
        String resultStr;
        String name;
        Uri builtUri;

        int i = nucEntries.getCount();
        Vector<ContentValues> libVector = new Vector<ContentValues>(i);
        //iterate the cursor until i have elements
        while (nucEntries.moveToNext()) {
            ContentValues libraryValues = new ContentValues();
            nuc = nucEntries.getString(1);
            //Log.v(LOG_TAG, "Nuc: " + nuc);
            baseUrl = FORECAST_BASE_URL + nuc + "?";
            builtUri = Uri.parse(baseUrl).buildUpon()
                    .appendQueryParameter(KEY_PARAM, key)
                    .appendQueryParameter(FORMAT_PARAM, format)
                    .build();
            resultStr = retrieveFromUrl(builtUri);
            JSONObject root = new JSONObject(resultStr);
            JSONObject contr = root.getJSONObject(TRV_CONTRIBUTOR);
            name = contr.optString(TRV_NAME);
            //Log.v(LOG_TAG, "Name: " + name);
            if (!name.equals("")) {
                libraryValues.put(LibrariesEntry.COLUMN_NUC, nuc);
                libraryValues.put(LibrariesEntry.COLUMN_LIBRARY_NAME, name);
                //libraryValues.put(LibrariesEntry.COLUMN_CITY, cityName);  future use
                //libraryValues.put(LibrariesEntry.COLUMN_URL, url);
                libVector.add(libraryValues);
            }
        }

        if (libVector.size() > 0) {
            ContentValues[] cvArray = new ContentValues[libVector.size()];
            libVector.toArray(cvArray);
            mContext.getContentResolver().bulkInsert(LibrariesEntry.CONTENT_URI, cvArray);
            Log.v(LOG_TAG, "added " + libVector.size() + " rows");
        }
        //return ContentUris.parseId(locationInsertUri);

    }

    private String retrieveFromUrl(Uri builtUri) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        // Will contain the raw JSON response as a string.
        String resultJsonStr = null;
        try {

            URL url = new URL(builtUri.toString());
            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line).append("\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            resultJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return resultJsonStr;
    }

    /**
     * Take the String representing the complete forecast in JSON Format and
     * pull out the data we need to construct the Strings needed for the wireframes.
     * <p/>
     * Fortunately parsing is easy:  constructor takes the JSON string and converts it
     * into an Object hierarchy for us.
     */
    private void getDataFromJson(String resultJsonStr)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.

        //Trove API
        final String TRV_RESPONSE = "response";
        final String TRV_ZONE = "zone";
        final String TRV_RECORDS = "records";
        final String TRV_WORK = "work";
        final String TRV_TITLE = "title";
        final String TRV_YEAR = "issued";
        final String TRV_URL = "troveUrl";
        final String TRV_CONTRIBUTOR = "contributor";
        final String TRV_ID = "id";
        final String TRV_HOLDINGS_NR = "holdingsCount";
        final String TRV_VERSIONS = "versionCount";
        final String TRV_URL_HOLDING = "url";
        final String TRV_URL_VALUE = "value";
        final String TRV_NUC = "nuc";
        final String TRV_HOLDING = "holding";

        JSONObject root = new JSONObject(resultJsonStr);
        JSONObject lev1 = root.getJSONObject(TRV_RESPONSE);
        JSONObject lev2 = lev1.getJSONArray(TRV_ZONE).getJSONObject(0);
        JSONObject lev3 = lev2.getJSONObject(TRV_RECORDS);

        JSONArray bookArray = lev3.getJSONArray(TRV_WORK);

        int res = bookArray.length();

        // Get and insert the new book information into the database
        Vector<ContentValues> cVVector = new Vector<ContentValues>(res);

        Vector<ContentValues> hVector = new Vector<ContentValues>(0);

        //I clean the holdings table before start the iteration otherwise it will be wiped for every new book entry
        mContext.getContentResolver().delete(HoldingsEntry.CONTENT_URI, null, null);

        for (int i = 0; i < res; i++) {
            // These are the values that will be collected.
            String url;
            String author;
            String title;
            String id;
            String year;
            String holdings;
            String versions;

            // Get the JSON object representing the single entry
            JSONObject bookObj = bookArray.getJSONObject(i);

            author = bookObj.optString(TRV_CONTRIBUTOR);
            url = bookObj.getString(TRV_URL);
            title = bookObj.getString(TRV_TITLE);
            id = bookObj.getString(TRV_ID);
            year = bookObj.optString(TRV_YEAR);
            holdings = bookObj.getString(TRV_HOLDINGS_NR);
            versions = bookObj.getString(TRV_VERSIONS);
            //cleaning of author string
            author = Utility.formatAuthor(author);
            //cleaning of title string
            title = Utility.formatTitle(title);

            ContentValues dbValues = new ContentValues();

            dbValues.put(BooksEntry.COLUMN_TROVE_KEY, id);
            dbValues.put(BooksEntry.COLUMN_BOOK_AUTHOR, author);
            dbValues.put(BooksEntry.COLUMN_BOOK_TITLE, title);
            dbValues.put(BooksEntry.COLUMN_URL, url);
            dbValues.put(BooksEntry.COLUMN_BOOK_YEAR, year);
            dbValues.put(BooksEntry.COLUMN_BOOK_HOLDINGS, holdings);
            dbValues.put(BooksEntry.COLUMN_BOOK_VERSIONS, versions);

            if (!holdings.equals("0")) {
                JSONArray holdingsArray = bookObj.getJSONArray(TRV_HOLDING);

                int len = holdingsArray.length();

                // Get and insert the new holdings information into the database
                hVector = new Vector<ContentValues>(len);

                for (int j = 0; j < len; j++) {
                    String urlb = "";
                    String nuc;

                    // Get the JSON object representing the single entry
                    JSONObject libObj = holdingsArray.getJSONObject(j);
                    nuc = libObj.optString(TRV_NUC);
                    if (libObj.toString().contains("url")) {
                        JSONObject urlObj = libObj.getJSONObject(TRV_URL_HOLDING);

                        urlb = urlObj.optString(TRV_URL_VALUE);
                    }
                    ContentValues db2Values = new ContentValues();

                    db2Values.put(HoldingsEntry.COLUMN_NUC, nuc);
                    db2Values.put(HoldingsEntry.COLUMN_TROVE_KEY, id);
                    db2Values.put(HoldingsEntry.COLUMN_URL, urlb);

                    hVector.add(db2Values);

                }
                if (hVector.size() > 0) {
                    //Log.v(LOG_TAG, "Hold Nr: " + hVector.size());
                    ContentValues[] cvArray = new ContentValues[hVector.size()];
                    hVector.toArray(cvArray);
                    mContext.getContentResolver().bulkInsert(HoldingsEntry.CONTENT_URI, cvArray);
                }
            }
            cVVector.add(dbValues);
        }
        if (cVVector.size() > 0) {
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);
            mContext.getContentResolver().delete(BooksEntry.CONTENT_URI, null, null);
            mContext.getContentResolver().bulkInsert(BooksEntry.CONTENT_URI, cvArray);
        }
        //at this point, i Have the tables populated with the new data
        //and I go to check if there are libraries not present in the Libraries table
        checkLibraries();
    }

    @Override
    protected Void doInBackground(String... params) {

        // If there's no zip code, there's nothing to look up.  Verify size of params.
        if (params.length == 0) {
            return null;
        }
        String query = params[0];

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String resultJsonStr = null;

        String format = "json";
        String zone = "book";
        String include = "holdings";
        String key = "dd539bfbq0hec6pq";
        int numRes = Utility.getResultsNr(mContext);
        // Construct the URL for the OpenWeatherMap query
        // Possible parameters are avaiable at TRV's forecast API page, at
        // http://openweathermap.org/API#forecast
        final String TROVE_BASE_URL =
                "http://api.trove.nla.gov.au/result?";
        final String KEY_PARAM = "key";
        final String QUERY_PARAM = "q";
        final String FORMAT_PARAM = "encoding";
        final String ZONE_PARAM = "zone";
        final String DAYS_PARAM = "n";
        final String INCLUDE_PARAM = "include";
        Uri builtUri = Uri.parse(TROVE_BASE_URL).buildUpon()
                .appendQueryParameter(KEY_PARAM, key)
                .appendQueryParameter(QUERY_PARAM, query)
                .appendQueryParameter(FORMAT_PARAM, format)
                .appendQueryParameter(INCLUDE_PARAM, include)
                .appendQueryParameter(ZONE_PARAM, zone)
                .appendQueryParameter(DAYS_PARAM, Integer.toString(numRes))
                .build();


        resultJsonStr = retrieveFromUrl(builtUri);

        try {
            if (resultJsonStr!=null) {
                getDataFromJson(resultJsonStr);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        // This will only happen if there was an error getting or parsing the forecast.
        return null;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(mContext, "", "Loading Results...", true);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        dialog.dismiss();
    }
}
