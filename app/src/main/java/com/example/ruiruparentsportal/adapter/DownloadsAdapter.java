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
import com.example.ruiruparentsportal.model.PDFDownload;

import java.util.List;

public class DownloadsAdapter extends RecyclerView.Adapter<DownloadsAdapter.NewsViewHolder> {

    private List<PDFDownload> pdfItemList;
    private Context context;
    private static final String TAG = "NewsAdapter";
    private OnItemClickLisener onItemClickLisener;

    public interface OnItemClickLisener {
        void onItemClick(int position, PDFDownload pdfDownload);
    }

    public void setOnItemClickLisener(OnItemClickLisener onItemClickLisener) {
        this.onItemClickLisener = onItemClickLisener;
    }

    public DownloadsAdapter(List<PDFDownload> pdfItemList, Context context) {
        this.pdfItemList = pdfItemList;
        this.context = context;
        Log.e(TAG, "NewsAdapter: Adapter data: " + pdfItemList.size());
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_download, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        PDFDownload currentItem = pdfItemList.get(position);
        Log.i(TAG, "onBindViewHolder: This item: " + currentItem);
        holder.title.setText(currentItem.getFilename());

        holder.itemView.setOnClickListener(v -> onItemClickLisener.onItemClick(position, currentItem));
    }

    @Override
    public int getItemCount() {
        return pdfItemList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvFileName);
        }
    }
}
