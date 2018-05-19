package com.example.snickers.auto;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Reminder extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_reminder, container, false);
        FloatingActionButton t = view.findViewById(R.id.floatingActionButton3);
        t.setOnClickListener(t1 -> {
            Intent in = new Intent(view.getContext(), Create_Reminder.class);
            startActivity(in);
        });

        return view;
    }

}

