package app.fuelfinder;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class MotRoadActivity extends Activity implements View.OnClickListener{

    DatePicker motDate ;
    DatePicker roadDate ;
    TextView motDateText ;
    TextView roadDateText ;
    TextView motStatus ;
    TextView roadStatus ;
    Button btn_edit ;
    Button save ;
    SharedPreferences sp;
    SharedPreferences.Editor edit ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mot_road);

        // save data into share SharePreference
        sp = getSharedPreferences("shared_prefrences_motRoad", MODE_PRIVATE);


        //Initializing fields
        motDate=(DatePicker)findViewById(R.id.motDatePicker) ;
        roadDate=(DatePicker)findViewById(R.id.roadTax_DatePicker) ;

        motDateText=(TextView)findViewById(R.id.motDate_text) ;
        roadDateText=(TextView)findViewById(R.id.roadTax_text) ;

        motStatus=(TextView)findViewById(R.id.motStatus_text) ;
        roadStatus=(TextView)findViewById(R.id.roadStatus_text) ;

        btn_edit=(Button)findViewById(R.id.Edit) ;
        save=(Button)findViewById(R.id.save) ;

        btn_edit.setOnClickListener(this);
        save.setOnClickListener(this);

        motDate.setVisibility(View.GONE);
        roadDate.setVisibility(View.GONE);

        //Setting values
        setValues();


    }

    @Override
    public void onClick(View view) {
        if(view.getId()==btn_edit.getId()){

            //Enabling and disabling views
            motDateText.setVisibility(View.GONE);
            roadDateText.setVisibility(View.GONE);

            motDate.setVisibility(View.VISIBLE);
            roadDate.setVisibility(View.VISIBLE);


        }
        else if(view.getId()==save.getId()){

            //Enabling and disabling views
            motDateText.setVisibility(View.VISIBLE);
            roadDateText.setVisibility(View.VISIBLE);

            motDate.setVisibility(View.GONE);
            roadDate.setVisibility(View.GONE);

            //putting values in shared prefrences
            edit = sp.edit();
            edit.putString("motExpiryDate",getMotDate());
            edit.putString("RoadExpiryDate",getRoadDate());
            edit.apply();

            setValues();


        }

    }

    //Formatting date
    //Mot
    private String getMotDate(){
        //Formating date
        int day = motDate.getDayOfMonth();
        int month = motDate.getMonth();
        int year = motDate.getYear();

        edit.putInt("day",motDate.getDayOfMonth()) ;
        edit.putInt("month",motDate.getMonth()) ;
        edit.putInt("year",motDate.getYear()) ;


        return month+"/"+day+"/"+year ;

        //Road
    }
    private String getRoadDate(){
        //Formating Road expiry date
        int day = roadDate.getDayOfMonth();
        int month = roadDate.getMonth();
        int year = roadDate.getYear();

        edit.putInt("day1",roadDate.getDayOfMonth()) ;
        edit.putInt("month1",roadDate.getMonth()) ;
        edit.putInt("year1",roadDate.getYear()) ;


        return month+"/"+day+"/"+year ;

    }



    private void setValues(){

        if(!(sp.getString("motExpiryDate","MM/DD/YYYY").equals(null))){
            motDateText.setText(sp.getString("motExpiryDate","MM/DD/YYYY"));
            roadDateText.setText(sp.getString("RoadExpiryDate","MM/DD/YYYY"));

            //Setting Mot and Road Status
          settingMotStatusText(motStatus);
          settingRoadStatusText(roadStatus);
        }

    }

    //Method to setMotStatusText
    private void settingMotStatusText(TextView status){
        try {


            //getting current date
            Calendar todayDate = Calendar.getInstance();
            int year=todayDate.get(Calendar.YEAR);
            int month=todayDate.get(Calendar.MONTH);
            int mDay = todayDate.get(Calendar.DAY_OF_MONTH);





            //Comparing dates to set Status as Valid or expired

            if(year>sp.getInt("year",1)){
                status.setText("Expired");
                status.setTextColor(getResources().getColor(R.color.red));
            }else if (year==sp.getInt("year",1)){
                if(month>sp.getInt("month",1)){
                    status.setText("Expired");
                    status.setTextColor(getResources().getColor(R.color.red));
                }else if(month==sp.getInt("month",1)){
                    if (mDay>sp.getInt("day",1)){
                        status.setText("Expired");
                        status.setTextColor(getResources().getColor(R.color.red));
                    }else {
                        status.setText("Active");
                        status.setTextColor(getResources().getColor(R.color.green));
                    }
                }else {
                    status.setText("Active");
                    status.setTextColor(getResources().getColor(R.color.green));
                }

            }else {
                status.setText("Active");
                status.setTextColor(getResources().getColor(R.color.green));
            }

            Log.d("d1",todayDate.toString()) ;



        }catch (Exception e){
            Log.d("Date exception", e.toString());
        }


    }

    //Method to set RoadStatusText
    private void settingRoadStatusText(TextView status){
        try {


            //getting current date
            Calendar todayDate = Calendar.getInstance();
            int year=todayDate.get(Calendar.YEAR);
            int month=todayDate.get(Calendar.MONTH);
            int mDay = todayDate.get(Calendar.DAY_OF_MONTH);





            //Comparing dates to set Status as Valid or expired

            if(year>sp.getInt("year1",1)){
                status.setText("Expired");
                status.setTextColor(getResources().getColor(R.color.red));
            }else if (year==sp.getInt("year1",1)){
                if(month>sp.getInt("month1",1)){
                    status.setText("Expired");
                    status.setTextColor(getResources().getColor(R.color.red));
                }else if(month==sp.getInt("month1",1)){
                    if (mDay>sp.getInt("day1",1)){
                        status.setText("Expired");
                        status.setTextColor(getResources().getColor(R.color.red));
                    }else {
                        status.setText("Active");
                        status.setTextColor(getResources().getColor(R.color.green));
                    }
                }else {
                    status.setText("Active");
                    status.setTextColor(getResources().getColor(R.color.green));
                }

            }else {
                status.setText("Active");
                status.setTextColor(getResources().getColor(R.color.green));
            }

            Log.d("d1",todayDate.toString()) ;



        }catch (Exception e){
            Log.d("Date exception", e.toString());
        }


    }

}
