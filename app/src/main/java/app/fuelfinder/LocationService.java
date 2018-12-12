package app.fuelfinder;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Mac on 04/03/2018.
 */

public class LocationService extends Service {
    LocationManager locationManager ;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public static double latitude ;
    public static double longitude ;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {


        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        locationManager.requestLocationUpdates(

                LocationManager.NETWORK_PROVIDER, 3000, 0,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {

                        longitude = location.getLongitude();
                        latitude = location.getLatitude();

                        Intent intent=new Intent("location_update") ;
                        intent.putExtra("lat",latitude) ;
                        intent.putExtra("lng",longitude) ;
                        sendBroadcast(intent);

                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                3000, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {

                        longitude = location.getLongitude();
                        latitude = location.getLatitude();

                        Intent intent=new Intent("location_update") ;
                        intent.putExtra("lat",latitude) ;
                        intent.putExtra("lng",longitude) ;
                        sendBroadcast(intent);
                    }



                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });


    }

}



