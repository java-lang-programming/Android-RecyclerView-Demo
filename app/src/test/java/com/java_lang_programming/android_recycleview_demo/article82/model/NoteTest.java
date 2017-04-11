package com.java_lang_programming.android_recycleview_demo.article82.model;

import android.support.test.filters.SmallTest;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A Test class for Note
 */
@SmallTest
public class NoteTest {

    @Test
    public void onClickPlus_plus_1() {
        Note note = new Note();
        note.onClickPlus();
        assertTrue(note.onPlus);
        assertEquals(note.plus, 1);
    }

    @Test
    public void onClickPlus_cancel_plus_0() {
        Note note = new Note();
        note.onPlus = true;
        note.onClickPlus();
        assertFalse(note.onPlus);
        assertEquals(note.plus, 0);
    }

    @Test
    public void onClickPlus_cancel_plus_1() {
        Note note = new Note();
        note.onPlus = true;
        note.plus = 10;
        note.onClickPlus();
        assertFalse(note.onPlus);
        assertEquals(note.plus, 9);
    }
}
