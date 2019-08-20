package com.datanapps.androidsearchview

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu


import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set toolbar

        setSupportActionBar(toolbarMainActivity)

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(componentName))

        return true
    }
}
