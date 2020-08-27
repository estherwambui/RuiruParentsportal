package com.example.ruiruparentsportal.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ruiruparentsportal.R;
import com.example.ruiruparentsportal.model.NewsItem;
import com.example.ruiruparentsportal.utils.AppUtils;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<NewsItem> newsItemList;
    private Context context;
    private static final String TAG = "NewsAdapter";

    private OnItemClickLisener onItemClickLisener;

    public interface OnItemClickLisener {
        void onItemClick(int position, NewsItem pdfDownload);
    }

    public void setOnItemClickLisener(OnItemClickLisener onItemClickLisener) {
        this.onItemClickLisener = onItemClickLisener;
    }

    public NewsAdapter(List<NewsItem> newsItemList, Context context) {
        this.newsItemList = newsItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsItem currentItem = newsItemList.get(position);
        Log.i(TAG, "onBindViewHolder: This item: " + currentItem);
        holder.title.setText(currentItem.getEvent());
        holder.content.setText(currentItem.getDescription());
        holder.tvNewsDate.setText(AppUtils.formatDate(currentItem.getDate()));

        holder.itemView.setOnClickListener(v -> onItemClickLisener.onItemClick(position, currentItem));

    }

    @Override
    public int getItemCount() {
        return newsItemList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView title, content, tvNewsDate;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvNewsTitle);
            content = itemView.findViewById(R.id.tvNewsShortDesc);
            tvNewsDate = itemView.findViewById(R.id.tvNewsDate);
        }
    }
}
