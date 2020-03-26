package com.example.ruiruparentsportal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ruiruparentsportal.R;
import com.example.ruiruparentsportal.adapter.NewsAdapter;
import com.example.ruiruparentsportal.model.NewsItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewsAndEventsFragment extends Fragment {
    private NewsAdapter adapter;
    private List<NewsItem> newsList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_news, container, false);
        setUpRecyclerView(root);

        return root;
    }

    private void setUpRecyclerView(View root) {
        RecyclerView recyclerView = root.findViewById(R.id.newsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsAdapter(newsList, getContext());

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

}
