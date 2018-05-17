package com.example.snickers.auto;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Create_Reminder extends AppCompatActivity {
    EditText etdistanceremind,etdescriptionremind,etdateremind;
    FloatingActionButton addreminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__reminder);

        etdateremind = findViewById(R.id.etdateremind);
        etdistanceremind = findViewById(R.id.etdistanceremind);
        etdescriptionremind = findViewById(R.id.etdescriptionremind);
        addreminder = findViewById(R.id.floatingActionButton3f);
        etdistanceremind.setRawInputType(InputType.TYPE_CLASS_NUMBER);


        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String date = sdf.format(new Date(System.currentTimeMillis()));
        etdateremind.setText(date);

        etdateremind.setOnClickListener(t -> {
            DialogFragment dialogfragment = new DatePickerDialogClass1();
            dialogfragment.show(getFragmentManager(), "Date Picker Dialog");



        });

    }


    public static class DatePickerDialogClass1 extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_DEVICE_DEFAULT_DARK, this, year, month, day);

            return datepickerdialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            EditText date = getActivity().findViewById(R.id.etdateremind);

            date.setText(day + ":" + (month + 1) + ":" + year);

        }
    }
}
