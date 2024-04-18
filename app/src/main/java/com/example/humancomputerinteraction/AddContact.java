package com.example.humancomputerinteraction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;

public class AddContact extends AppCompatActivity
{
    TextView description;
    ImageView person_face;

    EditText first_name_field;
    EditText last_name_field;
    EditText phone_number_field;
    Button add_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        description = (TextView) findViewById(R.id.describe_add);
        person_face = (ImageView) findViewById(R.id.bigImage2);

        first_name_field = (EditText) findViewById(R.id.contact_first_name_textfield);
        last_name_field = (EditText) findViewById(R.id.contact_last_name_textfield);
        phone_number_field = (EditText) findViewById(R.id.contact_number_field);

        add_contact = (Button) findViewById(R.id.add_new_contact_button);

        String fn_input = first_name_field.getText().toString().trim();
        String ln_input = last_name_field.getText().toString().trim();
        String pn_input = phone_number_field.getText().toString().trim();

        add_contact.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               // ADD
            }
        });
        
    }

    private Contact createContact(String fn, String ln, String pn)
    {
        return new Contact(fn, ln, pn);
    }

    private boolean checkFields()
    {
        String first_name_input = first_name_field.getText().toString().trim();
        String last_name_input = last_name_field.getText().toString().trim();
        String phone_number_input = phone_number_field.getText().toString().trim();

        return !(first_name_input.isEmpty() || last_name_input.isEmpty() || phone_number_input.isEmpty());

    }
}