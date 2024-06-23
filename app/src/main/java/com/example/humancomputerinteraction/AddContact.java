package com.example.humancomputerinteraction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.HashSet;

public class AddContact extends AppCompatActivity
{
    TextView description;
    ImageView person_face;
    EditText first_name_field;
    EditText last_name_field;
    EditText phone_number_field;
    Button add_contact;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        sp = this.getSharedPreferences("com.example.humancomputerinteraction", Context.MODE_PRIVATE);

        description = (TextView) findViewById(R.id.describe_add);
        person_face = (ImageView) findViewById(R.id.bigImage2);

        first_name_field = (EditText) findViewById(R.id.contact_first_name_textfield);
        last_name_field = (EditText) findViewById(R.id.contact_last_name_textfield);
        phone_number_field = (EditText) findViewById(R.id.contact_number_field);

        add_contact = (Button) findViewById(R.id.add_new_contact_button);

        add_contact.setOnClickListener(view ->
        {
            String fn_input = first_name_field.getText().toString().trim();
            String ln_input = last_name_field.getText().toString().trim();
            String pn_input = phone_number_field.getText().toString().trim();

            if(checkFields())
            {
                Log.d("Marios", "Contact parameters valid! Ready to save contact");
                Contact new_c = createContact(fn_input, ln_input, pn_input);
                new_c.printStats();
                Log.d("Marios", "First Name: " + fn_input);
                Log.d("Marios", "Last Name: " + ln_input);
                Log.d("Marios", "Phone Number: " + pn_input);

                Gson gson = new Gson();
                String json = gson.toJson(new_c);
                Log.d("Marios", "Json file of contact: " + json);
                ContactList.contacts.add(json);
                Log.d("Marios", "Contact List: " + ContactList.contacts);
                HashSet<String> contactSet = new HashSet<>(ContactList.contacts);
                sp.edit().putStringSet("contacts", contactSet).apply();

                Intent back_to_contacts = new Intent(AddContact.this, ContactList.class);
                startActivity(back_to_contacts);

            }
            else
            {
                Log.d("Marios", "Contact Parameters invalid! Check again");
                Log.d("Marios", "First Name: " + fn_input);
                Log.d("Marios", "Last Name: " + ln_input);
                Log.d("Marios", "Phone Number: " + pn_input);
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