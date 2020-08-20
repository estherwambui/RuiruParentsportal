package com.example.ruiruparentsportal.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ruiruparentsportal.R;
import com.example.ruiruparentsportal.model.NewsItem;

public class NewsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        NewsItem newsItem = (NewsItem) getIntent().getExtras().getSerializable("news");

        findViewById(R.id.btnBack).setOnClickListener(v -> onBackPressed());

        ((TextView) findViewById(R.id.tvNewsTitle)).setText(newsItem.getEvent());
        ((TextView) findViewById(R.id.tvNewsDate)).setText(newsItem.getDate());
        ((TextView) findViewById(R.id.tvNewsContent)).setText(newsItem.getDescription());
    }
}
