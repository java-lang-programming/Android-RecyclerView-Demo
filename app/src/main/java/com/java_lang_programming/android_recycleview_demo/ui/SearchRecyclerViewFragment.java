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

import android.app.Activity;
import android.app.ProgressDialog;
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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchRecyclerViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchRecyclerViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchRecyclerViewFragment extends Fragment {

    private SearchRecyclerViewFragmentAdapter recyclerViewFragmentAdapter;
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
    public static SearchRecyclerViewFragment newInstance() {
        SearchRecyclerViewFragment fragment = new SearchRecyclerViewFragment();
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
            recyclerViewFragmentAdapter = new SearchRecyclerViewFragmentAdapter(list, listener);
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
        load();
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
                ProgressDialog progressDialog = ProgressDialog.show(getActivity(), null, null, true, false);
                //progressDialog.setContentView(R.layout.progress_layout);
                progressDialog.show();

                // 登録
                list.add(0, item);
                recyclerViewFragmentAdapter.notifyItemInserted(0);

                progressDialog.dismiss();
                progressDialog = null;
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

    /**
     * initialize variables
     */
    public void initParam() {
        list = null;
        offset = 0;
        isLoading = false;
        autoScrollSize = 0;
    }

    /**
     * if AutoScroll is possible, return true, else false.
     *
     * @param lastInScreen
     * @return
     */
    private boolean isAutoScroll(int lastInScreen) {
        // If you have never loaded,  Auto Scroll do not do
        if (autoScrollSize == 0) {
            return false;
        }

        // Not scroll while loading.
        if (isLoading) {
            return false;
        }

        //　画面下でない場合は、AutoScrollしない
        if (autoScrollSize != lastInScreen) {
            return false;
        }

        return true;
    }

    /**
     * load data.
     */
    private void load() {
        // sample data 200 count
        if (list.size() >= 200) {
            return;
        }
        List<Item> items = new ArrayList();
        for (int i = 0; i < DEFAULT_OFFSET; i++) {
            Item item = new Item();
            item.name = "item " + (list.size() + i);
            items.add(item);
        }
        setList(items);
    }

    private void setList(@Nullable List<Item> items) {
        if (items == null || items.size() == 0) {
            addFooterIfZeroCount();
            return;
        }

        // delete old footer
        deleteOldFooter();
        // add new footer
        addNewFooter(items);

        int positionStart = list.size();
        for (Item item : items) {
            list.add(item);
        }
        recyclerViewFragmentAdapter.notifyItemRangeInserted(positionStart, items.size());

        offset += DEFAULT_OFFSET;
        isLoading = false;
        autoScrollSize += DEFAULT_OFFSET;
    }

    /**
     * Delete old footer from RecyclerView
     */
    private void deleteOldFooter() {
        // delete old footer
        if (list.size() > 0) {
            Item item = list.get(list.size() - 1);
            if (item.isFooter() || item.isLoadingFooter()) {
                list.remove(item);
            }
        }
    }

    /**
     * Add new footer to RecyclerView
     *
     * @param items
     */
    private void addNewFooter(@Nullable List<Item> items) {
        // add new footer
        int add_item_size = items.size();
        Item item_footer = new Item();
        if (add_item_size < DEFAULT_OFFSET) {
            //item_footer.setType(Item.TYPE_FOOTER);
        } else if (add_item_size == DEFAULT_OFFSET) {
            //item_footer.setType(Memory.TYPE_LOADING_FOOTER);
        }
        items.add(item_footer);
    }

    /**
     * Add new footer to RecyclerView
     */
    private void addFooterIfZeroCount() {
        if (list.size() > 0) {
            deleteOldFooter();
            Item item_footer = new Item();
            //item_footer.setType(Item.TYPE_FOOTER);
            list.add(item_footer);
            int positionStart = list.size();
            recyclerViewFragmentAdapter.notifyItemRangeInserted(positionStart, 1);
        }
    }

    public interface OnFragmentInteractionListener {
        void onClickItem(Item item);

        void onClickDelete(Item item);
    }
}