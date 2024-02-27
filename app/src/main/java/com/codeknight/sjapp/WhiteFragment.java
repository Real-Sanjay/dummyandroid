package com.codeknight.sjapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;

public class WhiteFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_white, container, false);

        // Get the ImageView reference
        ImageView imageView = view.findViewById(R.id.imageView);

        // You can perform further operations on the ImageView if needed

        return view;
    }
}
