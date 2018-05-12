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

import java.util.ArrayList;

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


        sort(contactModels);
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

    void sort(ArrayList<ContactModel> contactModels) {

        for (int j = 0; j < contactModels.size(); j++) {
            String date11[] = contactModels.get(j).getDate().split(":");
            for (int i = j; i < contactModels.size(); i++) {
                String date2[] = contactModels.get(i).getDate().split(":");
                if (Integer.parseInt(date11[2]) > Integer.parseInt(date2[2])) {
                    ContactModel item = contactModels.get(i);
                    contactModels.remove(i);
                    contactModels.add(i, contactModels.get(j));
                    int t = contactModels.size();
                    contactModels.remove(j);
                    contactModels.add(j, item);
                }
            }
        }


        for (int j = 0; j < contactModels.size(); j++) {
            String date11[] = contactModels.get(j).getDate().split(":");
            for (int i = j; i < contactModels.size(); i++) {
                String date2[] = contactModels.get(i).getDate().split(":");
                if (Integer.parseInt(date11[1]) > Integer.parseInt(date2[1]) && Integer.parseInt(date11[2]) == Integer.parseInt(date2[2])) {
                    ContactModel item = contactModels.get(i);
                    contactModels.remove(i);
                    contactModels.add(i, contactModels.get(j));
                    int t = contactModels.size();
                    contactModels.remove(j);
                    contactModels.add(j, item);
                }
            }
        }
        for (int j = 0; j < contactModels.size(); j++) {
            String date11[] = contactModels.get(j).getDate().split(":");
            for (int i = j; i < contactModels.size(); i++) {
                String date2[] = contactModels.get(i).getDate().split(":");
                if (Integer.parseInt(date11[0]) > Integer.parseInt(date2[0]) && Integer.parseInt(date11[2]) == Integer.parseInt(date2[2])
                        && Integer.parseInt(date11[1]) == Integer.parseInt(date2[1])) {
                    ContactModel item = contactModels.get(i);
                    contactModels.remove(i);
                    contactModels.add(i, contactModels.get(j));
                    int t = contactModels.size();
                    contactModels.remove(j);
                    contactModels.add(j, item);
                }
            }
        }

    }
}
