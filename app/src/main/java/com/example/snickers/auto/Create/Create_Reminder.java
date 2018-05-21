package com.example.snickers.auto.Create;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.usage.UsageEvents;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.snickers.auto.Adapter.ReminderAdapter;
import com.example.snickers.auto.DB.contacts.ContactModel;
import com.example.snickers.auto.DB.events.EventDao;
import com.example.snickers.auto.DB.events.EventModel;
import com.example.snickers.auto.MainActivity;
import com.example.snickers.auto.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Create_Reminder extends AppCompatActivity {
    EditText etdistanceremind, etdescriptionremind, etdateremind;
    FloatingActionButton addreminder;
    EventModel item = new EventModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__reminder);

        etdateremind = findViewById(R.id.etdateremind);
        etdistanceremind = findViewById(R.id.etdistanceremind);
        etdescriptionremind = findViewById(R.id.etdescriptionremind);
        addreminder = findViewById(R.id.addreminder);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.M.yyyy");
        String date = sdf.format(new Date(System.currentTimeMillis()));
        etdateremind.setText(date);
        etdistanceremind.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        item = (EventModel) getIntent().getSerializableExtra("item");
        if (item != (null)) {
            etdateremind.setText(item.getDate());
            etdistanceremind.setText(item.getDistance() + "");
            etdescriptionremind.setText(item.getDescription() + "");

        }
        addreminder.setOnClickListener(t -> {
            try {
                if (item != (null)) {

                    EventDao eventDao = new EventDao(t);
                    if (eventDao.update(item.get_id(), etdateremind.getText().toString(),
                            Integer.parseInt(etdistanceremind.getText().toString()),
                            etdescriptionremind.getText().toString())) {
                        Intent inthomefra = new Intent(this, MainActivity.class);
                        inthomefra.putExtra("Create_Note", 2);
                        startActivity(inthomefra);

                    } else {
                        Toast.makeText(this, "Error data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    EventDao eventDao = new EventDao(t);
                    if (eventDao.add(etdateremind.getText().toString(), Integer.parseInt(etdistanceremind.getText().toString()),
                            etdescriptionremind.getText().toString())) {
                        Intent inthomefra = new Intent(this, MainActivity.class);
                        inthomefra.putExtra("Create_Note", 2);
                        startActivity(inthomefra);

                    } else {
                        Toast.makeText(this, "Error data", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error data", Toast.LENGTH_SHORT).show();
            }

        });


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
