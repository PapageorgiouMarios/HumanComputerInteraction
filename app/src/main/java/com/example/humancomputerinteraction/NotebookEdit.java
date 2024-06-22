package com.example.humancomputerinteraction;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

public class NotebookEdit extends AppCompatActivity
{
    EditText note_edit;
    int note_id;
    SharedPreferences sp;
    Button saveButton;
    ImageButton micButton;
    static final int REQUEST_SPEECH_RECOGNIZER = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook_edit);

        micButton = (ImageButton) findViewById(R.id.microphone_button);
        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(view -> {startActivity(new Intent(getApplicationContext(), Notebook.class));});

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
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                Notebook.notes.set(note_id, String.valueOf(charSequence));
                Notebook.adapter.notifyDataSetChanged();

                HashSet<String> noteSet = new HashSet<>(Notebook.notes);
                sp.edit().putStringSet("notes", noteSet).apply();
            }
            @Override
            public void afterTextChanged(Editable editable){}
        });
        //Enables voice to text
        micButton.setOnClickListener((view ->{recogniseSpeech();}));
    }

    public void recogniseSpeech()
    {
        Intent intent = new Intent
                (RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Turn your words into a note!");

        try {
            startActivityForResult(intent, REQUEST_SPEECH_RECOGNIZER);
        }catch (Exception e){
            Toast.makeText(this, " "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SPEECH_RECOGNIZER) {
            if (resultCode == RESULT_OK && null != data) {

                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String voiceCommand = result.get(0);
                Log.d("mic notes", voiceCommand);

                note_edit.setText(voiceCommand);

                Intent intent = getIntent();
                note_id = intent.getIntExtra("noteId", -1);

                if(note_id != -1)
                {
                    note_edit.setText(voiceCommand);
                }
                else
                {
                    Notebook.notes.add("");
                    note_id = Notebook.notes.size() - 1;
                }

                Notebook.notes.set(note_id, voiceCommand);
                Notebook.adapter.notifyDataSetChanged();

                HashSet<String> noteSet = new HashSet<>(Notebook.notes);
                sp.edit().putStringSet("notes", noteSet).apply();
            }
        }
    }
}