package com.example.mynotebook;

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
 * This activity is for adding new notes. It provides a EditText field,
 * TextView field and a save menu. When the users click the save menu,
 * their notes will be stored in database. This activity will be closed.
 * And users will return to MainActivity.
 *
 * @author Zheng Zhang
 * Created by Zheng Zhang on 2016/3/15.
 */
public class AddActivity extends AppCompatActivity {
    public NoteDataSource data;
    EditText editText;
    TextView textView;


    /**
     * This method is to create the add activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //add app bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.add_toolbar);
        setSupportActionBar(myToolbar);

        data = new NoteDataSource(this);
        try {
            data.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //get the users' entering.
        editText = (EditText) findViewById(R.id.note_add);
        textView = (TextView) findViewById(R.id.note_add_print);
    }

    /**
     * This method is for creating the menus in this activity.
     * @param menu It is the menu xml needed to add in this activity.
     * @return This returns true or false to represent whether the optionsMenu set up successfully.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * This method set the handler for the option menu.
     * @param item It is the menu item.
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_save:
                save();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is for users saving their notes.
     */
    public void save(){
        String str = editText.getText().toString();
        textView.setText(str);
        data.createNote(str);

        //add "add" toast
        Toast.makeText(this,"note added",Toast.LENGTH_SHORT).show();
        //When the notes saved, finish this add activity.
        finish();
    }
}
