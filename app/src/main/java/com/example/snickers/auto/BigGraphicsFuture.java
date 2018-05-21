package com.example.snickers.auto;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.snickers.auto.DB.ContactModel;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class BigGraphicsFuture extends AppCompatActivity {
    private ArrayList<ContactModel> contactModels = new ArrayList<>();
    private LineChartView graphicsFuture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_graphics_future);
        graphicsFuture = findViewById(R.id.chartBigFuture);
        contactModels.addAll((ArrayList<ContactModel>) getIntent().getSerializableExtra("graphics"));
        graphicsFuture();
    }

    void graphicsFuture() {

        // SimpleDateFormat sdf = new SimpleDateFormat("MM");
        //  String date = sdf.format(new Date(System.currentTimeMillis()));


        List<PointValue> values = new ArrayList<>();
        PointValue tempPointValue;
        for (ContactModel item : contactModels) {
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

}
