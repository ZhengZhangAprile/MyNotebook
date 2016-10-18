package com.example.mynotebook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * This class is my database class. It will store the users' notes.
 * @author Zheng Zhang
 * Created by Zheng Zhang on 2016/3/21.
 */
public class MySQL extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mynote";
    public static final String TABLE_NAME = "notes";
    public static final String ID = "id";
    public static final String CONTENT = "content";
    public static final String TIME = "time";

    // Database creation sql statement
    private static final String DATABASE_CREATE = "CREATE TABLE "
            + TABLE_NAME +" (" + ID
            + " integer primary key autoincrement, " + CONTENT
            + " text not null);";

    public MySQL(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /**
     * This method will create a database.
     * @param db This is a SQLiteDatabase Object.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
