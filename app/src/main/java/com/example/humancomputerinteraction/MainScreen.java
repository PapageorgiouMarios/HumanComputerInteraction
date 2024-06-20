package com.example.humancomputerinteraction;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class MainScreen extends AppCompatActivity{

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
    ImageButton mic;
    ImageButton flash_button;
    boolean flash = false;

    TextView flash_on_off;

    static final int REQUEST_SPEECH_RECOGNIZER = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        getSupportActionBar().hide();

        day_and_date = (TextView) findViewById(R.id.day_date_text);
        month_and_year = (TextView) findViewById(R.id.month_year_text);
        hour = (TextView) findViewById(R.id.time_text);
        flash_button = (ImageButton) findViewById(R.id.flash_button);

        System.out.println("Check .xml file assets");//for debug only
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

        english_voice = new TextToSpeech(this, i -> {//not used
            if (i != TextToSpeech.ERROR) {
                english_voice.setLanguage(Locale.ENGLISH);
            }
        });

        greek_voice = new TextToSpeech(this, i -> {//not used
            if (i != TextToSpeech.ERROR) {
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
        flash_on_off = (TextView) findViewById(R.id.flash_on_off_text);
        mic = (ImageButton) findViewById(R.id.microphone_button);
        add_contact_button = (ImageButton) findViewById(R.id.contact_activity);
        //----------------------------------------------------------------

        call_button.setOnClickListener(view -> { openCallApp();});
        notebook_button.setOnClickListener(view -> {openNotebookApp();});
        gps_button.setOnClickListener(view -> {openGoogleMaps();});
        sos_button.setOnClickListener(view -> {openSosApp(); });
        wake_button.setOnClickListener(view ->{openAlarmApp(); });
        video_button.setOnClickListener(view ->{openVideo(); });
        add_contact_button.setOnClickListener(view -> {openContactsApp();});
        gallery_button.setOnClickListener(view -> {openGalleryApp();});
        photo_button.setOnClickListener(view ->{openCamera();});
        weather_button.setOnClickListener(view -> {openWeatherApp();});
        flash_button.setOnClickListener(view -> {
            if (!flash) {
                flashSwitch(true);
                flash = true;
                flash_on_off.setText("Flash / ON");
            } else {
                flashSwitch(false);
                flash = false;
                flash_on_off.setText("Flash / OFF");
            }
        });
        mic.setOnClickListener((view ->{recogniseSpeech();}));
    }

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
                    String voiceCommand = result.get(0);
                    Log.d("m",voiceCommand);
                    voiceCommandExecution(voiceCommand);
                }
            }
        }
    }

    private void flashSwitch(boolean state) {
        if (this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
                String cameraId = null;
                try {
                    cameraId = cameraManager.getCameraIdList()[0];
                    cameraManager.setTorchMode(cameraId, state);
                } catch (CameraAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
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

    private void CheckAssets() {//maybe we should delete
        if (day_and_date != null) {System.out.println("Day and date text set");}
        if (month_and_year != null) {System.out.println("Month and year text set");}
        if (hour != null) {System.out.println("Hour text set");}
        if (voice_command_button != null) {System.out.println("Voice command button set");}
    }

    private void openCamera() {
        Log.i("main","pic");
        Intent takePictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
            startActivity(takePictureIntent);
    }

    private void openVideo() {
        Log.i("main","vid");
        Intent startVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (startVideoIntent.resolveActivity(getPackageManager()) == null) {
            return;
        }
        startActivity(startVideoIntent);
    }

    private void Speak(TextToSpeech voice, String sentence) {//is not used
        voice.speak(sentence, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override
    protected void onPause() { //is not used

        if (english_voice != null && greek_voice != null) {
            english_voice.stop();
            greek_voice.stop();
        }

        super.onPause();
    }

    @Override
    public void onBackPressed() {System.out.println("Back pressed nothing happening");}

    protected void openCallApp(){startActivity(new Intent(MainScreen.this, PhoneCall.class));}
    protected void openNotebookApp(){startActivity(new Intent(MainScreen.this, Notebook.class));}
    protected void openSosApp(){startActivity(new Intent(MainScreen.this, EmergencyCall.class));}
    protected void openAlarmApp(){startActivity(new Intent(MainScreen.this, Alarm.class));}
    protected void openContactsApp(){startActivity(new Intent(MainScreen.this, ContactList.class));}
    protected void openWeatherApp(){startActivity(new Intent(MainScreen.this, Weather.class));}
    protected void openGalleryApp()
    {
        //old start activity code, incase a problem arises vvv
        /* Intent galleryApp = new Intent(MainScreen.this, Gallery.class);
        startActivity(galleryApp);*/
        startActivity(new Intent(MainScreen.this, Gallery.class));
    }
    private void openGoogleMaps() {
        Intent mapIntent = new Intent(Intent.ACTION_MAIN);
        mapIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        mapIntent.setComponent(new ComponentName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity"));
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(this, "Google Maps app is not installed", Toast.LENGTH_SHORT).show();
        }

    }

    protected void voiceCommandExecution(String voiceCommand)
    {
        String vc = voiceCommand.toLowerCase()
                .replaceAll("open","").replaceAll("app","").replaceAll("up","")
                .trim();
        Log.i("VC",vc);

        switch(vc){
            case "phone":
            case "call":
                openCallApp();break;
            case "note":
            case "notes":
            case "notebook":
            case "notepad":
                openNotebookApp(); break;
            case "alarm":
            case "alarms":
            case "clock":
            case "clocks":
                openAlarmApp(); break;
            case "contact":
            case "contacts":
                openContactsApp(); break;
            case "weather":
                openWeatherApp(); break;
            case "gallery":
            case "photo":
            case "photos":
                openGalleryApp(); break;
        }
    }
}