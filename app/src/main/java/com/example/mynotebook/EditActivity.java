package com.example.mynotebook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.SQLException;

/**
 * This is an edit activity, which is for editing the users notes.
 * @author  Zheng Zhang
 * Created by Zheng Zhang on 2016/3/14.
 */
public class EditActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;
    public NoteDataSource data;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //add app bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.edit_toolbar);
        setSupportActionBar(myToolbar);

        // open the database
        data = new NoteDataSource(this);
        try {
            data.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        textView = (TextView) findViewById(R.id.note_edit_print);
        editText = (EditText) findViewById(R.id.note_edit);

        //get the data from other activities.
        Intent intent = getIntent();
        String note = intent.getStringExtra("note");
        editText.setText(note);
        id = intent.getLongExtra("id",0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit_save:
                save();
                break;
            case R.id.menu_edit_delete:
                deleteAlert();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is for users saving their edited notes.
     */
    public void save(){
        //get the string from editText
        String newNote = editText.getText().toString();
        textView.setText(newNote);
        //delete the old note and add the new one in the database.
        data.deleteNote(id);
        data.createNote(newNote);

        //add "change saved" toast
        Toast.makeText(this, "change saved", Toast.LENGTH_SHORT).show();

        finish();
    }


    /**
     * This method is for deleting the notes.
     */
    public void delete(){
        data.deleteNote(id);

        //add "note deleted" toast
        Toast.makeText(this,"note deleted",Toast.LENGTH_SHORT).show();

        finish();
    }

    /**
     * This method is for giving users alerts for their delete actions.
     */
    public void deleteAlert(){
        final Context context = this;
        //add alert
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Click confirm to delete the note!");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "confirm",//when press confirm, the note will be deleted.
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        delete();
                    }
                });

        builder1.setNegativeButton(
                "cancel",//when press the cancel, the note won't delete.
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
