package com.java_lang_programming.android_recycleview_demo.ui.dialog;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.java_lang_programming.android_recycleview_demo.R;
import com.java_lang_programming.android_recycleview_demo.model.Product;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CustomDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CustomDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomDialogFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TYPE = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int type;
    private Product product;

    public static final int TYPE_INSERT = 1;
    public static final int TYPE_UPDATE = 2;

    private TextInputLayout mNameLayout;
    private AutoCompleteTextView mNameView;
    private Button btnOk;

    private OnFragmentInteractionListener mListener;

    /**
     * 登録用のインストランス
     *
     * @return
     */
    public static CustomDialogFragment newInstance() {
        CustomDialogFragment fragment = new CustomDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, TYPE_INSERT);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 更新用のインスタンス
     *
     * @param product
     * @return
     */
    public static CustomDialogFragment newInstance(Product product) {
        CustomDialogFragment fragment = new CustomDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, TYPE_UPDATE);
        args.putParcelable(ARG_PARAM2, product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(ARG_TYPE);
            product = getArguments().getParcelable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("CustomRecycler", "onCreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_custom_dialog, container, false);

        mNameLayout = (TextInputLayout) view.findViewById(R.id.name_text_input_layout);
        mNameView = (AutoCompleteTextView) view.findViewById(R.id.name);
        if (type == TYPE_UPDATE) {
            mNameView.setText(product.name);
        } else {
            product = new Product();
        }
        btnOk = (Button) view.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnOk();
            }
        });

        mNameView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_SEND) {
                    String name = textView.getText().toString();
                    mNameLayout.setError(getInValidNameMessage(name));
                    return true;
                }
                return false;
            }
        });

        return view;
    }

    /**
     * Return error message.
     * エラーがない場合は、nullを返す。
     *
     * @param str name
     * @return
     */
    public String getInValidNameMessage(String str) {
        if (TextUtils.isEmpty(str)) {
            return getString(R.string.validate_require, "name");
        } else if (str.length() < getResources().getInteger(R.integer.name_min_count)) {
            return getString(R.string.validate_minimun_word, "name", getResources().getInteger(R.integer.name_min_count));
        } else if (str.length() > getResources().getInteger(R.integer.name_max_count)) {
            return getString(R.string.validate_maximun_word, "name", getResources().getInteger(R.integer.name_max_count));
        } else {
            return null;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteractionInsert(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("CustomRecycler", "onAttach context");
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
        void onFragmentInteractionInsert(Product product);
        void onFragmentInteractionUpdate(Product product);
    }

    /**
     * ボタンOK
     */
    private void onBtnOk() {
        if (mListener != null) {
            String name = mNameView.getText().toString();
            product.name = name;
            if (TYPE_INSERT == type) {
                //　ここでデータ 登録
                product.id = 1;
                mListener.onFragmentInteractionInsert(product);
            } else if (TYPE_UPDATE == type) {
                mListener.onFragmentInteractionUpdate(product);
            }
        }
        dismiss();
    }
}
