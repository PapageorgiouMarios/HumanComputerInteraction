package com.example.humancomputerinteraction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class MainScreen extends AppCompatActivity
{
    TextView day_and_date;
    TextView month_and_year;
    TextView hour;
    ImageButton voice_command_button;
    Locale locale = Locale.ENGLISH;
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd", locale);
    SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMMM yyyy", locale);
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", locale);

    TextToSpeech english_voice;
    TextToSpeech greek_voice;

    // Interface apps
    ImageButton call_button;
    ImageButton gps_button;
    ImageButton sos_button;
    ImageButton notebook_button;
    ImageButton weather_button;
    ImageButton wake_button;
    ImageButton photo_button;
    ImageButton video_button;
    ImageButton gallery_button;
    ImageButton add_contact_button;
    ImageButton flash_button;
    boolean flash = false;

    TextView flash_on_off;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        getSupportActionBar().hide();

        //----------------------------Date----------------------------
        day_and_date = (TextView) findViewById(R.id.day_date_text);
        //------------------------------------------------------------

        //-----------------------Month and Year-----------------------
        month_and_year = (TextView) findViewById(R.id.month_year_text);
        //------------------------------------------------------------

        //--------------------Time----------------------
        hour = (TextView) findViewById(R.id.time_text);
        //----------------------------------------------

        //--------------------------Voice command Button--------------------------
        voice_command_button = (ImageButton) findViewById(R.id.microphone_button);
        flash_button = (ImageButton) findViewById(R.id.flash_button);
        //------------------------------------------------------------------------

        System.out.println("Check .xml file assets");
        CheckAssets();

        updateDateTime();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    try {
                        Thread.sleep(1000);
                        runOnUiThread(() -> updateDateTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        english_voice = new TextToSpeech(this, i -> {
            if(i != TextToSpeech.ERROR)
            {
                english_voice.setLanguage(Locale.ENGLISH);
            }
        });

        greek_voice = new TextToSpeech(this, i -> {
            if(i != TextToSpeech.ERROR)
            {
                greek_voice.setLanguage(new Locale("el", "GR"));
            }
        });

        //-------------------------APPS----------------------------------
        call_button = (ImageButton) findViewById(R.id.call_button);
        gps_button = (ImageButton) findViewById(R.id.gps_button);
        sos_button = (ImageButton) findViewById(R.id.sos_button);
        notebook_button = (ImageButton) findViewById(R.id.notebook_button);
        weather_button = (ImageButton) findViewById(R.id.weather_button);
        wake_button = (ImageButton) findViewById(R.id.wake_button);
        photo_button = (ImageButton) findViewById(R.id.photo_button);
        video_button = (ImageButton) findViewById(R.id.video_button);
        gallery_button = (ImageButton) findViewById(R.id.gallery_button);
        //----------------------------------------------------------------

        call_button.setOnClickListener(view -> {
            Intent call_APP = new Intent(MainScreen.this, PhoneCall.class);
            startActivity(call_APP);
        });

        notebook_button.setOnClickListener(view -> {
            Intent notebook_APP = new Intent(MainScreen.this, Notebook.class);
            startActivity(notebook_APP);
        });

        sos_button.setOnClickListener(view -> {
            Intent sos_APP = new Intent(MainScreen.this, EmergencyCall.class);
            startActivity(sos_APP);
        });

        wake_button.setOnClickListener(view ->
        {
            Intent alarm_APP = new Intent(MainScreen.this, Alarm.class);
            startActivity(alarm_APP);
        });

        add_contact_button = (ImageButton) findViewById(R.id.contact_activity);

        add_contact_button.setOnClickListener(view -> {
            Intent contact_APP = new Intent(MainScreen.this, ContactList.class);
            startActivity(contact_APP);
        });

        flash_on_off = (TextView) findViewById(R.id.flash_on_off_text);

        flash_button.setOnClickListener(view ->
        {
            if(!flash)
            {
                flashSwitch(true);
                flash = true;
                flash_on_off.setText("Flash / ON");
            }
            else
            {
                flashSwitch(false);
                flash =  false;
                flash_on_off.setText("Flash / OFF");
            }

        });
    }

    private void flashSwitch(boolean state)
    {
        if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH))
        {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
                String cameraId = null;
                try
                {
                    cameraId = cameraManager.getCameraIdList()[0];
                    cameraManager.setTorchMode(cameraId, state);
                }
                catch (CameraAccessException e)
                {
                    throw new RuntimeException(e);
                }
            }
        }
        else
        {
            Toast.makeText(this, "No flashlight!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateDateTime() {

        Calendar calendar = Calendar.getInstance();
        String dayDate = dateFormat.format(calendar.getTime());
        String monthYear = monthYearFormat.format(calendar.getTime());
        String currentTime = timeFormat.format(calendar.getTime());

        day_and_date.setText(dayDate);
        month_and_year.setText(monthYear);
        hour.setText(currentTime);
    }

    private void CheckAssets()
    {
        if(day_and_date != null) {System.out.println("Day and date text set");}

        if(month_and_year != null) {System.out.println("Month and year text set");}

        if(hour != null) {System.out.println("Hour text set");}

        if(voice_command_button != null) {System.out.println("Voice command button set");}
    }

    private void Speak(TextToSpeech voice, String sentence)
    {
        voice.speak(sentence, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override
    protected void onPause() {

        if(english_voice != null && greek_voice != null)
        {
            english_voice.stop();
            greek_voice.stop();
        }

        super.onPause();
    }

    @Override
    public void onBackPressed()
    {
        System.out.println("Back pressed nothing happening");
    }
}
