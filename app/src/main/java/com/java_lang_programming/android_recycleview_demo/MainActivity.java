/*
 * Copyright (C) 2016 Masaya Suzuki
 *
 *      http://java-lang-programming.com/
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
package com.java_lang_programming.android_recycleview_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.java_lang_programming.android_recycleview_demo.article19.RecyclerViewDividerLineActivity;
import com.java_lang_programming.android_recycleview_demo.article79.RecyclerViewDividerItemDecorationActivity;
import com.java_lang_programming.android_recycleview_demo.article82.RecyclerViewGooglePlusActivity;
import com.java_lang_programming.android_recycleview_demo.article90.kotlin.RecyclerViewDiffUtilKtActivity;
import com.java_lang_programming.android_recycleview_demo.ui.AutoScrollRecyclerViewActivity;
import com.java_lang_programming.android_recycleview_demo.ui.CustomRecyclerViewActivity;
import com.java_lang_programming.android_recycleview_demo.ui.FooterRecyclerViewActivity;
import com.java_lang_programming.android_recycleview_demo.ui.RecyclerViewActivity;
import com.java_lang_programming.android_recycleview_demo.ui.RecyclerViewSwipeActivity;
import com.java_lang_programming.android_recycleview_demo.ui.SearchRecyclerViewActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // app:layout_behavior="@string/appbar_scrolling_view_behavior"
        Button recyclerViewBtn = (Button) findViewById(R.id.recyclerView_btn);
        recyclerViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recyclerViewIntent = new Intent(MainActivity.this,
                        RecyclerViewActivity.class);
                startActivity(recyclerViewIntent);
            }
        });

        Button recyclerViewSwipedBtn = (Button) findViewById(R.id.recyclerView_swiped_btn);
        recyclerViewSwipedBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent recyclerViewIntent = new Intent(MainActivity.this,
                        RecyclerViewSwipeActivity.class);
                startActivity(recyclerViewIntent);
            }
        });

        Button recyclerViewDividerLineBtn = (Button) findViewById(R.id.recyclerView_divider_line_btn);
        recyclerViewDividerLineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recyclerViewSwipeIntent = new Intent(MainActivity.this,
                        RecyclerViewDividerLineActivity.class);
                startActivity(recyclerViewSwipeIntent);
            }
        });

        Button customRecyclerViewBtn = (Button) findViewById(R.id.custom_recyclerView_btn);
        customRecyclerViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent customRecyclerViewBtnIntent = new Intent(MainActivity.this,
                        CustomRecyclerViewActivity.class);
                startActivity(customRecyclerViewBtnIntent);
            }
        });

        Button autoScrollRecyclerViewBtn = (Button) findViewById(R.id.auto_scroll_recyclerView_btn);
        autoScrollRecyclerViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent autoScrollRecyclerViewBtnIntent = new Intent(MainActivity.this,
                        AutoScrollRecyclerViewActivity.class);
                startActivity(autoScrollRecyclerViewBtnIntent);
            }
        });

        Button footerRecyclerViewBtn = (Button) findViewById(R.id.footer_recyclerView_btn);
        footerRecyclerViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent footerRecyclerViewBtnIntent = new Intent(MainActivity.this,
                        FooterRecyclerViewActivity.class);
                startActivity(footerRecyclerViewBtnIntent);
            }
        });

        Button searchRecyclerViewBtn = (Button) findViewById(R.id.search_recyclerView_btn);
        searchRecyclerViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchRecyclerViewActivity = new Intent(MainActivity.this,
                        SearchRecyclerViewActivity.class);
                startActivity(searchRecyclerViewActivity);
            }
        });

        Button recyclerViewDividerItemDecorationBtn = (Button) findViewById(R.id.recyclerView_divider_item_decoration_btn);
        recyclerViewDividerItemDecorationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recyclerViewDividerItemDecorationActivity = new Intent(MainActivity.this,
                        RecyclerViewDividerItemDecorationActivity.class);
                startActivity(recyclerViewDividerItemDecorationActivity);
            }
        });

        Button recyclerViewGooglePlusBtn = (Button) findViewById(R.id.recyclerView_googlePlus_btn);
        recyclerViewGooglePlusBtn.setOnClickListener(v -> {
            moveRecyclerViewGooglePlusActivity();
        });

        Button recyclerViewDiffUtilBtn = findViewById(R.id.recyclerView_diff_util_btn);
        recyclerViewDiffUtilBtn.setOnClickListener(v -> {
            moveRecyclerViewDiffUtilActivity();
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void moveRecyclerViewGooglePlusActivity() {
        Intent recyclerViewGooglePlusActivity = new Intent(MainActivity.this,
                RecyclerViewGooglePlusActivity.class);
        startActivity(recyclerViewGooglePlusActivity);
    }

    private void moveRecyclerViewDiffUtilActivity() {
        // RecyclerViewDiffUtilActivity
        // RecyclerViewDiffUtilKtActivity
        Intent recyclerViewDiffUtilActivity = new Intent(MainActivity.this,
                RecyclerViewDiffUtilKtActivity.class);
        startActivity(recyclerViewDiffUtilActivity);
    }
}
