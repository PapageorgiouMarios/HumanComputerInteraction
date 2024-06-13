package com.example.humancomputerinteraction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.util.Log;

public class Gallery extends AppCompatActivity {

    private ImageView imageView;
    private ImageButton leftButton, rightButton;
    private int[] imageIds =  {R.drawable.aueb100years , R.drawable.aueb, R.drawable.cs };
    private int currentIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        int maxIdx = imageIds.length-1;

        imageView = findViewById(R.id.imageView);
        leftButton = (ImageButton) findViewById(R.id.leftButton);
        rightButton = (ImageButton) findViewById(R.id.rightButton);
        imageView.setImageResource(imageIds[currentIndex]);

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentIndex-1 >= 0) {currentIndex--;}
                imageView.setImageResource(imageIds[currentIndex]);
                Log.i("gallery"," leftButton");
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentIndex+1 <= maxIdx) {currentIndex++;}
                imageView.setImageResource(imageIds[currentIndex]);
                Log.i("gallery"," rightButton");
            }
        });
    }
}
