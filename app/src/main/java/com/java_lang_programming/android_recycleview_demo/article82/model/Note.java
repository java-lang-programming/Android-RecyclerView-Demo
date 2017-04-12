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

package com.java_lang_programming.android_recycleview_demo.article82.model;

/**
 * Model for Note
 */
public class Note extends Home {
    public long id;
    public String title;
    public String summary;
    public String imagePath;
    public int plus;
    public boolean onPlus;
//    private List<Commnet> commnet;

    /**
     * click plus button
     */
    public void onClickPlus() {
        if (onPlus) {
            if (plus > 0) {
                plus--;
            }
            onPlus = false;
        } else {
            plus++;
            onPlus = true;
        }
    }
}
