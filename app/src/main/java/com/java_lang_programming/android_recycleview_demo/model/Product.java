/**
 * Copyright (C) 2016 Programming Java Android Development Project
 * Programming Java is
 *
 *      http://java-lang-programming.com/
 *
 * Model Generator version : 1.3.2
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
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

/**
 * An Model class for Product
 */
public class Product implements Parcelable {

    public static final String TAG = "Product";

    // id
    public int id;
    // name
    public String name;

    public Product() {}
    public static final Parcelable.Creator<Product> CREATOR
            = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    private Product(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(name);
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append("Product [");
        str.append(" id=" + id);
        if (!TextUtils.isEmpty(name)) {
            str.append(", name=" + name);
        }
        str.append("]");
        return str.toString();
    }
}