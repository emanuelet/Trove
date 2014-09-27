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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.etapps.trove.data.BookContract.BooksEntry;
import com.etapps.trove.data.BookContract.HoldingsEntry;
import com.etapps.trove.data.BookContract.LibrariesEntry;

/**
 * Manages a local database for weather data.
 */
public class BooksDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "books.db";
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    public BooksDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a table to hold locations.  A location consists of the string supplied in the
        // location setting, the city name, and the latitude and longitude
        final String SQL_CREATE_BOOKS_TABLE = "CREATE TABLE " + BooksEntry.TABLE_NAME + " (" +
                BooksEntry._ID + " INTEGER PRIMARY KEY," +
                BooksEntry.COLUMN_TROVE_KEY + " TEXT UNIQUE NOT NULL, " +
                BooksEntry.COLUMN_BOOK_TITLE + " TEXT NOT NULL, " +
                BooksEntry.COLUMN_BOOK_AUTHOR + " TEXT, " +
                BooksEntry.COLUMN_BOOK_YEAR + " TEXT NOT NULL, " +
                BooksEntry.COLUMN_URL + " TEXT NOT NULL , "+
                BooksEntry.COLUMN_BOOK_HOLDINGS + " TEXT, " +
                BooksEntry.COLUMN_BOOK_VERSIONS + " TEXT); ";

        final String SQL_CREATE_HOLDINGS_TABLE = "CREATE TABLE " + HoldingsEntry.TABLE_NAME + " (" +
                HoldingsEntry._ID + " INTEGER PRIMARY KEY, " +
                HoldingsEntry.COLUMN_NUC + " TEXT NOT NULL, " +
                HoldingsEntry.COLUMN_TROVE_KEY + " TEXT NOT NULL, " +
                HoldingsEntry.COLUMN_URL + " TEXT, " +
                // Set up the location column as a foreign key to location table.
                " FOREIGN KEY (" + HoldingsEntry.COLUMN_TROVE_KEY  + ") REFERENCES " +
                BooksEntry.TABLE_NAME + " (" + BooksEntry.COLUMN_TROVE_KEY + "), " +
                " FOREIGN KEY (" + HoldingsEntry.COLUMN_NUC + ") REFERENCES " +
                LibrariesEntry.TABLE_NAME + " (" + LibrariesEntry.COLUMN_NUC + "));";

        final String SQL_CREATE_LIBRARIES_TABLE = "CREATE TABLE " + LibrariesEntry.TABLE_NAME + " (" +
                LibrariesEntry._ID + " INTEGER PRIMARY KEY," +
                LibrariesEntry.COLUMN_NUC + " TEXT NOT NULL, " +
                LibrariesEntry.COLUMN_LIBRARY_NAME + " TEXT " +
                //LibrariesEntry.COLUMN_CITY + " TEXT, " +
                //LibrariesEntry.COLUMN_URL + " TEXT);";
                 ");";

        sqLiteDatabase.execSQL(SQL_CREATE_BOOKS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_HOLDINGS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_LIBRARIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BooksEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + HoldingsEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LibrariesEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
