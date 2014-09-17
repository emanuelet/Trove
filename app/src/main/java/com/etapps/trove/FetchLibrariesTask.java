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

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.etapps.trove.data.BookContract.LibrariesEntry;

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

public class FetchLibrariesTask extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = FetchLibrariesTask.class.getSimpleName();
    private final Context mContext;

    public FetchLibrariesTask(Context context) {
        mContext = context;
    }


    private long addLocation(String nuc, String cityName, String name, String url) {

        // First, check if the location with this city name exists in the db
        Cursor cursor = mContext.getContentResolver().query(
                LibrariesEntry.CONTENT_URI,
                new String[]{LibrariesEntry._ID},
                LibrariesEntry.COLUMN_NUC + " = ?",
                new String[]{nuc},
                null);

        if (cursor.moveToFirst()) {
            int libIndex = cursor.getColumnIndex(LibrariesEntry._ID);
            return cursor.getLong(libIndex);
        } else {
            ContentValues libraryValues = new ContentValues();
            libraryValues.put(LibrariesEntry.COLUMN_NUC, nuc);
            libraryValues.put(LibrariesEntry.COLUMN_LIBRARY_NAME, name);
            libraryValues.put(LibrariesEntry.COLUMN_CITY, cityName);
            libraryValues.put(LibrariesEntry.COLUMN_URL, url);

            Uri locationInsertUri = mContext.getContentResolver()
                    .insert(LibrariesEntry.CONTENT_URI, libraryValues);

            return ContentUris.parseId(locationInsertUri);
        }
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
        //TODO:refactor fro the work response
        final String TRV_WORK = "work";
        final String TRV_URL = "url";
        final String TRV_URL_VALUE = "value";
        final String TRV_NUC = "nuc";
        final String TRV_HOLDINGS = "holding";

        JSONObject root = new JSONObject(resultJsonStr);
        JSONObject lev1 = root.getJSONObject(TRV_WORK);


        JSONArray holdingsArray = lev1.getJSONArray(TRV_HOLDINGS);

        int res = holdingsArray.length();

        // Get and insert the new weather information into the database
        Vector<ContentValues> cVVector = new Vector<ContentValues>(res);

        for (int i = 0; i < res; i++) {
            // These are the values that will be collected.
            String url="";
            String nuc;
            //String city;
            //String libName;

            // Get the JSON object representing the single entry
            JSONObject libObj = holdingsArray.getJSONObject(i);

            nuc = libObj.optString(TRV_NUC);
            if (libObj.toString().contains("url")) {
                JSONObject urlObj = libObj.getJSONObject(TRV_URL);

                url = urlObj.optString(TRV_URL_VALUE);
            }
            Log.v(LOG_TAG,"obj: "+nuc+" / "+ url);
            ContentValues dbValues = new ContentValues();

            dbValues.put(LibrariesEntry.COLUMN_NUC, nuc);
            //dbValues.put(LibrariesEntry.COLUMN_LIBRARY_NAME, );
            //dbValues.put(LibrariesEntry.COLUMN_CITY, );
            dbValues.put(LibrariesEntry.COLUMN_URL, url);


            cVVector.add(dbValues);
        }
        if (cVVector.size() > 0) {
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);
            mContext.getContentResolver().delete(LibrariesEntry.CONTENT_URI, null, null);
            mContext.getContentResolver().bulkInsert(LibrariesEntry.CONTENT_URI, cvArray);
        }
    }

    @Override
    protected Void doInBackground(String... params) {

        // If there's no zip code, there's nothing to look up.  Verify size of params.
        if (params.length == 0) {
            return null;
        }
        String trove_id = params[0];

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String resultJsonStr = null;

        String format = "json";
        String reclevel="full";
        String include="holdings";
        String key = "dd539bfbq0hec6pq";
        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at TRV's forecast API page, at
            // http://openweathermap.org/API#forecast
            final String FORECAST_BASE_URL =
                    "http://api.trove.nla.gov.au/work/"+trove_id+"?";
            final String KEY_PARAM = "key";
            final String INCLUDE_PARAM = "include";
            final String FORMAT_PARAM = "encoding";
            final String RECLEVEL_PARAM = "reclevel";
            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendQueryParameter(KEY_PARAM, key)
                    .appendQueryParameter(FORMAT_PARAM, format)
                    .appendQueryParameter(INCLUDE_PARAM, include)
                    .appendQueryParameter(RECLEVEL_PARAM, reclevel)
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
}
