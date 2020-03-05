package com.example.ruiruparentsportal.activities;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ruiruparentsportal.R;
import com.example.ruiruparentsportal.adapter.NewsAdapter;
import com.example.ruiruparentsportal.model.NewsItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    private NewsAdapter adapter;
    private List<NewsItem> newsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar t = findViewById(R.id.newsToolBar);
        setSupportActionBar(t);

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.newsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsAdapter(newsList, this);

        recyclerView.setAdapter(adapter);

        populateRecyclerView();

    }

    private void populateRecyclerView() {
        NewsItem[] newses = {
                new NewsItem("New Bus", "This is the content"),
                new NewsItem("School Fees Abolished", "No paying fees this term"),
                new NewsItem("Free lunch", "This is the content"),
        };
        newsList.addAll(Arrays.asList(newses));
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
