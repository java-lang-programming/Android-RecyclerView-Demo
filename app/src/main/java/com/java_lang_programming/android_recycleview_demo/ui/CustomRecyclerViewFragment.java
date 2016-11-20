package com.java_lang_programming.android_recycleview_demo.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.java_lang_programming.android_recycleview_demo.R;
import com.java_lang_programming.android_recycleview_demo.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * interface.
 */
public class CustomRecyclerViewFragment extends Fragment {

    private CustomRecyclerViewFragmentAdapter customRecyclerViewFragmentAdapter;
    private OnFragmentInteractionListener mFragmentListener;
    private List<Product> list;

    /**
     * Create object with singleton.
     *
     * @return object
     */
    public static CustomRecyclerViewFragment newInstance() {
        CustomRecyclerViewFragment fragment = new CustomRecyclerViewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("CustomRecycler", "onCreateView");
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_item_list2, container, false);
        list = new ArrayList<Product>();

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            customRecyclerViewFragmentAdapter = new CustomRecyclerViewFragmentAdapter(list, mFragmentListener);
            recyclerView.setAdapter(customRecyclerViewFragmentAdapter);
        }
        return view;
    }

    @Override
    public void onStart() {
        Log.d("CustomRecycler", "onStart");
        super.onStart();
        setCountUI();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("CustomRecycler", "onAttach activity");
        if (activity instanceof OnFragmentInteractionListener) {
            mFragmentListener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("CustomRecycler", "onAttach context");
        if (context instanceof OnFragmentInteractionListener) {
            mFragmentListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        list = null;
        customRecyclerViewFragmentAdapter = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFragmentListener = null;
    }

    /**
     * オブジェクトをinsertする
     *
     * @param product
     */
    public void insertToRecyclerView(Product product) {
        if (list != null) {
            int index = list.indexOf(product);
            if (-1 == index) {
                list.add(0, product);
                customRecyclerViewFragmentAdapter.notifyItemInserted(0);
                setCountUI();
            }
        }
    }

    /**
     * オブジェクトをupdateする
     *
     * @param product
     */
    public void updateToRecyclerView(Product product) {
        if (list != null) {
            int index = list.indexOf(product);
            if (index != -1) {
                //list.remove(index);
                //list.add(index, product);
                customRecyclerViewFragmentAdapter.notifyItemChanged(index, product);
                //customRecyclerViewFragmentAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * オブジェクトdeleteする
     *
     * @param product
     */
    public void deleteFromRecyclerView(Product product) {
        if (list != null) {
            // object index
            int index = list.indexOf(product);
            if (index != -1) {
                boolean isDelete = list.remove(product);
                if (isDelete) {
                    customRecyclerViewFragmentAdapter.notifyItemRemoved(index);
                    //customRecyclerViewFragmentAdapter.notifyDataSetChanged();
                    setCountUI();
                }
            }
        }
    }

    /**
     * UIに件数を反映する
     */
    private void setCountUI() {
        if (mFragmentListener != null) {
            mFragmentListener.onListFragmentInteractionCount(list.size());
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onClickItem(Product product);

        void onClickDelete(Product product);

        void onListFragmentInteractionCount(int size);
    }
}
