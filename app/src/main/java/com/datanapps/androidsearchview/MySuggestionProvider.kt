package com.datanapps.androidsearchview

import android.content.SearchRecentSuggestionsProvider

class MySuggestionProvider : SearchRecentSuggestionsProvider() {
    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        val AUTHORITY = "com.datanapps.androidsearchview.MySuggestionProvider"
        val MODE = SearchRecentSuggestionsProvider.DATABASE_MODE_QUERIES or SearchRecentSuggestionsProvider.DATABASE_MODE_2LINES
    }
}
