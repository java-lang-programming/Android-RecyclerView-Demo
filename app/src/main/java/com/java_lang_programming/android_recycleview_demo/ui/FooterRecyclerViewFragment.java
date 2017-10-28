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

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.java_lang_programming.android_recycleview_demo.R;
import com.java_lang_programming.android_recycleview_demo.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * interface.
 */
public class FooterRecyclerViewFragment extends Fragment {

    private FooterRecyclerViewFragmentAdapter recyclerViewFragmentAdapter;
    private OnFragmentInteractionListener listener;
    private List<Item> list;
    private long offset;
    private long autoScrollSize;
    private boolean isLoading;
    private static final int DEFAULT_OFFSET = 20;

    /**
     * Create object with singleton.
     *
     * @return object
     */
    public static FooterRecyclerViewFragment newInstance() {
        FooterRecyclerViewFragment fragment = new FooterRecyclerViewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        initParam();
        list = new ArrayList<Item>();

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerViewFragmentAdapter = new FooterRecyclerViewFragmentAdapter(list, listener);
            recyclerView.setAdapter(recyclerViewFragmentAdapter);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    int visibleItemCount = recyclerView.getChildCount();
                    LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int firstVisibleItem = manager.findFirstVisibleItemPosition();
                    int lastInScreen = firstVisibleItem + visibleItemCount;

                    if (isAutoScroll(lastInScreen)) {
                        isLoading = true;
                        load();
                    }
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }
            });
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (autoScrollSize == 0) {
            load();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        list = null;
        recyclerViewFragmentAdapter = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    /**
     * Insert object to RecyclerView
     *
     * @param item
     */
    public void insertToRecyclerView(Item item) {
        if (list != null) {
            int index = list.indexOf(item);
            // check whether object has or not
            if (-1 == index) {
                list.add(0, item);
                recyclerViewFragmentAdapter.notifyItemInserted(0);
            }
        }
    }

    /**
     * Update object to RecyclerView
     *
     * @param item
     */
    public void updateToRecyclerView(Item item) {
        if (list != null) {
            int index = list.indexOf(item);
            // check whether object has or not
            if (-1 != index) {
                recyclerViewFragmentAdapter.notifyItemChanged(index, item);
            }
        }
    }

    /**
     * Delete object from RecyclerView
     *
     * @param item
     */
    public void deleteFromRecyclerView(Item item) {
        if (list != null) {
            int index = list.indexOf(item);
            if (-1 != index) {
                boolean isDelete = list.remove(item);
                if (isDelete) {
                    recyclerViewFragmentAdapter.notifyItemRemoved(index);
                }
            }
        }
    }

    public void initParam() {
        list = null;
        offset = 0;
        isLoading = false;
        autoScrollSize = 0;
    }

    private boolean isAutoScroll(int lastInScreen) {
        // If you have never loaded,  Auto Scroll do not do
        if (autoScrollSize == 0) {
            return false;
        }

        // loading中はAutoScrollしない
        if (isLoading) {
            return false;
        }

        //　画面下でない場合は、AutoScrollしない
        if (autoScrollSize != lastInScreen) {
            return false;
        }

        return true;
    }

    private void load() {
        // sample data 1,000 count
        int size = DEFAULT_OFFSET;
        if (list.size() > 200) {
            size = 7;
        }

        List<Item> items = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Item item = new Item();
            item.name = "Item " + (list.size() + i);
            items.add(item);
        }

//        // debug
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        setList(items);
    }

    private void setList(@Nullable List<Item> items) {
        // データがない
        if ((items == null || items.size() == 0) && list.size() == 0) {
            return;
        }

        // delete old footer
        deleteOldFooter();
        // add new footer
        addNewFooter(items);

        // リストにアイテムを追加
        int positionStart = list.size();
        for (Item item : items) {
            list.add(item);
        }
        recyclerViewFragmentAdapter.notifyItemRangeInserted(positionStart, items.size());

        offset = list.size();
        isLoading = false;
        autoScrollSize += DEFAULT_OFFSET;
    }

    /**
     * 古いfooterを削除
     */
    private void deleteOldFooter() {
        // 古いfooterを削除する
        if (list.size() > 0) {
            Item item = list.get(list.size() - 1);
            if (item.isFooter() || item.isLoadingFooter()) {
                list.remove(item);
            }
        }
    }

    /**
     * 新しいfooterを追加
     *
     * @param items
     */
    private void addNewFooter(@Nullable List<Item> items) {
        // 追加項目から表示するfooterの種類を決定する
        int add_item_size = items.size();
        // offset以下
        Item item_footer = new Item();
        if (add_item_size < DEFAULT_OFFSET) {
            item_footer.type = FooterRecyclerViewFragmentAdapter.TYPE_FOOTER;
        } else if (add_item_size == DEFAULT_OFFSET) {
            item_footer.type = FooterRecyclerViewFragmentAdapter.TYPE_LOADING_FOOTER;
        }
        items.add(items.size(), item_footer);
    }

    public interface OnFragmentInteractionListener {
        void onClickItem(Item item);

        void onClickDelete(Item item);
    }
}