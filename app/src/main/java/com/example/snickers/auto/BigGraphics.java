package com.example.snickers.auto;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.snickers.auto.DB.ContactModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;
import ru.rambler.libs.swipe_layout.SwipeLayout;

import android.support.v7.widget.helper.ItemTouchHelper.Callback;

import static android.widget.GridLayout.LEFT;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class BigGraphics extends AppCompatActivity {
    private ArrayList<ContactModel> contactModels = new ArrayList<>();
    private LineChartView lineChartView;
    private TextView month;
    private int monthEnd = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_graphics);
        lineChartView = findViewById(R.id.chartBig);
        month = findViewById(R.id.month);
        contactModels.addAll((ArrayList<ContactModel>) getIntent().getSerializableExtra("graphics"));
        graphics(0);
        lineChartView.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeTop() {
                Toast.makeText(BigGraphics.this, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
                //  Toast.makeText(BigGraphics.this, "right", Toast.LENGTH_SHORT).show();
                monthEnd--;
                graphics(monthEnd);

            }

            public void onSwipeLeft() {
                // Toast.makeText(BigGraphics.this, "left", Toast.LENGTH_SHORT).show();
                monthEnd++;
                graphics(monthEnd);

            }

            public void onSwipeBottom() {
                Toast.makeText(BigGraphics.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });
    }

    void graphics(int monthEnd) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        String date = sdf.format(new Date(System.currentTimeMillis()));
        month.setText("Місяць " + (Integer.parseInt(date) + monthEnd));
        List<PointValue> values = new ArrayList<>();
        PointValue tempPointValue;
        for (ContactModel item : contactModels) {
            String dateFirst[] = item.getDate().split(":");
            int f = (Integer.parseInt(date));
            int f1 = Integer.parseInt(dateFirst[1]);
            if ((Integer.parseInt(date) + monthEnd) == Integer.parseInt(dateFirst[1])) {
                tempPointValue = new PointValue(Float.parseFloat(dateFirst[0]), (float) item.getTogether());
                values.add(tempPointValue);
            }

        }
        if (values.size() == 0)
            Toast.makeText(BigGraphics.this, "Даних нема", Toast.LENGTH_SHORT).show();

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


        lineChartView.setLineChartData(data);
    }


    public class OnSwipeTouchListener implements OnTouchListener {

        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener(Context ctx) {
            gestureDetector = new GestureDetector(ctx, new GestureListener());
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        private final class GestureListener extends SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                            result = true;
                        }
                    } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                        result = true;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }

        public void onSwipeRight() {
        }

        public void onSwipeLeft() {
        }

        public void onSwipeTop() {
        }

        public void onSwipeBottom() {
        }
    }
}
