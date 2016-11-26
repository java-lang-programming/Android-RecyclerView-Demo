/**
 * Copyright (C) 2016 Programming Java Android Development Project
 * Programming Java is
 * <p>
 * http://java-lang-programming.com/
 * <p>
 * Model Generator version : 1.3.2
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

package com.java_lang_programming.android_recycleview_demo.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.java_lang_programming.android_recycleview_demo.ui.FooterRecyclerViewFragmentAdapter;

/**
 * An Model class for Item
 */
public class Item implements Parcelable {

    public static final String TAG = "Item";

    // データの種類
    public int type;
    // name
    public String name;

    public Item() {
    }

    public static final Parcelable.Creator<Item> CREATOR
            = new Parcelable.Creator<Item>() {
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    private Item(Parcel in) {
        type = in.readInt();
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(type);
        out.writeString(name);
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append("Item [");
        if (!TextUtils.isEmpty(name)) {
            str.append(" name=" + name);
        }
        str.append(" type=" + type);
        str.append("]");
        return str.toString();
    }

    /**
     * headerの場合はtrue
     *
     * @return
     */
    public boolean isHeader() {
        if (type == 1) {
            return true;
        }
        return false;
    }

    /**
     * footerの場合はtrue
     *
     * @return
     */
    public boolean isFooter() {
        if (type == FooterRecyclerViewFragmentAdapter.TYPE_FOOTER) {
            return true;
        }
        return false;
    }

    /**
     * loading footerの場合はtrue
     *
     * @return
     */
    public boolean isLoadingFooter() {
        if (type == FooterRecyclerViewFragmentAdapter.TYPE_LOADING_FOOTER) {
            return true;
        }
        return false;
    }
}
