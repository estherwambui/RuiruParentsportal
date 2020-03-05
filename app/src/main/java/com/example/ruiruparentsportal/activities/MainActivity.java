package com.example.ruiruparentsportal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.example.ruiruparentsportal.R;

public class MainActivity extends AppCompatActivity {
    CardView results,status,structure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        results= findViewById(R.id.result);
        status= findViewById(R.id.fee);
        structure=findViewById(R.id.fstructure);

        results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,ResultsActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
