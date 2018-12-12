package app.fuelfinder;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MenuActivity extends Activity implements View.OnClickListener {

    //declaring variables and objects
    String email ;
    private TextView emailTextView ;
    private Button btnFuelFinder ;
    private Button btnInsuranceDetails ;
    private Button btnMotAndRoadTax ;
    BroadcastReceiver broadcastReceiver ;
    public static double lat ;
    public static  double lng ;
    private boolean isLocationFetched=false ;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Starting service
        startService(new Intent(this,LocationService.class)) ;


        //Initializing activities
        progressBar=(ProgressBar)findViewById(R.id.progressBar) ;

        email=getIntent().getExtras().getString("email") ;

        emailTextView=(TextView) findViewById(R.id.text_email) ;
        emailTextView.setText(email);

        btnFuelFinder=(Button) findViewById(R.id.btn_FuelFinder) ;
        btnInsuranceDetails=(Button) findViewById(R.id.btn_InsuranceDetails) ;
        btnMotAndRoadTax=(Button) findViewById(R.id.btn_MotTax) ;

        btnMotAndRoadTax.setOnClickListener(this);
        btnInsuranceDetails.setOnClickListener(this);
        btnFuelFinder.setOnClickListener(this);

        //Setting visibilty to gone so we can display them after location is fethched
        btnFuelFinder.setVisibility(View.GONE);
        btnInsuranceDetails.setVisibility(View.GONE);
        btnMotAndRoadTax.setVisibility(View.GONE);

        //Receiving broadcast from Location service
        if(broadcastReceiver==null){


            broadcastReceiver=new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    lat = Double.parseDouble(intent.getExtras().get("lat").toString());
                    lng = Double.parseDouble(intent.getExtras().get("lng").toString());

                    //fetching location at the start of application
                    if (!isLocationFetched) {
                        if(lat!=0.0) {


                            //Setting visibilty to visible so we can display them after location is fethched
                            btnFuelFinder.setVisibility(View.VISIBLE);
                            btnInsuranceDetails.setVisibility(View.VISIBLE);
                            btnMotAndRoadTax.setVisibility(View.VISIBLE);

                            Log.d("latlng",""+lat+" "+lng) ;
                            //setting visibility to gone as location is fetched now
                            progressBar.setVisibility(View.GONE);

                            isLocationFetched=true ;
                        }
                    }
                }
            };

        } registerReceiver(broadcastReceiver,new IntentFilter("location_update")) ;







    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==btnMotAndRoadTax.getId()){
            //Starting Mot and Road activity
            startActivity(new Intent(this,MotRoadActivity.class));


        }else if(view.getId()==btnInsuranceDetails.getId()){
            //Starting Insurance Activity
            startActivity(new Intent(this,InsuranceActvitiy.class));

        }else if (view.getId()==btnFuelFinder.getId()){
            //Starting maps Activity
            startActivity(new Intent(this,MapsActivity.class));

        }
    }
}
