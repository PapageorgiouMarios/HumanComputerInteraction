package com.example.humancomputerinteraction;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EmergencyCall extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    private ArrayList<EmergencyContactModel> emergencyContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_call);

        ListView contact_list = findViewById(R.id.contact_list);
        emergencyContacts = setIconNameNumber();
        EmergencyAdapter contact_adapter = new EmergencyAdapter(EmergencyCall.this, emergencyContacts);

        contact_list.setAdapter(contact_adapter);
        contact_list.setOnItemClickListener(this);
    }

    private ArrayList<EmergencyContactModel> setIconNameNumber()
    {
        emergencyContacts = new ArrayList<>();

        emergencyContacts.add(new EmergencyContactModel(R.drawable.police, "ΑΣΤΥΝΟΜΊΑ",
                "100"));


        emergencyContacts.add(new EmergencyContactModel(R.drawable.fire, "ΠΥΡΟΣΒΕΤΣΙΚΉ",
                "199"));


        emergencyContacts.add(new EmergencyContactModel(R.drawable.ekab, "ΆΜΕΣΗ ΒΟΉΘΕΙΑ",
                "166"));

        return emergencyContacts;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    {
        EmergencyContactModel model = emergencyContacts.get(position);
        Toast.makeText(EmergencyCall.this, "Clicked: " + position, Toast.LENGTH_SHORT).show();
    }
}