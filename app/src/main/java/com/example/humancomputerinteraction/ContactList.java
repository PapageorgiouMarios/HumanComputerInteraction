package com.example.humancomputerinteraction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

public class ContactList extends AppCompatActivity {

    ListView existent_contacts;
    RelativeLayout empty_contact_layout;
    TextView empty_contact_text;
    Button add_contact;
    static ArrayList<String> contacts;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        existent_contacts = (ListView) findViewById(R.id.existant_contacts);
        empty_contact_layout = (RelativeLayout) findViewById(R.id.no_contacts_layout);
        empty_contact_text = (TextView) findViewById(R.id.no_contacts_text);
        add_contact = (Button) findViewById(R.id.add_contact_button);

        contacts = new ArrayList<>();

        sp = this.getSharedPreferences("com.example.humancomputerinteraction", Context.MODE_PRIVATE);
        HashSet<String> contactSet = (HashSet<String>) sp.getStringSet("contacts", null);

        if (contactSet == null || contactSet.isEmpty())
        {
            contacts = new ArrayList<>();
        }
        else
        {
            contacts = new ArrayList<>(contactSet);
        }

        ContactAdapter reminder_adapter = new ContactAdapter(ContactList.this, contacts);
        existent_contacts.setAdapter(reminder_adapter);

        add_contact.setOnClickListener(view -> {
            Intent add_contact_activity = new Intent(ContactList.this, AddContact.class);
            startActivity(add_contact_activity);
        });
    }
}