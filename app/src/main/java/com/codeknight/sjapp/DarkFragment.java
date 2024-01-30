package com.codeknight.sjapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DarkFragment extends Fragment {

    public DarkFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dark, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find the TextView in the fragment layout
        TextView tvFragmentLabel = view.findViewById(R.id.tvFragmentLabel);

        // Set an OnClickListener on the TextView
        tvFragmentLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch SecondActivity when TextView is clicked
                Intent intent = new Intent(getActivity(), SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}


