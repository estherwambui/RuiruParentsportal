package com.example.ruiruparentsportal.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ruiruparentsportal.R;
import com.example.ruiruparentsportal.adapter.NewsAdapter;
import com.example.ruiruparentsportal.interfaces.ApiService;
import com.example.ruiruparentsportal.model.NewsItem;
import com.example.ruiruparentsportal.model.NewsResponse;
import com.example.ruiruparentsportal.utils.AppUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsAndEventsFragment extends Fragment {
    private NewsAdapter adapter;
    private ApiService service;
    private RecyclerView recyclerView;
    private static final String TAG = "NewsAndEventsFragment";
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_news, container, false);
        service = AppUtils.getApiService();
        recyclerView = root.findViewById(R.id.newsRecyclerView);
        mSwipeRefreshLayout = root.findViewById(R.id.mSwipeRefreshLayout);

        retrieveNews();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveNews();
            }
        });

        return root;
    }

    private void retrieveNews() {
        mSwipeRefreshLayout.setRefreshing(true);
        service.getNewsArticles(AppUtils.GET_NEWS_TOKEN).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    populateData(response.body().getArticles());
                    Log.w(TAG, "onResponse: Populating data: " + response.body().getArticles());
                } else {
                    Toast.makeText(getContext(), "An error has occurred!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                mSwipeRefreshLayout.setRefreshing(false);
                Log.e(TAG, "onFailure: Failed to get news: " + t.getMessage());
            }
        });
    }

    private void populateData(List<NewsItem> articles) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsAdapter(articles, getContext());
        recyclerView.setAdapter(adapter);
        Log.w(TAG, "populateData: Adding data: " + articles.size());
        adapter.notifyDataSetChanged();
    }
}
