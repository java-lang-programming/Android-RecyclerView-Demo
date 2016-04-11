package com.java_lang_programming.android_recycleview_demo.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.java_lang_programming.android_recycleview_demo.R;

public class RecyclerViewDividerLineActivity extends AppCompatActivity {

    public final static String PRESENTER_TAG = "Presenter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_divider_line);
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

        FragmentManager fragmentManager = getFragmentManager();

        RecyclerViewDividerLineFragment itemFragment = (RecyclerViewDividerLineFragment) fragmentManager.findFragmentById(R.id.recycler_view_swipe_frag);
        if (itemFragment == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(itemFragment, PRESENTER_TAG);
            fragmentTransaction.commit();
        }
    }

}
