package com.example.humancomputerinteraction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class MainScreen extends AppCompatActivity {

    //------Top of the screen------
    ImageButton my_info_button;
    TextView my_info_text;
    ImageButton language_button;
    //-----------------------------

    //-------Date-------
    TextView day_and_date;
    //------------------

    //-----Month and Year-----
    TextView month_and_year;
    //------------------------

    //------Time------
    TextView hour;
    //----------------

    //------Voice command Button------
    ImageButton voice_command_button;
    //--------------------------------

    String language = "ENG";
    boolean english = true;
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
    ImageButton help_row_1_button;
    ImageButton notebook_button;
    ImageButton weather_button;
    ImageButton wake_button;
    ImageButton help_row_2_button;
    ImageButton photo_button;
    ImageButton video_button;
    ImageButton gallery_button;
    ImageButton help_row_3_button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        //------------------Top of the screen------------------
        my_info_button = (ImageButton) findViewById(R.id.my_info);
        my_info_text = (TextView) findViewById(R.id.my_info_text);
        language_button = (ImageButton) findViewById(R.id.language_button);
        //------------------------------------------------------

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
        //------------------------------------------------------------------------

        System.out.println("Check .xml file assets");
        CheckAssets();

        language_button.setOnClickListener(view -> ChangeLanguage());

        updateDateTime();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    try {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateDateTime();
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        english_voice = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != TextToSpeech.ERROR)
                {
                    english_voice.setLanguage(Locale.ENGLISH);
                }
            }
        });

        greek_voice = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != TextToSpeech.ERROR)
                {
                    greek_voice.setLanguage(new Locale("el", "GR"));
                }
            }
        });

        //-------------------------APPS----------------------------------
        call_button = (ImageButton) findViewById(R.id.call_button);
        gps_button = (ImageButton) findViewById(R.id.gps_button);
        sos_button = (ImageButton) findViewById(R.id.sos_button);
        help_row_1_button = (ImageButton) findViewById(R.id.help_row_1);
        notebook_button = (ImageButton) findViewById(R.id.notebook_button);
        weather_button = (ImageButton) findViewById(R.id.weather_button);
        wake_button = (ImageButton) findViewById(R.id.wake_button);
        help_row_2_button = (ImageButton) findViewById(R.id.help_row_2);
        photo_button = (ImageButton) findViewById(R.id.photo_button);
        video_button = (ImageButton) findViewById(R.id.video_button);
        gallery_button = (ImageButton) findViewById(R.id.gallery_button);
        help_row_3_button = (ImageButton) findViewById(R.id.help_row_3);
        //----------------------------------------------------------------

        notebook_button.setOnClickListener(view -> {
            Intent notebook_APP = new Intent(MainScreen.this, Notebook.class);
            startActivity(notebook_APP);
        });
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
        if(my_info_button != null) {System.out.println("My info button set");}

        if(my_info_text != null) {System.out.println("My info text set");}

        if(language_button != null) {System.out.println("Language button set");}

        if(day_and_date != null) {System.out.println("Day and date text set");}

        if(month_and_year != null) {System.out.println("Month and year text set");}

        if(hour != null) {System.out.println("Hour text set");}

        if(voice_command_button != null) {System.out.println("Voice command button set");}
    }

    private void ChangeLanguage()
    {
        if(language.equals("ENG") && english)
        {
            Speak(greek_voice, "Ελληνικά");

            language_button.setImageResource(R.drawable.greek);
            english = false;
            language = "GR";
            locale = new Locale("el", "GR");

            dateFormat = new SimpleDateFormat("EEEE dd", locale);
            monthYearFormat = new SimpleDateFormat("MMMM yyyy", locale);
            timeFormat = new SimpleDateFormat("HH:mm", locale);
        }
        else if(language.equals("GR") && !english)
        {
            Speak(english_voice, "English");

            language_button.setImageResource(R.drawable.english);
            english = true;
            language = "ENG";
            locale = Locale.ENGLISH;

            dateFormat = new SimpleDateFormat("EEEE dd", locale);
            monthYearFormat = new SimpleDateFormat("MMMM yyyy", locale);
            timeFormat = new SimpleDateFormat("HH:mm", locale);
        }
        else
        {
            System.out.println("Error in language change!");
        }

        System.out.println("Language: " + language);
        System.out.println("English boolean set to: " + english);
        System.out.println("Locale: " + locale.getDisplayLanguage());
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
}
