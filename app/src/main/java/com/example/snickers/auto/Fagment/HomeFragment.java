package com.example.snickers.auto.Fagment;

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

import com.example.snickers.auto.Adapter.AutoAdapter;
import com.example.snickers.auto.Create.Create_note;
import com.example.snickers.auto.DB.contacts.ContactDao;
import com.example.snickers.auto.DB.contacts.ContactModel;
import com.example.snickers.auto.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class HomeFragment extends Fragment {
    private AutoAdapter autoAdapter = new AutoAdapter();
    private ArrayList<ContactModel> contactModels = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView rv;
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ContactDao contactdao = new ContactDao(v);
        contactModels.addAll(contactdao.select());

        Collections.sort(contactModels, (o1, o2) -> {
            if (o1.getDate() == null || o2.getDate() == null)

                return 0;
            SimpleDateFormat format = new SimpleDateFormat("dd:MM:yyyy");
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = format.parse(o1.getDate());
                date2 = format.parse(o2.getDate());

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date1.compareTo(date2);
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
            Date date2 = null;
            try {
                date1 = format.parse(o.getDate());
                date2 = format.parse(getDateTime().toString());

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date2.compareTo(date1);
        }
    }
}
