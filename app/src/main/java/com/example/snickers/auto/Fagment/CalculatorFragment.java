package com.example.snickers.auto.Fagment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.snickers.auto.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorFragment extends android.support.v4.app.Fragment  {
    EditText etcost, etdistance, etprice;
    TextView tvcost, tvvolume;
    Button button;
    double sum;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        //return inflater.inflate(R.layout.calc_activity, null);

        // EditText etcost = (EditText) View.findViewById(R.id.etcost);
        View view = inflater.inflate(R.layout.calc_activity, container, false);
        etcost = (EditText) view.findViewById(R.id.etcost);
        etprice = (EditText) view.findViewById(R.id.etprice);
        etdistance = (EditText) view.findViewById(R.id.etdistance);
        tvcost = (TextView) view.findViewById(R.id.tvcost);
        tvvolume = (TextView) view.findViewById(R.id.tvvolume);
        etcost.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        etprice.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        etdistance.setRawInputType(InputType.TYPE_CLASS_NUMBER);

        etdistance.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(2)});
        etprice.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(2)});
        etcost.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(2)});
      //  button = (Button) view.findViewById(R.id.button);
        /* Set Text Watcher listener */
        etdistance.addTextChangedListener( textWatcher);
        etcost.addTextChangedListener(textWatcher);
        etprice.addTextChangedListener( textWatcher2);

        return view;


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




    private final  TextWatcher textWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //  mTextHint.setVisibility(View.VISIBLE);
        }

        //Задаем действия для TextView после смены введенных в EditText символов:
        public void afterTextChanged(Editable s) {
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            try {
              double  k = Double.parseDouble(etcost.getText().toString());
              double  k1 = Double.parseDouble(etdistance.getText().toString());
                 sum = (k1 * k) /100;
               // tvvolume.setText(Double.toString(sum));
                tvvolume.setText(decimalFormat.format(new BigDecimal(sum +"")));
               tvvolume.setVisibility(View.VISIBLE);
                getFinalSum();
            }catch (Exception ex){}
        }
    };


    private final  TextWatcher  textWatcher2  = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        //Задаем действия для TextView после смены введенных в EditText символов:
        public void afterTextChanged(Editable s) {
            getFinalSum();

        }
    };

    private void getFinalSum() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        try {
            if (tvvolume.getText() != null) {
                //  double z = Double.parseDouble(tvvolume.getText().toString());
                //double z = 5;
                double z1 = Double.parseDouble(etprice.getText().toString());
                double finalSum = sum * z1;
                tvcost.setText(df.format(new BigDecimal(finalSum +"")));
                tvcost.setVisibility(View.VISIBLE);


            }
        }catch (Exception ex){}
    }

}
