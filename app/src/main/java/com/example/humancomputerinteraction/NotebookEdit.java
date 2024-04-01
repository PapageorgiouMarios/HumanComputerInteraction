package com.example.humancomputerinteraction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.HashSet;

public class NotebookEdit extends AppCompatActivity
{
    EditText note_edit;
    int note_id;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook_edit);


        sp = this.getSharedPreferences("com.example.humancomputerinteraction", Context.MODE_PRIVATE);
        note_edit = (EditText) findViewById(R.id.note_edit);

        Intent intent = getIntent();
        note_id = intent.getIntExtra("noteId", -1);

        if(note_id != -1)
        {
            note_edit.setText(Notebook.notes.get(note_id));
        }
        else
        {
            Notebook.notes.add("");
            note_id = Notebook.notes.size() - 1;
        }

        note_edit.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                Notebook.notes.set(note_id, String.valueOf(charSequence));
                Notebook.adapter.notifyDataSetChanged();

                HashSet<String> noteSet = new HashSet<>(Notebook.notes);
                sp.edit().putStringSet("notes", noteSet).apply();

            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.save_note)
        {
            startActivity(new Intent(getApplicationContext(), Notebook.class));
            finish();
            return true;
        }
        return false;
    }
}