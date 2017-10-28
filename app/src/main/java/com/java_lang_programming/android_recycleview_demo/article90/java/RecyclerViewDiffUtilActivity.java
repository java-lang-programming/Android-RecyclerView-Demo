package com.java_lang_programming.android_recycleview_demo.article90.java;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.java_lang_programming.android_recycleview_demo.R;
import com.java_lang_programming.android_recycleview_demo.article90.java.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/mrmike/DiffUtil-sample/blob/master/app/src/main/java/com/moczul/diffutilsample/ActorDiffCallback.java
 * アニメ
 * 記事にしよう。。。。
 * https://github.com/wasabeef/recyclerview-animators
 * 次
 * animation
 */
public class RecyclerViewDiffUtilActivity extends AppCompatActivity {

    public static final String TAG = "DiffUtilActivity";

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerViewDiffUtilAdapter adapter;
//    private DiffUtil.DiffResult diffResult;

//    private List<Item> displayList;
//    private List<Item> newDisplayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_diff_util);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        displayList = new ArrayList<>();
//        displayList.addAll(getItemList());
//
//        newDisplayList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getBaseContext());
        adapter = new RecyclerViewDiffUtilAdapter(getItemList());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        // divider
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Set Menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.diff_util, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert:
                insert();
                break;
            case R.id.action_update:
                update();
                break;
            case R.id.action_delete:
                delete();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * RecyclerView.Adapter
     */
    private class RecyclerViewDiffUtilAdapter extends RecyclerView.Adapter<ViewHolder> {

        private final List<Item> mValues;

        private RecyclerViewDiffUtilAdapter(List<Item> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.item = mValues.get(position);
            holder.mIdView.setText(String.valueOf(holder.item.id));
            holder.mContentView.setText(holder.item.name);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public void diffItems(List<Item> newList) {
            final ItemDiffUtil diffCallback = new ItemDiffUtil(mValues, newList);
            final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

            this.mValues.clear();
            this.mValues.addAll(newList);
            diffResult.dispatchUpdatesTo(this);
        }

        public List<Item> getList() {
            return mValues;
        }

        public void add(Item item) {
            mValues.add(item);
        }

    }

    /**
     * ViewHolder
     */
    private class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mIdView;
        private final TextView mContentView;
        public Item item;

        private ViewHolder(View view) {
            super(view);
            mIdView = view.findViewById(R.id.id);
            mContentView = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    /**
     * 2つのリスト間のdiffを計算する際に利用するコールバック関数
     */
    private class ItemDiffUtil extends DiffUtil.Callback {

        private List<Item> oldList;
        private List<Item> newList;

        private ItemDiffUtil(List<Item> oldList, List<Item> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).id == newList.get(newItemPosition).id;
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            Log.d(TAG, "areContentsTheSame : newList : " + newList.get(newItemPosition).name + ", oldList : " + oldList.get(oldItemPosition).name);
            return oldList.get(oldItemPosition).name.equals(newList.get(newItemPosition).name);
        }

//        // falseが呼び出される
//        // Payload ユーザーが送信したいデータそのものを指し示す
//        @Override
//        public Item getChangePayload(int oldItemPosition, int newItemPosition) {
//            // Log.d(TAG, "getChangePayload");
//            //return oldList.get(oldItemPosition);
//            Item item = new Item();
//            item.id = 9999;
//            item.name = "更新されてよ。";
//            return item;
//        }
    }

    private void insert() {
        List<Item> newDisplayList = new ArrayList<>();
        newDisplayList.addAll(adapter.getList());
        newDisplayList.addAll(getNewItemList());
        adapter.diffItems(newDisplayList);
    }

    private void update() {
        Log.d(TAG, "update");
        int i = 0;
        List<Item> newDisplayList = new ArrayList<>();
        for (Item item : adapter.getList()) {
            if (i == 2) {
                Item updateItem = new Item();
                updateItem.id = 3;
                updateItem.name = "updated";
                newDisplayList.add(updateItem);
            } else {
                newDisplayList.add(item);
            }
            i++;
        }
        adapter.diffItems(newDisplayList);
    }

    private void delete() {
        List<Item> newDisplayList = new ArrayList<>();
        newDisplayList.addAll(adapter.getList());
        newDisplayList.remove(0);
        newDisplayList.remove(0);
        adapter.diffItems(newDisplayList);
    }

    private List<Item> getItemList() {
        List<Item> list = new ArrayList<>();
        Item item = new Item();
        item.id = 1;
        item.name = "Item1";
        item.summary = "Item1 Test";
        list.add(item);

        Item item2 = new Item();
        item2.id = 2;
        item2.name = "Item2";
        item2.summary = "Item2 Test";
        list.add(item2);

        return list;
    }

    int count = 3;

    private List<Item> getNewItemList() {
        List<Item> list = new ArrayList<>();
        Item item3 = new Item();
        item3.id = count;
        item3.name = "Item" + count;
        item3.summary = "Item" + count + " Test ";
        list.add(item3);

        count++;

        Item item4 = new Item();
        item4.id = count;
        item4.name = "Item" + count;
        item4.summary = "Item" + count + " Test ";
        list.add(item4);
        count++;

        return list;
    }

}
