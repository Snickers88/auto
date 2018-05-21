package com.example.snickers.auto;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.snickers.auto.DB.ContactDao;
import com.example.snickers.auto.DB.ContactModel;
import com.jjoe64.graphview.series.DataPoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class Graphics extends Fragment {
    private DataPoint[] datePoint;

    private ArrayList<ContactModel> contactModel = new ArrayList<>();
    private View v;
    private LineChartView graphicsNow, graphicsFuture;
    private ArrayList<ContactModel> contactModelsFuture = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView rv;
        View v = inflater.inflate(R.layout.activity_graphics, container, false);
        ContactDao contactDao = new ContactDao(v);
        contactModel.addAll(contactDao.select());
        graphicsNow = v.findViewById(R.id.graphicsNow);
        graphicsFuture = v.findViewById(R.id.graphicsFuture);
        Collections.sort(contactModel, (o1, o2) -> {
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
        graphicsNow.setOnClickListener(t -> {
            Intent in = new Intent(v.getContext(), BigGraphicsNow.class);
            in.putExtra("graphics", contactModel);
            startActivity(in);
        });

        graphicsFuture.setOnClickListener(t -> {
            Intent in = new Intent(v.getContext(), BigGraphicsFuture.class);
            in.putExtra("graphics", contactModelsFuture);
            startActivity(in);
        });
        graphicsNow();
        return v;
    }

    void graphicsNow() {

        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        String date = sdf.format(new Date(System.currentTimeMillis()));

        List<PointValue> values = new ArrayList<>();
        PointValue tempPointValue;
        for (ContactModel item : contactModel) {
            String dateFirst[] = item.getDate().split(":");
            if (Integer.parseInt(date) == Integer.parseInt(dateFirst[1])) {
                tempPointValue = new PointValue(Float.parseFloat(dateFirst[0]), (float) item.getTogether());
                values.add(tempPointValue);
                contactModelsFuture.add(item);
            }

        }

        Line line = new Line(values)
                .setColor(Color.BLUE)
                .setCubic(false)
                .setHasPoints(true).setHasLabels(true);
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        List<AxisValue> axisValuesForX = new ArrayList<>();
        List<AxisValue> axisValuesForY = new ArrayList<>();
        AxisValue tempAxisValue;
        for (float i = 0; i <= 31; i += 1) {
            tempAxisValue = new AxisValue(i);
            tempAxisValue.setLabel(i + "");
            axisValuesForX.add(tempAxisValue);
        }

        for (float i = 0; i <= 1000; i += 1) {
            tempAxisValue = new AxisValue(i);
            tempAxisValue.setLabel("" + i);
            axisValuesForY.add(tempAxisValue);
        }
        Axis xAxis = new Axis(axisValuesForX);
        Axis yAxis = new Axis(axisValuesForY);
        data.setAxisXBottom(xAxis);
        data.setAxisYLeft(yAxis);

        graphicsFuture();
        graphicsNow.setLineChartData(data);
    }


    void graphicsFuture() {
        foresight();
        // SimpleDateFormat sdf = new SimpleDateFormat("MM");
        //  String date = sdf.format(new Date(System.currentTimeMillis()));


        List<PointValue> values = new ArrayList<>();
        PointValue tempPointValue;
        for (ContactModel item : contactModelsFuture) {
            String dateFirst[] = item.getDate().split(":");
            //  if (Integer.parseInt(date)  == Integer.parseInt(dateFirst[1])) {
            tempPointValue = new PointValue(Float.parseFloat(dateFirst[0]), (float) item.getTogether());
            values.add(tempPointValue);
            // }

        }

        Line line = new Line(values)
                .setColor(Color.BLUE)
                .setCubic(false)
                .setHasPoints(true).setHasLabels(true);
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        List<AxisValue> axisValuesForX = new ArrayList<>();
        List<AxisValue> axisValuesForY = new ArrayList<>();
        AxisValue tempAxisValue;
        for (float i = 0; i <= 31; i += 1) {
            tempAxisValue = new AxisValue(i);
            tempAxisValue.setLabel(i + "");
            axisValuesForX.add(tempAxisValue);
        }

        for (float i = 0; i <= 1000; i += 1) {
            tempAxisValue = new AxisValue(i);
            tempAxisValue.setLabel("" + i);
            axisValuesForY.add(tempAxisValue);
        }
        Axis xAxis = new Axis(axisValuesForX);
        Axis yAxis = new Axis(axisValuesForY);
        data.setAxisXBottom(xAxis);
        data.setAxisYLeft(yAxis);


        graphicsFuture.setLineChartData(data);
    }

    void foresight() {

        //contactModelsFuture
        Random r = new Random();
        double price, volume;
        price = volume = 0.0;
        int i = 0;
        for (ContactModel item : contactModelsFuture) {
            price = item.getPrice();
            volume = item.getVolume();
            price *= 100;
            volume *= 1000;
            int priceCout = r.nextInt((int) (price / 10));
           // int t = (int) (volume / 12);
              int volumeCout  = r.nextInt((int)(volume / 7.5));

            if (r.nextBoolean()) {

                volume += volumeCout;
            } else {
                volume -= volumeCout;
            }
            if (r.nextBoolean()) {

                price += priceCout;
            } else {
                price -= priceCout;
            }
            ContactModel element = new ContactModel();

            element.setPrice(price/100);
            element.setVolume(volume/1000);
            element.setTogether(price * volume/100000);

            element.setDate(item.getDate());
            contactModelsFuture.set(i, element);

            i++;
        }

int t;
    }
   /* public static class MyObject implements Comparable<ContactModel> {

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
    }*/
}
