/*
package com.example.humancomputerinteraction;

import static com.example.humancomputerinteraction.MainScreen.REQUEST_SPEECH_RECOGNIZER;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Utilities extends AppCompatActivity
{

    public void recogniseSpeech()
    {

        Intent intent = new Intent
                (RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say Something");

        try {
            startActivityForResult(intent, REQUEST_SPEECH_RECOGNIZER);
        }catch (Exception e){
            Toast.makeText(this, " "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);

        switch(requestCode){
            case REQUEST_SPEECH_RECOGNIZER:{
                if(resultCode==RESULT_OK && null!=data){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String a = result.get(0);
                    Log.d("m",a);


                }
            }
        }
    }
}
*/
