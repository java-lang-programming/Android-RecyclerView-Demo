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
import com.java_lang_programming.android_recycleview_demo.article82.model.Note;
import com.java_lang_programming.android_recycleview_demo.article82.model.NoteHelper;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Fragment
 */
public class RecyclerViewGooglePlusFragment extends Fragment {
    public static final String TAG = "MainFragment";

    private boolean isLoad;
    private List<Note> noteList;
//    private long offset;
//    private long autoScrollSize;
//    private boolean isLoading;
//    private static final int DEFAULT_OFFSET = 20;

    private RecyclerViewGooglePlusFragmentAdapter recyclerViewFragmentAdapter;
    private OnFragmentInteractionListener callback;

    /**
     * Create object with singleton.
     *
     * @return object
     */
    public static RecyclerViewGooglePlusFragment newInstance() {
        // RecyclerViewGooglePlusFragment fragment = new RecyclerViewGooglePlusFragment();
        return new RecyclerViewGooglePlusFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_recycler_view_google_plus, container, false);
        initParam();
        noteList = new CopyOnWriteArrayList<>();

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerViewFragmentAdapter = new RecyclerViewGooglePlusFragmentAdapter(getActivity().getApplicationContext(), noteList, callback);
            recyclerView.setAdapter(recyclerViewFragmentAdapter);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

//                    int visibleItemCount = recyclerView.getChildCount();
//                    LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                    int firstVisibleItem = manager.findFirstVisibleItemPosition();
//                    int lastInScreen = firstVisibleItem + visibleItemCount;
//
//                    if (isAutoScroll(lastInScreen)) {
//                        isLoading = true;
//                        load();
//                    }
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
    public void onStart() {
        super.onStart();
        if (!isLoad) {
            load();
            isLoad = true;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            callback = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onClickItem(Note note);
    }

    /**
     * パラメーター初期化
     */
    private void initParam() {
        isLoad = false;
        noteList = null;
//        offset = 0;
//        isLoading = false;
//        autoScrollSize = 0;
    }

    /**
     * Load
     */
    private void load() {
        setList(NoteHelper.getNoteList(getActivity().getApplicationContext()));
    }

    /**
     * メニューを追加する
     *
     * @param addNoteList addNoteList
     */
    private void setList(@Nullable List<Note> addNoteList) {
        if (addNoteList == null || addNoteList.size() == 0) {
            return;
        }

        int positionStart = noteList.size();
        for (Note note : addNoteList) {
            noteList.add(note);
        }

        try {
            recyclerViewFragmentAdapter.notifyItemRangeInserted(positionStart, addNoteList.size());
        } catch (IndexOutOfBoundsException ex) {
            Log.e(TAG, ex.getMessage());
        }
    }
}
