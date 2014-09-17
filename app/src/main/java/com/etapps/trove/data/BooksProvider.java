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
package com.etapps.trove.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class BooksProvider extends ContentProvider {

    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final int BOOKS = 100;
    private static final int BOOKS_WITH_ID = 101;
    private static final int WEATHER_WITH_LOCATION_AND_DATE = 102;
    private static final int LIBRARY = 300;
    private static final int LIBRARY_ID = 301;
    private static final SQLiteQueryBuilder sBookbyIdQueryBuilder;
    static{
        sBookbyIdQueryBuilder = new SQLiteQueryBuilder();
        sBookbyIdQueryBuilder.setTables(
                BookContract.BooksEntry.TABLE_NAME);
    }
    private static final String sTroveIdSelection =
            BookContract.BooksEntry.TABLE_NAME+
                    "." + BookContract.BooksEntry.COLUMN_TROVE_KEY + " = ? ";
/*

    private static final String sLocationSettingWithStartDateSelection =
            BookContract.LibrariesEntry.TABLE_NAME+
                    "." + BookContract.LibrariesEntry.COLUMN_LOCATION_SETTING + " = ? AND " +
                    BookContract.BooksEntry.COLUMN_DATETEXT + " >= ? ";

    private static final String sLocationSettingAndDaySelection =
            BookContract.LibrariesEntry.TABLE_NAME +
                    "." + BookContract.LibrariesEntry.COLUMN_LOCATION_SETTING + " = ? AND " +
                    BookContract.BooksEntry.COLUMN_DATETEXT + " = ? ";*/
    private BooksDbHelper mOpenHelper;

    private static UriMatcher buildUriMatcher() {
        // I know what you're thinking.  Why create a UriMatcher when you can use regular
        // expressions instead?  Because you're not crazy, that's why.

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = BookContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, BookContract.PATH_BOOKS, BOOKS);
        matcher.addURI(authority, BookContract.PATH_BOOKS + "/*", BOOKS_WITH_ID);
        matcher.addURI(authority, BookContract.PATH_BOOKS + "/*/*", WEATHER_WITH_LOCATION_AND_DATE);
        matcher.addURI(authority, BookContract.PATH_LIBRARIES, LIBRARY);
        matcher.addURI(authority, BookContract.PATH_LIBRARIES + "/*", LIBRARY_ID);
        return matcher;
    }

    private Cursor getBookbyId(Uri uri, String[] projection, String sortOrder) {
        String locationSetting = BookContract.BooksEntry.getLocationSettingFromUri(uri);
        String[] selectionArgs=new String[]{locationSetting};
        String selection= sTroveIdSelection;

        /*if (key == null) {
            selection = sTroveIdSelection;
            selectionArgs = new String[]{locationSetting};
        } else {
            selectionArgs = new String[]{locationSetting, key};
            selection = sLocationSettingWithStartDateSelection;
        }*/

        return sBookbyIdQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getWeatherByLocationSettingAndDate(
            Uri uri, String[] projection, String sortOrder) {
        String locationSetting = BookContract.BooksEntry.getLocationSettingFromUri(uri);
        String date = BookContract.BooksEntry.getDateFromUri(uri);

        /*return sBookbyIdQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                sLocationSettingAndDaySelection,
                new String[]{locationSetting, date},
                null,
                null,
                sortOrder
        );*/
        return null;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new BooksDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            // "weather/*/*"
            case WEATHER_WITH_LOCATION_AND_DATE:
            {
                Log.v("query","1");
                retCursor = getWeatherByLocationSettingAndDate(uri, projection, sortOrder);
                break;
            }
            // "weather/*"
            case BOOKS_WITH_ID: {

                retCursor = getBookbyId(uri, projection, sortOrder);
                break;
            }
            // "weather"
            case BOOKS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        BookContract.BooksEntry.TABLE_NAME,
                        null,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            // "location/*"
            case LIBRARY_ID: {
                Log.v("query","4");
                retCursor = mOpenHelper.getReadableDatabase().query(
                        BookContract.LibrariesEntry.TABLE_NAME,
                        projection,
                        BookContract.LibrariesEntry._ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            // "location"
            case LIBRARY: {
                Log.v("query libraries",projection+" / "+selection);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        BookContract.LibrariesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            default: {
                retCursor=null;
                Log.e("query","Unknown uri: " + uri);
            }
        }
        if (retCursor != null) {
            retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case WEATHER_WITH_LOCATION_AND_DATE:
                return BookContract.BooksEntry.CONTENT_ITEM_TYPE;
            case BOOKS_WITH_ID:
                return BookContract.BooksEntry.CONTENT_TYPE;
            case BOOKS:
                return BookContract.BooksEntry.CONTENT_TYPE;
            case LIBRARY:
                return BookContract.LibrariesEntry.CONTENT_TYPE;
            case LIBRARY_ID:
                return BookContract.LibrariesEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case BOOKS: {
                long _id = db.insert(BookContract.BooksEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = BookContract.BooksEntry.buildWeatherUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case LIBRARY: {
                long _id = db.insert(BookContract.LibrariesEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = BookContract.LibrariesEntry.buildLibrariesUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        switch (match) {
            case BOOKS:
                rowsDeleted = db.delete(
                        BookContract.BooksEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case LIBRARY:
                rowsDeleted = db.delete(
                        BookContract.LibrariesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (selection == null || rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(
            Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case BOOKS:
                rowsUpdated = db.update(BookContract.BooksEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case LIBRARY:
                rowsUpdated = db.update(BookContract.LibrariesEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case BOOKS:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(BookContract.BooksEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }
}
