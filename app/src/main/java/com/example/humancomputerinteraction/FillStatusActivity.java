package com.example.humancomputerinteraction;

import static android.Manifest.permission.READ_PHONE_NUMBERS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.telephony.TelephonyManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;

public class FillStatusActivity extends AppCompatActivity {

    TextView welcome_message;
    ImageButton language_button;
    TextView change_language_text;

    TextInputEditText first_name;
    TextInputEditText last_name;
    TextInputEditText address;
    TextInputEditText mail_code;
    TextView my_phone_number;
    Button check_proceed;

    String language = "ENG";
    boolean english = true;
    Locale locale = Locale.ENGLISH;

    TextToSpeech english_voice;
    TextToSpeech greek_voice;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_status);

        my_phone_number = (TextView) findViewById(R.id.my_number);
        GetNumber();

        welcome_message = (TextView) findViewById(R.id.welcome_text);
        language_button = (ImageButton) findViewById(R.id.language_button);
        change_language_text = (TextView) findViewById(R.id.change_language_text);
        first_name = (TextInputEditText) findViewById(R.id.first_name_textfield);
        last_name = (TextInputEditText)findViewById(R.id.last_name_textfield);
        address = (TextInputEditText)findViewById(R.id.address);
        mail_code = (TextInputEditText)findViewById(R.id.mail_code);
        check_proceed = (Button) findViewById(R.id.check_proceed);

        language_button.setOnClickListener(view -> ChangeLanguage());

        check_proceed.setOnClickListener(view -> CheckFields());
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

            ChangeAssetsToGreek();
        }
        else if(language.equals("GR") && !english)
        {
            Speak(english_voice, "English");

            language_button.setImageResource(R.drawable.english);
            english = true;
            language = "ENG";
            locale = Locale.ENGLISH;

            ChangeAssetsToEnglish();
        }
        else
        {
            System.out.println("Error in language change!");
        }
    }

    private void ChangeAssetsToGreek()
    {
        welcome_message.setText("Kαλωσήρθατε");
        change_language_text.setText("Αλλαγή στα Αγγλικά");
        first_name.setHint("Πληκτρολογήστε το όνομα σας");
        last_name.setHint("Πληκτρολογήστε το επώνυμο σας");
        address.setHint("Πληκτρολογήστε τη διεύθυνση σας με το νούμερο της");
        mail_code.setHint("Πληκτρολογήστε τον ταχυδρομικό σας κώδικα");
        check_proceed.setText("Έλεγχος και Συνέχεια");
    };

    private void ChangeAssetsToEnglish()
    {
        welcome_message.setText("Welcome");
        change_language_text.setText("Change to Greek");
        first_name.setHint("Type first name here");
        last_name.setHint("Type last name here");
        address.setHint("Type address with number here");
        mail_code.setHint("Type mail code here");
        check_proceed.setText("CHECK AND PROCEED");
    };

    private void CheckFields()
    {
        String firstName = String.valueOf(first_name.getText());
        String lastName = String.valueOf(last_name.getText());

        String regex = "[^a-zA-Z]";

        if(!firstName.matches(regex))
        {
            System.out.println("Error: First name invalid");
        }
        else
        {
            System.out.println("First name valid");
        }

        if(!lastName.matches(regex))
        {
            System.out.println("Error: Last name invalid");
        }
        else
        {
            System.out.println("Last name valid");
        }

        String address_part1 = String.valueOf(address.getText());
        String mail_number_part2 = String.valueOf(mail_code);
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

    public void GetNumber() {

        if (ActivityCompat.checkSelfPermission(this, READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, READ_PHONE_NUMBERS) ==
                        PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {

            TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            String phoneNumber = telephonyManager.getLine1Number();
            my_phone_number.setText(phoneNumber);
        }
        else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                requestPermission();
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void requestPermission()
    {
        requestPermissions(new String[]{READ_SMS, READ_PHONE_NUMBERS, READ_PHONE_STATE}, 100);
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100)
        {
            TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, READ_SMS) !=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            String phoneNumber = telephonyManager.getLine1Number();
            my_phone_number.setText(phoneNumber);
        }
        else
        {
            throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }
}