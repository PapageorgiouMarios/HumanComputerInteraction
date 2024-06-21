package com.example.humancomputerinteraction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PhoneCallManual extends AppCompatActivity
{
    private TextView textViewNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_phone_call);
        textViewNumber = findViewById(R.id.textViewNumber);
    }

    public void appendToNumber(View view)
    {
        String buttonValue = ((TextView) view).getText().toString();
        String currentNumber = textViewNumber.getText().toString();
        textViewNumber.setText(currentNumber + buttonValue);
    }

    public void backspace(View view)
    {
        String currentNumber = textViewNumber.getText().toString();
        if (!currentNumber.isEmpty()) {
            textViewNumber.setText(currentNumber.substring(0, currentNumber.length() - 1));
        }
    }

    public void makePhoneCall(View view)
    {
        String phoneNumber = textViewNumber.getText().toString().trim();
        if (!phoneNumber.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        }else {
            Log.i("manual phone call", "no number");
        }
    }
}

