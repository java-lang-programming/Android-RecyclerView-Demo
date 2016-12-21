/**
 * Copyright (C) 2016 Programming Java Android Development Project
 * Programming Java is
 * <p>
 * http://java-lang-programming.com/
 * <p>
 * RecyclerView Generator version : 0.3.0
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.java_lang_programming.android_recycleview_demo.ui;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.java_lang_programming.android_recycleview_demo.R;
import com.java_lang_programming.android_recycleview_demo.model.Item;
import com.java_lang_programming.android_recycleview_demo.provider.ItemSearchRecentSuggestionsProvider;

/**
 * This class is an activity class that manages a screen.
 */
public class SearchRecyclerViewActivity extends AppCompatActivity implements SearchRecyclerViewFragment.OnFragmentInteractionListener {

    private SearchRecyclerViewFragment recyclerViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recycler_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        recyclerViewFragment = (SearchRecyclerViewFragment) fragmentManager.findFragmentById(R.id.search_recycler_view_frag);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d("Search", query);
            getSupportActionBar().setTitle(query);

            SearchRecentSuggestions searchRecentSuggestions = new SearchRecentSuggestions(getApplicationContext(),
                    ItemSearchRecentSuggestionsProvider.AUTHORITY,
                    ItemSearchRecentSuggestionsProvider.MODE);

            searchRecentSuggestions.saveRecentQuery(query, null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Set Menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert:
                break;
            case R.id.action_clear_search_history:
                SearchRecentSuggestions searchRecentSuggestions = new SearchRecentSuggestions(getApplicationContext(),
                        ItemSearchRecentSuggestionsProvider.AUTHORITY,
                        ItemSearchRecentSuggestionsProvider.MODE);

                searchRecentSuggestions.clearHistory();
                // show delete message
                Toast.makeText(this, getString(R.string.action_clear_msg), Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickItem(Item item) {
    }

    @Override
    public void onClickDelete(Item item) {
    }

}
