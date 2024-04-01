package com.example.humancomputerinteraction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Notebook extends AppCompatActivity
{
    ListView notes_list;
    TextView empty;

    static List<String> notes;
    static ArrayAdapter adapter;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook);

        sp = this.getSharedPreferences("com.example.humancomputerinteraction", Context.MODE_PRIVATE);

        notes_list = (ListView) findViewById(R.id.notes_list);
        empty = (TextView) findViewById(R.id.empty);

        notes = new ArrayList<>();

        HashSet<String> noteSet = (HashSet<String>) sp.getStringSet("notes", null);

        if(noteSet == null || noteSet.isEmpty())
        {
            empty.setVisibility(View.VISIBLE);
        }
        else
        {
            empty.setVisibility(View.GONE);
            notes = new ArrayList<>(noteSet);
        }

        adapter = new ArrayAdapter(getApplicationContext(), R.layout.note_item_view, R.id.note_text, notes);
        notes_list.setAdapter(adapter);

        notes_list.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent open_note = new Intent(getApplicationContext(), NotebookEdit.class);
            open_note.putExtra("noteId", i);
            startActivity(open_note);
        });

        notes_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                int item_to_delete = i;
                new AlertDialog.Builder(Notebook.this)
                        .setTitle("Are you sure?")
                        .setMessage("Delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                notes.remove(item_to_delete);
                                adapter.notifyDataSetChanged();

                                HashSet<String> noteSet = new HashSet<>(notes);
                                sp.edit().putStringSet("notes", noteSet).apply();

                                if(noteSet.isEmpty() || noteSet == null)
                                {
                                    empty.setVisibility(View.VISIBLE);
                                }
                            }
                        }).setNegativeButton("No", null)
                        .show();
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent main_window = new Intent(getApplicationContext(), MainScreen.class);
        startActivity(main_window);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.add_note)
        {
            startActivity(new Intent(getApplicationContext(), NotebookEdit.class));
            return true;
        }
        return false;
    }
}