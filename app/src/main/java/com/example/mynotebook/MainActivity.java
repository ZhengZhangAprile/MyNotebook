package com.example.mynotebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.sql.SQLException;
import java.util.List;

/**
 * This is the main activity. It will shows users' notes in listview.
 * And it also has the function search, delete and add new note.
 * @author Zheng Zhang
 * Created by Zheng Zhang on 2016/3/14.
 */
public class MainActivity extends AppCompatActivity {
    private NoteDataSource datasource;
    ListView mListView;
    ArrayAdapter<Note> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //add app bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);

        datasource = new NoteDataSource(this);
        try {
            datasource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final List<Note> values = datasource.getAllNotes();
        mListView = (ListView) findViewById(R.id.main_list);
        registerForContextMenu(mListView);//register a context menu for deleting the note.


        // use the SimpleCursorAdapter to show the elements in a ListView.
        adapter = new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1, values);
        mListView.setAdapter(adapter);

        // click the view to launch the edit activity
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long id) {
                List<Note> values = datasource.getAllNotes();
                String value = values.get(position).getNote();
                long NoteId = values.get(position).getId();
                //put extra data to the edit activity.
                Intent edit = new Intent(MainActivity.this, EditActivity.class);
                edit.putExtra("note",value);
                edit.putExtra("id", NoteId);
                startActivity(edit);
            }
        });
    }

    @Override
    protected void onResume() {
        try {
            datasource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //refresh the adapter
        List<Note> values = datasource.getAllNotes();
        adapter = new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1, values);
        mListView.setAdapter(adapter);

        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * This method is for launch AddActivity.
     */
    public void launchAddActivity(){
        Intent addActivity = new Intent(this, AddActivity.class);
        startActivity(addActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_add:
                launchAddActivity();
                break;
            case R.id.menu_main_search:
                onSearchRequested();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.list_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        List<Note> values = datasource.getAllNotes();
        Note note = values.get(info.position);//get the note, which will be modified.
        switch (item.getItemId()) {
            case R.id.menu_note_delete:{//when click the delete menu, the note will be deleted.
                datasource.deleteNote(note);

                //add "note deleted" toast
                Toast.makeText(this, "note deleted", Toast.LENGTH_SHORT).show();

                onResume();
                return true;
            }
            default:
                return super.onContextItemSelected(item);
        }
    }
}
