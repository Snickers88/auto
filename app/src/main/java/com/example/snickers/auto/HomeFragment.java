package com.example.snickers.auto;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.RelativeLayout;

import com.example.snickers.auto.Adapter.AutoAdapter;
import com.example.snickers.auto.DB.ContactDao;
import com.example.snickers.auto.DB.ContactModel;
import com.example.snickers.auto.DB.DBHelper;
import com.jjoe64.graphview.series.DataPoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class HomeFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView rv;
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ContactDao contactdao = new ContactDao(v);
        AutoAdapter autoAdapter = new AutoAdapter();
        ArrayList<ContactModel> contactModels = new ArrayList<>();
        contactModels.addAll(contactdao.select());


        Collections.sort(contactModels, (o1, o2) -> {
            if (o1.getDate() == null || o2.getDate() == null)
                return 0;
            return o1.getDate().compareTo(o2.getDate());
        });
        autoAdapter.setDate(contactModels);

        rv = v.findViewById(R.id.rvListview);
        rv.setLayoutManager(new LinearLayoutManager(v.getContext()));
        rv.setAdapter(autoAdapter);
        FloatingActionButton t = v.findViewById(R.id.floatingActionButton2);
        t.setOnClickListener(t1 -> {
            Intent in = new Intent(v.getContext(), Create_note.class);
            startActivity(in);
        });

        return v;


    }



    public static class MyObject implements Comparable<ContactModel> {

        private Date dateTime;

        public Date getDateTime() {
            return dateTime;
        }

        public void setDateTime(Date datetime) {
            this.dateTime = datetime;
        }


        @Override
        public int compareTo(@NonNull ContactModel o) {
            if (getDateTime() == null || o.getDate() == null)
                return 0;
            SimpleDateFormat format = new SimpleDateFormat("dd:MM:yyyy");
            Date date1 = null;
            try {
                date1 = format.parse(o.getDate());


            } catch (ParseException e) {
                e.printStackTrace();
            }
            return getDateTime().compareTo(date1);
        }
    }
}
