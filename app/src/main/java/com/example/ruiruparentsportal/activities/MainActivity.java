package com.example.ruiruparentsportal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;

import com.example.ruiruparentsportal.R;

public class MainActivity extends AppCompatActivity {
    CardView results,status,structure,news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        results= findViewById(R.id.result);
        status= findViewById(R.id.fee);
        structure=findViewById(R.id.fstructure);
        news=findViewById(R.id.news);

    }

}
