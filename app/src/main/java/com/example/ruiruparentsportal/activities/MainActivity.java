package com.example.ruiruparentsportal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.example.ruiruparentsportal.FeeStatusActivity;
import com.example.ruiruparentsportal.FeeStructureActivity;
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

        results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,ResultsActivity.class);
                startActivity(intent);
            }
        });
        structure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, FeeStructureActivity.class);
                startActivity(intent);

            }
        });
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, FeeStatusActivity.class);
                startActivity(intent);

            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,NewsActivity.class);
                startActivity(intent);
            }
        });

    }

}
