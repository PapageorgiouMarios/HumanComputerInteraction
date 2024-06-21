package com.example.humancomputerinteraction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.Manifest;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class PhoneCall extends AppCompatActivity implements PhoneCallContactAdapter.CallButtonClickListener
{
    protected EditText phone_input;
    protected Button call_input;
    protected Button call_manually_button;
    protected static int PERMISSION_CODE = 100;
    protected ListView who_to_call_list;
    protected static ArrayList<String> contacts;
    protected SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_call);

        who_to_call_list = (ListView) findViewById(R.id.saved_contacts);
        call_manually_button = (Button) findViewById(R.id.call_manually);
        //phone_input = (EditText) findViewById(R.id.type_phone);
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

        call_manually_button.setOnClickListener(view -> {callManually();});

        contacts = new ArrayList<>();

        sp = this.getSharedPreferences("com.example.humancomputerinteraction", Context.MODE_PRIVATE);
        HashSet<String> contactSet = (HashSet<String>) sp.getStringSet("contacts", null);

        if (contactSet == null || contactSet.isEmpty()){ contacts = new ArrayList<>(); }
        else{ contacts = new ArrayList<>(contactSet); }

        PhoneCallContactAdapter who_to_call_adapter = new PhoneCallContactAdapter(PhoneCall.this, contacts);
        who_to_call_list.setAdapter(who_to_call_adapter);
        who_to_call_adapter.setCallButtonClickListener(this);

    }

    private void makePhoneCall(String number)
    {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + number));
        startActivity(callIntent);
    }
    @Override
    public void onCallButtonClicked(String phoneNumber)
    {
        makePhoneCall(phoneNumber);
    }

    public void callManually()
    {
        startActivity(new Intent(PhoneCall.this, PhoneCallManual.class));
    }

}