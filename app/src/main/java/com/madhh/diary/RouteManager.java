package com.madhh.diary;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseGeoPoint;

/**
 * Created by debasish on 6/11/2015.
 */
public class RouteManager {
    private static RouteManager routeManager = null;
    private Context mContext;
    public double start_lat;
    public double start_long;
    public double end_lat;
    public double end_long;

    private RouteManager(Context mContext){
        this.mContext = mContext;
    };

    public static RouteManager getRouteManager(Context mContext){
        if(routeManager == null){
            routeManager = new RouteManager(mContext);
        }
        return routeManager;
    }
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 2; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 10000; // in Milliseconds
    protected LocationManager locationManager;
    protected MyLocationListener myListener;
    public RouteInfo routeInfo = null;


    public void startTrack()
    {

        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        myListener = new MyLocationListener();

        routeInfo = new RouteInfo();


        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        start_lat=location.getLatitude();
        start_long=location.getLongitude();

        routeInfo.setParseGeoPoint(new ParseGeoPoint(start_lat, start_long));

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                myListener
        );
    }

    public void stopTrack()
    {
        String message = String.format(
                "End Tracking");
        //Toast.makeText(MapActivity.this, message, Toast.LENGTH_LONG).show();
        locationManager.removeUpdates(myListener);
        myListener = null;
        locationManager = null;
        routeInfo.saveRouteInfo();
        try {
            routeInfo.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        routeInfo = null;
    }

    private class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
            String message = String.format(
                    "New Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            routeInfo.setParseGeoPoint(new ParseGeoPoint(location.getLatitude(), location.getLongitude()));
            //connectLocations(routeInfo.getParseGeoPoint());
            end_lat = location.getLatitude();
            end_long = location.getLongitude();
            //Toast.makeText(MapActivity.this, message, Toast.LENGTH_LONG).show();

        }

        public void onStatusChanged(String s, int i, Bundle b) {
//            Toast.makeText(MapActivity.this, "Provider status changed",
//                    Toast.LENGTH_LONG).show();

        }

        public void onProviderDisabled(String s) {
//            Toast.makeText(MapActivity.this,
//                    "Provider disabled by the user. GPS turned off",
//                    Toast.LENGTH_LONG).show();
        }

        public void onProviderEnabled(String s) {
//            Toast.makeText(MapActivity.this,
//                    "Provider enabled by the user. GPS turned on",
//                    Toast.LENGTH_LONG).show();
        }

    }
}
