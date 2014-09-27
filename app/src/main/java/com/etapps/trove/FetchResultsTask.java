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

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.etapps.trove.data.BookContract.BooksEntry;
import com.etapps.trove.data.BookContract.HoldingsEntry;

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

    /**
     * Helper method to handle insertion of a new location in the weather database.
     *
     * @param locationSetting The location string used to request updates from the server.
     * @param cityName A human-readable city name, e.g "Mountain View"
     * @param lat the latitude of the city
     * @param lon the longitude of the city
     * @return the row ID of the added location.
     */
    /*private long addLocation(String locationSetting, String cityName, double lat, double lon) {

        // First, check if the location with this city name exists in the db
        Cursor cursor = mContext.getContentResolver().query(
                LibrariesEntry.CONTENT_URI,
                new String[]{LibrariesEntry._ID},
                LibrariesEntry.COLUMN_LOCATION_SETTING + " = ?",
                new String[]{locationSetting},
                null);

        if (cursor.moveToFirst()) {
            int locationIdIndex = cursor.getColumnIndex(LibrariesEntry._ID);
            return cursor.getLong(locationIdIndex);
        } else {
            ContentValues locationValues = new ContentValues();
            locationValues.put(LibrariesEntry.COLUMN_LOCATION_SETTING, locationSetting);
            locationValues.put(LibrariesEntry.COLUMN_CITY_NAME, cityName);
            locationValues.put(LibrariesEntry.COLUMN_COORD_LAT, lat);
            locationValues.put(LibrariesEntry.COLUMN_COORD_LONG, lon);

            Uri locationInsertUri = mContext.getContentResolver()
                    .insert(LibrariesEntry.CONTENT_URI, locationValues);

            return ContentUris.parseId(locationInsertUri);
        }
    }*/

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

        // Get and insert the new weather information into the database
        Vector<ContentValues> cVVector = new Vector<ContentValues>(res);

        Vector<ContentValues> hVector = new Vector<ContentValues>(0);

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


            JSONArray holdingsArray = bookObj.getJSONArray(TRV_HOLDING);

            int len = holdingsArray.length();

            // Get and insert the new weather information into the database
            hVector = new Vector<ContentValues>(len);

            for (int j = 0; j < len; j++) {
                String urlb="";
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
                db2Values.put(HoldingsEntry.COLUMN_TROVE_KEY,id);
                db2Values.put(HoldingsEntry.COLUMN_URL,urlb);


                hVector.add(db2Values);

            }
            if (hVector.size() >0) {
                Log.v(LOG_TAG, "Hold NR "+hVector.size());
                ContentValues[] cvArray = new ContentValues[hVector.size()];
                hVector.toArray(cvArray);
                mContext.getContentResolver().bulkInsert(HoldingsEntry.CONTENT_URI, cvArray);
            }
            cVVector.add(dbValues);
        }
        if (cVVector.size() > 0) {
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);
            mContext.getContentResolver().delete(BooksEntry.CONTENT_URI, null, null);
            mContext.getContentResolver().bulkInsert(BooksEntry.CONTENT_URI, cvArray);
        }

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
        String include="holdings";
        String key = "dd539bfbq0hec6pq";
        int numRes = Utility.getResultsNr(mContext);
        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at TRV's forecast API page, at
            // http://openweathermap.org/API#forecast
            final String FORECAST_BASE_URL =
                    "http://api.trove.nla.gov.au/result?";
            final String KEY_PARAM = "key";
            final String QUERY_PARAM = "q";
            final String FORMAT_PARAM = "encoding";
            final String ZONE_PARAM = "zone";
            final String DAYS_PARAM = "n";
            final String INCLUDE_PARAM = "include";
            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendQueryParameter(KEY_PARAM, key)
                    .appendQueryParameter(QUERY_PARAM, query)
                    .appendQueryParameter(FORMAT_PARAM, format)
                    .appendQueryParameter(INCLUDE_PARAM, include)
                    .appendQueryParameter(ZONE_PARAM, zone)
                    .appendQueryParameter(DAYS_PARAM, Integer.toString(numRes))
                    .build();

            URL url = new URL(builtUri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
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
                buffer.append(line + "\n");
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

        try {
            getDataFromJson(resultJsonStr);
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
