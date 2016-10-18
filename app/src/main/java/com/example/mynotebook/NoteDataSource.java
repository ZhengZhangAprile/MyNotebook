package com.example.mynotebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class encapsulates the database action into a easier way.
 * @author  Zheng Zhang
 * Created by Zheng Zhang on 2016/3/21.
 */
public class NoteDataSource {
    // Database fields
    private SQLiteDatabase database;
    private MySQL db;
    private String[] allColumns = { MySQL.ID,
            MySQL.CONTENT };

    //NoteDataSource objects are MySQL objects.
    public NoteDataSource(Context context) {
        db = new MySQL(context);
    }

    public void open() throws SQLException {
        database = db.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    /**
     * This method will create a note in database.
     * @param note this string is the content should be stored in the database.
     * @return it will return a Note.
     */
    public Note createNote(String note) {
        ContentValues values = new ContentValues();
        values.put(MySQL.CONTENT, note);
        long insertId = database.insert(MySQL.TABLE_NAME, null,
                values);//insert a note in database.
        Note newNote = new Note();
        newNote.setId(insertId);
        newNote.setNote(note);
        return newNote;
    }

    /**
     * This method will delete a note in database.
     * @param note it represents the note should be deleted.
     */
    public void deleteNote(Note note) {
        long id = note.getId();
        System.out.println("Note deleted with id: " + id);
        database.delete(MySQL.TABLE_NAME, MySQL.ID
                + " = " + id, null);//delete the note in database.
    }

    /**
     * This method will delete a note in database.
     * @param id this id is the id of the note will be deleted.
     */
    public void deleteNote(long id){
        System.out.println("Note deleted with id: " + id);
        database.delete(MySQL.TABLE_NAME, MySQL.ID
                + " = " + id, null);//delete a note in database.
    }

    /**
     * This method will return all notes stores in the database.
     * @return return a list of note.
     */
    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<Note>();

        Cursor cursor = database.query(MySQL.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Note note = cursorToNote(cursor);
            notes.add(note);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return notes;
    }

    /**
     * This method is for using cursor to find note.
     * @param cursor it stores the information on note
     * @return
     */
    private Note cursorToNote(Cursor cursor) {
        Note note = new Note();
        note.setId(cursor.getLong(0));
        note.setNote(cursor.getString(1));
        return note;
    }

    /**
     * This method is for search specific content str in the database and show
     * the relative notes.
     * @param str it is the query str.
     * @return return a list of notes contains the str.
     */
    public List<Note> search(String str){
        List<Note> notes = new ArrayList<>();
        String[] selectionArgs = { "%"+str+"%" };
        //query the notes contains the str.
        Cursor cursor = database.rawQuery("SELECT * FROM "+db.TABLE_NAME+" WHERE "+db.CONTENT+" LIKE  ? ", selectionArgs);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Note note = cursorToNote(cursor);
            notes.add(note);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return notes;
    }
}
