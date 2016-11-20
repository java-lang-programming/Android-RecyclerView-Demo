package com.java_lang_programming.android_recycleview_demo.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.java_lang_programming.android_recycleview_demo.R;
import com.java_lang_programming.android_recycleview_demo.model.Product;
import com.java_lang_programming.android_recycleview_demo.ui.dialog.CustomDialogFragment;

/**
 * CustomRecyclerViewActivity
 */
public class CustomRecyclerViewActivity extends AppCompatActivity implements CustomRecyclerViewFragment.OnFragmentInteractionListener,
        CustomDialogFragment.OnFragmentInteractionListener {

    private TextView msg;
    private CustomRecyclerViewFragment customRecyclerViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CustomRecycler", "onCreate : ");
        setContentView(R.layout.activity_custom_recycler_view);
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


        msg = (TextView) findViewById(R.id.msg);
        FragmentManager fragmentManager = getSupportFragmentManager();
        customRecyclerViewFragment = (CustomRecyclerViewFragment) fragmentManager.findFragmentById(R.id.custom_recycler_view_frag);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Set Menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.custom_recycler_view, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert:
                showDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * ダイアログ表示
     */
    private void showDialog() {
        CustomDialogFragment newFragment = CustomDialogFragment.newInstance();
        newFragment.show(getFragmentManager(), "dialog");
    }

    /**
     * 登録
     *
     * @param product
     */
    @Override
    public void onFragmentInteractionInsert(Product product) {
        Log.d("CustomRecycler", "onFragmentInteractionInsert : " + product.name);
        // 登録
        if (customRecyclerViewFragment != null) {
            customRecyclerViewFragment.insertToRecyclerView(product);
        }

    }

    /**
     * 更新
     *
     * @param product
     */
    @Override
    public void onFragmentInteractionUpdate(Product product) {
        Log.d("CustomRecycler", "onFragmentInteractionUpdate : " + product.name);
        // 登録
        if (customRecyclerViewFragment != null) {
            customRecyclerViewFragment.updateToRecyclerView(product);
        }
    }

    /**
     * 列クリック
     *
     * @param product
     */
    @Override
    public void onClickItem(Product product) {
        CustomDialogFragment newFragment = CustomDialogFragment.newInstance(product);
        newFragment.show(getFragmentManager(), "dialog");
    }

    /**
     * 削除
     *
     * @param product
     */
    @Override
    public void onClickDelete(Product product) {
        Log.d("CustomRecycler", "onListFragmentInteractionDelete : " + product.name);
        // 登録
        if (customRecyclerViewFragment != null) {
            customRecyclerViewFragment.deleteFromRecyclerView(product);
        }
    }

    /**
     * 件数変更
     *
     * @param size
     */
    @Override
    public void onListFragmentInteractionCount(int size) {
        if (size == 0) {
            msg.setVisibility(View.VISIBLE);
        } else {
            msg.setVisibility(View.GONE);
        }
    }

}
