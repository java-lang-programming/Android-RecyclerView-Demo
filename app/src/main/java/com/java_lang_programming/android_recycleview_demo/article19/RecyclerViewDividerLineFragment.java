package com.java_lang_programming.android_recycleview_demo.article19;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.java_lang_programming.android_recycleview_demo.R;
import com.java_lang_programming.android_recycleview_demo.ui.MyItemRecyclerViewAdapter;
import com.java_lang_programming.android_recycleview_demo.ui.dummy.DummyContent;

/**
 * Fragment for RecyclerView.ItemDecoration.
 */
public class RecyclerViewDividerLineFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private LinearLayoutManager linearLayoutManager;

    /**
     * Use this factory method to create a new instance of this fragment.
     *
     * @return A new instance of fragment RecyclerViewDividerLineFragment.
     */
    public static RecyclerViewDividerLineFragment newInstance(String param1, String param2) {
        RecyclerViewDividerLineFragment fragment = new RecyclerViewDividerLineFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            linearLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(DummyContent.ITEMS));

            RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    linearLayoutManager.getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {}
}
