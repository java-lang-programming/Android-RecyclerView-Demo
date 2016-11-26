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

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.java_lang_programming.android_recycleview_demo.R;
import com.java_lang_programming.android_recycleview_demo.model.Item;

import java.util.List;

import static com.java_lang_programming.android_recycleview_demo.R.layout.fragment_item;
import static com.java_lang_programming.android_recycleview_demo.R.layout.layout_item_footer;
import static com.java_lang_programming.android_recycleview_demo.R.layout.layout_item_loading_footer;

/**
 * {@link RecyclerView.Adapter} that can display a list and makes a call to the specified item.
 */
public class FooterRecyclerViewFragmentAdapter extends RecyclerView.Adapter<FooterRecyclerViewFragmentAdapter.ViewHolder> {

    public final static int TYPE_LIST = 1;
    public final static int TYPE_LOADING_FOOTER = 2;
    public final static int TYPE_FOOTER = 3;

    private final List<Item> items;
    private final FooterRecyclerViewFragment.OnFragmentInteractionListener listener;

    public FooterRecyclerViewFragmentAdapter(List<Item> items, FooterRecyclerViewFragment.OnFragmentInteractionListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_FOOTER) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(layout_item_footer, parent, false);
        } else if (viewType == TYPE_LOADING_FOOTER) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(layout_item_loading_footer, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(fragment_item, parent, false);
        }
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (TYPE_FOOTER == holder.viewType || TYPE_LOADING_FOOTER == holder.viewType) {
            return;
        }

        holder.item = items.get(position);
        holder.content.setText(items.get(position).name);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onClickItem(holder.item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position).isLoadingFooter()) {
            return TYPE_LOADING_FOOTER;
        } else if (items.get(position).isFooter()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_LIST;
        }
    }

//    View view;
//    if (viewType == TYPE_HEADER) {
//        view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.fragment_item, parent, false);
//    } else {
//        view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.fragment_item_fotter, parent, false);
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView content;
        //public final Button delete;
        public Item item;
        public int viewType;

        public ViewHolder(View view, int viewType) {
            super(view);
            this.view = view;
            this.viewType = viewType;
            content = (TextView) view.findViewById(R.id.content);
        }
    }


}