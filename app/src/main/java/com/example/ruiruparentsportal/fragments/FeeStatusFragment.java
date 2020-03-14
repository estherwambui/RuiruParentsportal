package com.example.ruiruparentsportal.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ruiruparentsportal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeeStatusFragment extends Fragment {

    public FeeStatusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fee_status, container, false);
    }
}
