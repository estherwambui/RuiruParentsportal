package com.example.ruiruparentsportal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ruiruparentsportal.R;
import com.example.ruiruparentsportal.adapter.DownloadsAdapter;
import com.example.ruiruparentsportal.model.PDFDownload;
import com.example.ruiruparentsportal.utils.AppUtils;

import java.io.File;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadsFragment extends Fragment {

    private ArrayList<PDFDownload> pdfDownloads = new ArrayList<>();
    private RelativeLayout rlNoDownloads;

    public DownloadsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_downloads, container, false);

        RecyclerView downloadsRecyclerview = root.findViewById(R.id.rvDownloads);
        rlNoDownloads = root.findViewById(R.id.rlNoDownloads);
        downloadsRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        DownloadsAdapter adapter = new DownloadsAdapter(pdfDownloads, getContext());

        downloadsRecyclerview.setAdapter(adapter);

        pdfDownloads.addAll(AppUtils.getDownloadedFiles(getContext()));

        if (pdfDownloads.size() == 0) {
            AppUtils.showView(rlNoDownloads);
        } else {
            AppUtils.hideView(rlNoDownloads);
        }

        adapter.notifyDataSetChanged();

        adapter.setOnItemClickLisener((position, pdfDownload) -> {
            String pdfPath = getContext().getExternalFilesDir("rghs/").getAbsolutePath();
            File pdfFile = new File(pdfPath, pdfDownload.getFilename());
            AppUtils.openPdf(getContext(), pdfFile);
        });

        return root;
    }
}
