package com.example.mynotebook;

import android.app.Application;
import android.test.ApplicationTestCase;

import org.junit.Test;

/**
 * Created by aprile on 2016/3/27.
 */
public class NoteTest extends ApplicationTestCase<Application> {

    public NoteTest() {
        super(Application.class);
    }

    @Test
    public void getIdTest(){
        Note a = new Note(1,"a");
        Note b = new Note(2,"b");
        Note c = new Note(3,"c");
        assertEquals(a.getId(),1);
        assertEquals(b.getId(),2);
        assertEquals(c.getId(), 3);
    }

    @Test
    public void setIdTest(){
        Note a = new Note();
        a.setId(1);
        assertEquals(a.getId(),1);
    }

    @Test
    public void getNoteTest(){
        Note a = new Note(1,"a");
        Note b = new Note(2,"b");
        Note c = new Note(3,"c");
        assertEquals(a.getNote(),"a");
        assertEquals(b.getNote(),"b");
        assertEquals(c.getNote(),"c");
    }

    @Test
    public void setNoteTest(){
        Note a = new Note();
        a.setNote("a");
        assertEquals(a.getNote(),"a");
    }
}
