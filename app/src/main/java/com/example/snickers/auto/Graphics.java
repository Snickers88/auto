package com.example.snickers.auto;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.snickers.auto.DB.ContactDao;
import com.example.snickers.auto.DB.ContactModel;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Graphics extends Fragment {
    DataPoint[] datePoint;

    ArrayList<ContactModel> contactModel = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView rv;
        View v = inflater.inflate(R.layout.activity_graphics, container, false);
        ContactDao contactDao = new ContactDao(v);
        contactModel.addAll(contactDao.select());
        Collections.sort(contactModel, (o1, o2) -> {
            if (o1.getDate() == null || o2.getDate() == null)
                return 0;
            return o1.getDate().compareTo(o2.getDate());
        });
        datePoint = new DataPoint[contactModel.size()];

        int i = 0;


        String dateFirst[] = contactModel.get(0).getDate().split(":");

        for (ContactModel item : contactModel) {

            SimpleDateFormat format = new SimpleDateFormat("dd:MM:yyyy");
            try {
                Date date = format.parse(item.getDate());
                datePoint[i] = (new DataPoint(date, item.getTogether()));

            } catch (ParseException e) {
                e.printStackTrace();
            }

            i++;
        }

        GraphView graph = v.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(datePoint);
        graph.addSeries(series);

        graph.getViewport().setXAxisBoundsManual(true);
        SimpleDateFormat format = new SimpleDateFormat("dd:MM:yyyy");
        try {
            Date date = format.parse(contactModel.get(0).getDate());

            graph.getViewport().setMinX(date.getTime() - 10000000);
            date = format.parse(contactModel.get(1).getDate());

            graph.getViewport().setMaxX(date.getTime() + 10000000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(graph.getContext()));

        graph.getGridLabelRenderer().setNumHorizontalLabels(3);

        graph.getViewport().setScrollable(true);


        graph.getLegendRenderer().setVisible(true);

        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
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
