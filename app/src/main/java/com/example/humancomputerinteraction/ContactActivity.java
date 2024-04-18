package com.example.humancomputerinteraction;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity
{
    TextView contact_title;
    ListView existent_contacts;
    RelativeLayout empty_contact_layout;
    TextView empty_contact_text;
    Button add_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        contact_title = (TextView) findViewById(R.id.contact_title);
        existent_contacts = (ListView) findViewById(R.id.existant_contacts);
        empty_contact_layout = (RelativeLayout) findViewById(R.id.empty_contact_list);
        empty_contact_text = (TextView) findViewById(R.id.empty_contact_text);
        add_contact = (Button) findViewById(R.id.add_contact);
    }
}