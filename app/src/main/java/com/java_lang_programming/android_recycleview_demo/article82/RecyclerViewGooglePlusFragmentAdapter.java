/*
 * Copyright (C) 2017 Programming Java Android Development Project
 * Programming Java is
 * <p>
 * http://java-lang-programming.com/
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

package com.java_lang_programming.android_recycleview_demo.article82;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.java_lang_programming.android_recycleview_demo.R;
import com.java_lang_programming.android_recycleview_demo.article82.model.Note;

import java.util.List;

/**
 * Adapter for RecyclerView
 */
public class RecyclerViewGooglePlusFragmentAdapter extends RecyclerView.Adapter<RecyclerViewGooglePlusFragmentAdapter.ViewHolder> {
    private static final String TAG = "Adapter";

    private final Context context;
    private final List<Note> noteList;
    private int lastPosition;
    private final RecyclerViewGooglePlusFragment.OnFragmentInteractionListener listener;

    public RecyclerViewGooglePlusFragmentAdapter(Context context, List<Note> items, RecyclerViewGooglePlusFragment.OnFragmentInteractionListener listener) {
        this.context = context;
        this.noteList = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_recycler_view_google_plus_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.note = noteList.get(position);
        holder.title.setText(holder.note.title);
        holder.summary.setText(holder.note.summary);

        holder.view.setOnClickListener(v -> onClickView(holder));

        holder.plus.setOnClickListener(v -> onClickPlus(holder));

        if (lastPosition < holder.getAdapterPosition()) {
            startAnimation(holder.view, holder.getAdapterPosition());
            lastPosition = holder.getAdapterPosition();
        }
    }

    /**
     * click view
     *
     * @param holder ViewHolder
     */
    private void onClickView(final ViewHolder holder) {
        if (null != listener) {
            listener.onClickItem(holder.note);
        }
    }

    /**
     * click plus
     *
     * @param holder ViewHolder
     */
    private void onClickPlus(final ViewHolder holder) {
        holder.note.onClickPlus();
        if (holder.note.onPlus) {
            holder.plus.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_google_plus));
        } else {
            holder.plus.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_circle));
        }
    }

    // anime
    // http://qiita.com/takahirom/items/61976bdebe2c18dfce82
    // protected abstract Animator[] getAnimators(View view);
    // http://stackoverflow.com/questions/28181307/how-to-animate-recyclerview-on-scroll-like-google-plus-google-newsstand
    // up_from_bottom
    private void startAnimation(View view, int position) {
        if (position > 2) {
            Log.d(TAG, "animation start");
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.up_from_bottom);
            animation.setInterpolator(new LinearOutSlowInInterpolator());
            animation.setStartTime(500);
            view.startAnimation(animation);
        }
    }


    @Override
    public int getItemCount() {
        return noteList.size();
    }

    @Override
    public void onViewDetachedFromWindow(final ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        ViewCompat.animate(holder.view).cancel();
        Log.d(TAG, "animation cancel");
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final TextView title;
        private final TextView summary;
        //private final ImageView image;
        private final AppCompatImageView plus;
        //private final AppCompatImageView share;
        private Note note;
        //private int viewType;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            //this.viewType = viewType;
            title = (TextView) view.findViewById(R.id.title);
            summary = (TextView) view.findViewById(R.id.summary);
            //image = (ImageView) view.findViewById(R.id.image);
            plus = (AppCompatImageView) view.findViewById(R.id.plus);
            //share = (AppCompatImageView) view.findViewById(R.id.share);
        }
    }
}
