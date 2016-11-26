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

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.java_lang_programming.android_recycleview_demo.R;
import com.java_lang_programming.android_recycleview_demo.model.Item;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a list and makes a call to the specified item.
 */
public class AutoScrollRecyclerViewFragmentAdapter extends RecyclerView.Adapter<AutoScrollRecyclerViewFragmentAdapter.ViewHolder> {

    private final List<Item> items;
    private final AutoScrollRecyclerViewFragment.OnFragmentInteractionListener listener;

    public AutoScrollRecyclerViewFragmentAdapter(List<Item> items, AutoScrollRecyclerViewFragment.OnFragmentInteractionListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.item = items.get(position);
        holder.content.setText(items.get(position).name);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView content;
        //public final Button delete;
        public Item item;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            content = (TextView) view.findViewById(R.id.content);
        }
    }
}
