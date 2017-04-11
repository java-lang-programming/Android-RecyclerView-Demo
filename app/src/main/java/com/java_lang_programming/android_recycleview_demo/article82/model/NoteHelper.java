package com.java_lang_programming.android_recycleview_demo.article82.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msuzuki on 2017/04/04.
 */
public class NoteHelper {

    /**
     * return Menu List
     *
     * @param context you should use ApplicationContext. ApplicationContext can get getApplicationContext().
     * @return the list objects of rows, null otherwise.
     */
    public static List<Note> getNoteList(final Context context) {
        List<Note> list = new ArrayList<Note>();
        create(list);
        return list;
    }

    public static void create(List<Note> list) {
        Note note1 = new Note();
        note1.id = 1;
        note1.title = "test title 1";
        note1.summary = "This is summary1. you can create Android Application.";
        note1.imagePath = "";
        note1.plus = 10;

        Note note2 = new Note();
        note2.id = 2;
        note2.title = "test title 2";
        note2.summary = "This is summary2. you can create Android Application.";
        note2.imagePath = "";
        note2.plus = 11;

        Note note3 = new Note();
        note3.id = 3;
        note3.title = "test title 3";
        note3.summary = "This is summary3. you can create Android Application.";
        note3.imagePath = "";
        note3.plus = 3;

        Note note4 = new Note();
        note4.id = 4;
        note4.title = "test title 4";
        note4.summary = "This is summary4. you can create Android Application.";
        note4.imagePath = "";
        note4.plus = 99;

        Note note5 = new Note();
        note5.id = 5;
        note5.title = "test title 5";
        note5.summary = "This is summary5. you can create Android Application.";
        note5.imagePath = "";
        note5.plus = 0;

        Note note6 = new Note();
        note6.id = 6;
        note6.title = "test title 6";
        note6.summary = "This is summary6. you can create Android Application.";
        note6.imagePath = "";
        note6.plus = 0;

        Note note7 = new Note();
        note7.id = 7;
        note7.title = "test title 7";
        note7.summary = "This is summary7. you can create Android Application.";
        note7.imagePath = "";
        note7.plus = 0;

        Note note8 = new Note();
        note8.id = 8;
        note8.title = "test title 8";
        note8.summary = "This is summary8. you can create Android Application.";
        note8.imagePath = "";
        note8.plus = 10;

        Note note9 = new Note();
        note9.id = 9;
        note9.title = "test title 9";
        note9.summary = "This is summary9. you can create Android Application.";
        note9.imagePath = "";
        note9.plus = 12;

        list.add(note1);
        list.add(note2);
        list.add(note3);
        list.add(note4);
        list.add(note5);
        list.add(note6);
        list.add(note7);
        list.add(note8);
        list.add(note9);
    }
}
