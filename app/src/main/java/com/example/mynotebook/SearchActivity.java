package com.example.mynotebook;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

/**
 * This is a search Activity. It will fulfill the search function and show the search result.
 * @author Zheng Zhang
 * Created by Zheng Zhang on 2016/3/25.
 */
public class SearchActivity extends AppCompatActivity {
    NoteDataSource datasource;
    ArrayAdapter<Note> adapter;
    ListView mListView;
    List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //add app bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.search_toolbar);
        setSupportActionBar(myToolbar);

        datasource = new NoteDataSource(this);
        try {
            datasource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        mListView = (ListView) findViewById(R.id.search_list);
        registerForContextMenu(mListView);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            doMySearch(query);
        }

        // click the view to launch the edit activity
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long id) {
                String value = notes.get(position).getNote();
                long NoteId = notes.get(position).getId();
                // put extra data to EditActivity.
                Intent edit = new Intent(SearchActivity.this, EditActivity.class);
                edit.putExtra("note", value);
                edit.putExtra("id", NoteId);
                startActivity(edit);
                finish();
            }
        });
    }

    /**
     * This method is for search the query in database and show the result on this activity.
     * @param query this string is the content to be searched.
     */
    private void doMySearch(String query) {
        //toast
        Toast.makeText(this, "do search " + query, Toast.LENGTH_SHORT).show();

        //set the adapter
        notes = datasource.search(query);
        adapter = new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1, notes);
        mListView.setAdapter(adapter);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.list_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Note note = notes.get(info.position);
        switch (item.getItemId()) {
            case R.id.menu_note_delete:{
                datasource.deleteNote(note);

                //add "note deleted" toast
                Toast.makeText(this, "note deleted", Toast.LENGTH_SHORT).show();

                notes.remove(info.position);
                adapter = new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1, notes);
                mListView.setAdapter(adapter);
                return true;
            }
            default:
                return super.onContextItemSelected(item);
        }
    }
}
