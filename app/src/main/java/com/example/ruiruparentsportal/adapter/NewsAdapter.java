package com.example.ruiruparentsportal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ruiruparentsportal.R;
import com.example.ruiruparentsportal.model.NewsItem;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<NewsItem> newsItemList;
    private Context context;


    public NewsAdapter(List<NewsItem> newsItemList, Context context) {
        this.newsItemList = newsItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(context).inflate(R.layout.news_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsItem currentItem = newsItemList.get(position);
        holder.title.setText(currentItem.getTitle());
        holder.content.setText(currentItem.getContent());
    }

    @Override
    public int getItemCount() {
        return newsItemList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView title, content;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvNewsTitle);
            content = itemView.findViewById(R.id.tvNewsContent);
        }
    }
}
