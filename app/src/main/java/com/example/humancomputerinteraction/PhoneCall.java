package com.example.humancomputerinteraction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.Manifest;

public class PhoneCall extends AppCompatActivity
{
    EditText phone_input;
    Button call_input;
    static int PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_call);

        phone_input = (EditText) findViewById(R.id.type_phone);
        call_input = (Button) findViewById(R.id.call_button);

        if(ContextCompat.checkSelfPermission(PhoneCall.this, Manifest.permission.CALL_PHONE)
        != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(PhoneCall.this, new String[]{Manifest.permission.CALL_PHONE},
            PERMISSION_CODE);
        }

        call_input.setOnClickListener(view ->
        {
            String number_to_call = phone_input.getText().toString();
            makePhoneCall(number_to_call);
        });

    }

    private void makePhoneCall(String number)
    {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + number));
        startActivity(callIntent);
    }
}