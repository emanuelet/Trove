package com.etapps.trovenla.data;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by emanuele on 15/09/14.
 */
public class SuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = SuggestionProvider.class.getName();

    public final static int MODE = DATABASE_MODE_QUERIES;

    public SuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
