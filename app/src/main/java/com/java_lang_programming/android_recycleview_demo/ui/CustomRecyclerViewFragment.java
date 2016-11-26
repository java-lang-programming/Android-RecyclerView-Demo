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
    private long offset;
    private long autoScrollSize;
    private boolean isLoading;
    private static final int DEFAULT_OFFSET = 20;

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
        initParam();
        list = new ArrayList<Product>();

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            customRecyclerViewFragmentAdapter = new CustomRecyclerViewFragmentAdapter(list, mFragmentListener);
            recyclerView.setAdapter(customRecyclerViewFragmentAdapter);
            // scroll
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    Log.d("autoScrollSize", "onScrolled");

                    int visibleItemCount = recyclerView.getChildCount();
                    LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int totalItemCount = manager.getItemCount();
                    int firstVisibleItem = manager.findFirstVisibleItemPosition();

                    //Log.d("autoScrollSize", "visibleItemCount: " + visibleItemCount + ", totalItemCount: " + totalItemCount + ", firstVisibleItem: " + firstVisibleItem);

                    int lastInScreen = firstVisibleItem + visibleItemCount;
                    //Log.d(TAG, "lastInScreen: " + lastInScreen);
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
    public void onStart() {
        Log.d("CustomRecycler", "onStart");
        super.onStart();
        if (autoScrollSize == 0) {
            load();
        }
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
                    autoScrollSize--;
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
     * パラメーターを初期化する
     */
    public void initParam() {
        list = null;
        offset = 0;
        isLoading = false;
        autoScrollSize = 0;
    }

    /**
     * 自動スクロールをする場合はtrue。しない場合はfalse
     *
     * @param lastInScreen
     * @return
     */
    private boolean isAutoScroll(int lastInScreen) {
        Log.d("autoScrollSize", "lastInScreen :" + lastInScreen);
        // 1度もLoadしていない場合は、AutoScrollしない
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

    /**
     * ロード
     */
    private void load() {
        if (list.size() >= 1000) {
            return;
        }
        List<Product> products = new ArrayList();
        for (int i = 0; i < DEFAULT_OFFSET; i++) {
            Product product = new Product();
            product.name = "product " + (i + 1);
            products.add(product);
        }
        setList(products);
    }

    private void setList(@Nullable  List<Product> products)  {
        if (products == null || products.size() == 0) {
            return;
        }

        int positionStart = products.size();
        for (Product product : products) {
            list.add(product);
        }
        customRecyclerViewFragmentAdapter.notifyItemRangeInserted(positionStart, products.size());

        offset += list.size();
        isLoading = false;
        autoScrollSize += CustomRecyclerViewFragment.DEFAULT_OFFSET;
        Log.d("autoScrollSize", "autoScrollSize :" + autoScrollSize);
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
