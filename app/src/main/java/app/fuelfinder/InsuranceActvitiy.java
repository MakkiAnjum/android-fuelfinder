package app.fuelfinder;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class InsuranceActvitiy extends Activity implements View.OnClickListener {

    TextView insuranceText;
    EditText insurance;

    TextView vehicalText;
    EditText vehical;

    TextView status;

    DatePicker expiryDatePicker;
    TextView expiryDate;
    Button btn_edit;
    Button save;
    SharedPreferences sp;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_actvitiy);

        // save data into share SharePreference
        sp = getSharedPreferences("shared_prefrences_insurance", MODE_PRIVATE);

        insuranceText = (TextView) findViewById(R.id.insurance_text);
        insurance = (EditText) findViewById(R.id.insurance_EditText);

        vehicalText = (TextView) findViewById(R.id.vehical_text);
        vehical = (EditText) findViewById(R.id.vehical_EditText);

        status = (TextView) findViewById(R.id.status_text);

        expiryDatePicker = (DatePicker) findViewById(R.id.expiry_EditText);
        expiryDate = (TextView) findViewById(R.id.expiry_text);

        btn_edit = (Button) findViewById(R.id.Edit);
        save = (Button) findViewById(R.id.save);
        btn_edit.setOnClickListener(this);
        save.setOnClickListener(this);

        insurance.setVisibility(View.GONE);
        vehical.setVisibility(View.GONE);
        expiryDatePicker.setVisibility(View.GONE);


        setValues();


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btn_edit.getId()) {
            //Make them visible
            insurance.setVisibility(View.VISIBLE);
            vehical.setVisibility(View.VISIBLE);
            expiryDatePicker.setVisibility(View.VISIBLE);

            //Make invisble textview
            insuranceText.setVisibility(View.GONE);
            vehicalText.setVisibility(View.GONE);
            expiryDate.setVisibility(View.GONE);


        } else if (view.getId() == save.getId()) {
            //changing visibility
            insurance.setVisibility(View.GONE);
            vehical.setVisibility(View.GONE);
            expiryDatePicker.setVisibility(View.GONE);

            //Make visble textview
            insuranceText.setVisibility(View.VISIBLE);
            vehicalText.setVisibility(View.VISIBLE);
            expiryDate.setVisibility(View.VISIBLE);

            edit = sp.edit();
            edit.putString("vehical", vehical.getText().toString());
            edit.putString("insuranceProvider", insurance.getText().toString());
            edit.putString("date", getDate());
            edit.apply();


            setValues();


        }

    }

    //Formatting date
    private String getDate() {
        int day = expiryDatePicker.getDayOfMonth();
        int month = expiryDatePicker.getMonth();
        int year = expiryDatePicker.getYear();

        edit.putInt("day", expiryDatePicker.getDayOfMonth());
        edit.putInt("month", expiryDatePicker.getMonth());
        edit.putInt("year", expiryDatePicker.getYear());


        return month + "/" + day + "/" + year;

    }

    //assigning values
    private void setValues() {
        if (!(sp.getString("vehical", "Vehical").equals(null))) {
            vehicalText.setText(sp.getString("vehical", "Vehical"));
            insuranceText.setText(sp.getString("insuranceProvider", "Insurance Provider"));
            expiryDate.setText(sp.getString("date", "DD/MM/Year"));

            try {


                //getting current date
                Calendar todayDate = Calendar.getInstance();
                int year = todayDate.get(Calendar.YEAR);
                int month = todayDate.get(Calendar.MONTH);
                int mDay = todayDate.get(Calendar.DAY_OF_MONTH);


                //Comparing dates to set Status as Valid or expired

                if (year > sp.getInt("year", 1)) {
                    status.setText("Expired");
                    status.setTextColor(getResources().getColor(R.color.red));
                } else if (year == sp.getInt("year", 1)) {
                    if (month > sp.getInt("month", 1)) {
                        status.setText("Expired");
                        status.setTextColor(getResources().getColor(R.color.red));
                    } else if (month == sp.getInt("month", 1)) {
                        if (mDay > sp.getInt("day", 1)) {
                            status.setText("Expired");
                            status.setTextColor(getResources().getColor(R.color.red));
                        } else {
                            status.setText("Active");
                            status.setTextColor(getResources().getColor(R.color.green));
                        }
                    } else {
                        status.setText("Active");
                        status.setTextColor(getResources().getColor(R.color.green));
                    }

                } else {
                    status.setText("Active");
                    status.setTextColor(getResources().getColor(R.color.green));
                }

                Log.d("d1", todayDate.toString());


            } catch (Exception e) {
                Log.d("Date exception", e.toString());
            }


        }

    }


}
