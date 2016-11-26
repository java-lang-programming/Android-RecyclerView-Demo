/**
 * Copyright (C) 2016 Programming Java Android Development Project
 * Programming Java is
 * <p>
 * http://java-lang-programming.com/
 * <p>
 * RecyclerView Generator version : 0.2.0
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

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
public class AutoScrollRecyclerViewFragment extends Fragment {

    private AutoScrollRecyclerViewFragmentAdapter recyclerViewFragmentAdapter;
    private OnFragmentInteractionListener listener;
    private List<Item> list;
    private long offset;
    private long autoScrollPosition;
    private boolean isLoading;
    private static final int DEFAULT_OFFSET = 20;

    /**
     * Create object with singleton.
     *
     * @return object
     */
    public static AutoScrollRecyclerViewFragment newInstance() {
        AutoScrollRecyclerViewFragment fragment = new AutoScrollRecyclerViewFragment();
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
            recyclerViewFragmentAdapter = new AutoScrollRecyclerViewFragmentAdapter(list, listener);
            recyclerView.setAdapter(recyclerViewFragmentAdapter);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    int visibleItemCount = recyclerView.getChildCount();
                    Log.d("AutoScroll ", "recyclerView.getChildCount() : " + visibleItemCount);
                    LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int firstVisibleItem = manager.findFirstVisibleItemPosition();
                    Log.d("AutoScroll ", "manager.findFirstVisibleItemPosition() : " + firstVisibleItem);
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
        if (autoScrollPosition == 0) {
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
        autoScrollPosition = 0;
    }
    private boolean isAutoScroll(int lastInScreen) {
        // If you have never loaded,  Auto Scroll do not do
        if (autoScrollPosition == 0) {
            return false;
        }

        // loading中はAutoScrollしない
        if (isLoading) {
            return false;
        }

        //　画面下でない場合は、AutoScrollしない
        if (autoScrollPosition != lastInScreen) {
            return false;
        }

        return true;
    }

    private void load() {
        if (list.size() >= 1000) {
            return;
        }
        List<Item> items = new ArrayList();
        for (int i = 0; i < DEFAULT_OFFSET; i++) {
            Item item = new Item();
            item.name = "product " + (list.size() + i);
            items.add(item);
        }
        setList(items);
    }

    private void setList(@Nullable  List<Item> items)  {
        if (items == null || items.size() == 0) {
            return;
        }

        int positionStart = list.size();
        for (Item item : items) {
            list.add(item);
        }
        recyclerViewFragmentAdapter.notifyItemRangeInserted(positionStart, items.size());

        offset = list.size();
        autoScrollPosition += DEFAULT_OFFSET;
        isLoading = false;
    }

    public interface OnFragmentInteractionListener {
        void onClickItem(Item item);

        void onClickDelete(Item item);
    }
}