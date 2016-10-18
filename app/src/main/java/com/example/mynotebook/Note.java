package com.example.mynotebook;

/**
 * This class is the model class. It has two params, id and note.
 * @author Zheng Zhang
 * Created by Zheng Zhang on 2016/3/21.
 */
public class Note {
    private long id;
    private String note;

    public Note(long id, String note){
        this.id = id;
        this.note = note;
    }

    public Note(){

    }

    /**
     * This method will be used to get the id of the selected note.
     * @return it will return a long type id.
     */
    public long getId() {
        return id;
    }

    /**
     * This method is for setting the id of the selected note.
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return note;
    }
}
