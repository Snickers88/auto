package com.example.snickers.auto;

import android.app.AlertDialog;
import android.app.DatePickerDialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.widget.Toast;


import com.example.snickers.auto.DB.ContactDao;
import com.example.snickers.auto.DB.ContactModel;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Create_note extends AppCompatActivity {
    EditText etdistancecreate1, etvolumecreate1, ettogethercreate1, etpricecreate1, etdatecreate1;
    FloatingActionButton floatingActionButton;
    Spinner spinner;
    ContactModel item;
    double sum;
    double sum2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        etdatecreate1 = findViewById(R.id.etdatecreate);
        etdistancecreate1 = findViewById(R.id.etdistancecreate);
        etvolumecreate1 = findViewById(R.id.etvolumecreate);
        ettogethercreate1 = findViewById(R.id.ettogether);
        etpricecreate1 = findViewById(R.id.etpricecreate);
        spinner = findViewById(R.id.spinner);
                floatingActionButton = findViewById(R.id.floatingActionButton3f);
        etpricecreate1.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        etdistancecreate1.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        etvolumecreate1.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        ettogethercreate1.setRawInputType(InputType.TYPE_CLASS_NUMBER);


        etpricecreate1.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(2)});
        etvolumecreate1.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(2)});



        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String date = sdf.format(new Date(System.currentTimeMillis()));
        etdatecreate1.setText(date);



//        ettogethercreate.addTextChangedListener( textWatcher);
       etvolumecreate1.addTextChangedListener( textWatcher);
        etpricecreate1.addTextChangedListener( textWatcher);
        etpricecreate1.addTextChangedListener( textWatcher2);
        ettogethercreate1.addTextChangedListener( textWatcher2);

        item = (ContactModel) getIntent().getSerializableExtra("item");
        if (item !=(null)){
            etdatecreate1.setText(item.getDate());
            etdistancecreate1.setText(item.getDistance()+"");
            etvolumecreate1.setText(item.getVolume()+"");
            ettogethercreate1.setText(item.getTogether()+"");
            etpricecreate1.setText(item.getPrice()+"");
        }
        etdatecreate1.setOnClickListener(t -> {
            DialogFragment dialogfragment = new DatePickerDialogClass();
            dialogfragment.show(getFragmentManager(), "Date Picker Dialog");



        });
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.fuel,android.R.layout.simple_spinner_item);
         adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         spinner.setAdapter(adapter);


        floatingActionButton.setOnClickListener(t -> {
            ContactDao contactDao = new ContactDao(t);
        if (item == (null)){


          if(contactDao.add(etdatecreate1.getText().toString(),
                    Integer.parseInt(etdistancecreate1.getText().toString()),
                    Double.parseDouble(etvolumecreate1.getText().toString()),
                    Double.parseDouble(etpricecreate1.getText().toString()),
                 //   Double.parseDouble(s2),
                   // Double.parseDouble(ettogethercreate1.getText().toString()),

                  Double.parseDouble (etvolumecreate1.getText().toString()) * Double.parseDouble(etpricecreate1.getText().toString()),
       spinner.getSelectedItem().toString()
            )){
              Intent inthomefra = new Intent(this, MainActivity.class);
              inthomefra.putExtra("Create_Note",1);
              startActivity(inthomefra);
          }else{
              Toast.makeText(this, "Error data", Toast.LENGTH_SHORT).show();

          }}else{
            if(contactDao.update(item.get_id(),
                    etdatecreate1.getText().toString(),
                    Integer.parseInt(etdistancecreate1.getText().toString()),
                    Double.parseDouble(etvolumecreate1.getText().toString()),
                    Double.parseDouble(etpricecreate1.getText().toString()),
                    //ettogethercreate1.getText().toString(),

                     Double.parseDouble (etvolumecreate1.getText().toString()) * Double.parseDouble(etpricecreate1.getText().toString()),
                    spinner.getSelectedItem().toString()
            ))
            {
                Intent inthomefra = new Intent(this, MainActivity.class);
                inthomefra.putExtra("Create_Note",1);
                startActivity(inthomefra);
            }else{
                Toast.makeText(this, "Error data", Toast.LENGTH_SHORT).show();
            }
        }
        });

    }

    public class DecimalDigitsInputFilter implements InputFilter {
        Pattern mPattern;
        public DecimalDigitsInputFilter(int digitsAfterZero) {
            mPattern=Pattern.compile("[0-9]+((\\.[0-9]{0," + (digitsAfterZero-1) + "})?)||(\\.)?");
        }
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            Matcher matcher=mPattern.matcher(dest);
            if(!matcher.matches())
                return "";
            return null;
        }
    }



    private final TextWatcher textWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //  mTextHint.setVisibility(View.VISIBLE);
        }

        //Задаем действия для TextView после смены введенных в EditText символов:
        public void afterTextChanged(Editable s) {
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

            try {
               double q = Double.parseDouble(etvolumecreate1.getText().toString());
                double q1 = Double.parseDouble(etpricecreate1.getText().toString());
                 sum = q * q1;
                ettogethercreate1.setText(decimalFormat.format(new BigDecimal(sum  +"")));
               // tvvolume.setVisibility(View.VISIBLE);
            }catch (Exception ex){}
        }
    };


    private final TextWatcher textWatcher2 = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //  mTextHint.setVisibility(View.VISIBLE);
        }

        //Задаем действия для TextView после смены введенных в EditText символов:
        public void afterTextChanged(Editable s) {
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

            try {
                double w = Double.parseDouble(ettogethercreate1.getText().toString());
                double w1 = Double.parseDouble(etpricecreate1.getText().toString());
                 sum2 = w * w1;
                etvolumecreate1.setText(decimalFormat.format(new BigDecimal(sum2  +"")));
                // tvvolume.setVisibility(View.VISIBLE);
            }catch (Exception ex){}
        }
    };





    public static class DatePickerDialogClass extends DialogFragment implements DatePickerDialog.OnDateSetListener {

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

            EditText date = getActivity().findViewById(R.id.etdatecreate);

            date.setText(day + ":" + (month + 1) + ":" + year);

        }
    }
}