package com.madhh.diary;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;

import java.util.List;

public class MapActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 2; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 10000; // in Milliseconds
    protected LocationManager locationManager;
    protected MyLocationListener myListener;
//    protected Button locate;
    protected Button track;
    protected Button end;
    public double start_lat;
    public double start_long;
    public double end_lat;
    public double end_long;

    public RouteInfo routeInfo = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        track = (Button) findViewById(R.id.track);
        end = (Button) findViewById(R.id.end);
        showCurrentLocation();
        showTrackButton();
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (locationManager == null) {
                    System.out.println("LocationManager is null");
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    MadhhDiaryUtil.getMadhhDiaryUtil().toggleTrackState(getSharedPreferences("trac_pref", MODE_PRIVATE), "On");
                    showTrackButton();
//                }

                startTrack();
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locationManager != null) {
                    stopTrack();
                    MadhhDiaryUtil.getMadhhDiaryUtil().toggleTrackState(getSharedPreferences("trac_pref", MODE_PRIVATE), "Off");
                    showTrackButton();
                    Toast.makeText(MapActivity.this,
                            "Data saving",
                            Toast.LENGTH_LONG).show();
                    routeInfo.saveRouteInfo();
                    routeInfo = null;
                }
            }
        });
    }

    public void showTrackButton(){
        boolean state = MadhhDiaryUtil.getMadhhDiaryUtil().getTrackState(getSharedPreferences("trac_pref", MODE_PRIVATE));

        if(state){
            track.setVisibility(View.INVISIBLE);
            end.setVisibility(View.VISIBLE);
        }
        else{
            track.setVisibility(View.VISIBLE);
            end.setVisibility(View.INVISIBLE);
        }
    }

    private class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
            String message = String.format(
                    "New Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            routeInfo.setParseGeoPoint(new ParseGeoPoint( location.getLatitude(),location.getLongitude()));
            connectLocations(routeInfo.getParseGeoPoint());
            end_lat = location.getLatitude();
            end_long = location.getLongitude();
            Toast.makeText(MapActivity.this, message, Toast.LENGTH_LONG).show();

        }

        public void onStatusChanged(String s, int i, Bundle b) {
            Toast.makeText(MapActivity.this, "Provider status changed",
                    Toast.LENGTH_LONG).show();

        }

        public void onProviderDisabled(String s) {
            Toast.makeText(MapActivity.this,
                    "Provider disabled by the user. GPS turned off",
                    Toast.LENGTH_LONG).show();
        }

        public void onProviderEnabled(String s) {
            Toast.makeText(MapActivity.this,
                    "Provider enabled by the user. GPS turned on",
                    Toast.LENGTH_LONG).show();
        }

    }



    protected void showCurrentLocation() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        showInMap(location);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }


    private void showInMap(Location plot) {

            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();

        if (plot != null) {
            String message = String.format(
                    "Current my Location \n Longitude: %1$s \n Latitude: %2$s",
                    plot.getLongitude(), plot.getLatitude()
            );

            Toast.makeText(MapActivity.this, message,
                    Toast.LENGTH_LONG).show();

            mMap.addMarker(new MarkerOptions().position(new LatLng(plot.getLatitude(), plot.getLongitude())).title("Marker"));


            CameraUpdate center= CameraUpdateFactory.newLatLng(new LatLng(plot.getLatitude(), plot.getLongitude()));
            CameraUpdate zoom= CameraUpdateFactory.zoomTo(15);

            mMap.moveCamera(center);
            mMap.animateCamera(zoom);
        }
    }

    public void startTrack()
    {
        myListener = new MyLocationListener();
        mMap.clear();
        routeInfo = new RouteInfo();
        routeInfo.setUser();
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                .getMap();

        start_lat=location.getLatitude();
        start_long=location.getLongitude();
        routeInfo.setParseGeoPoint(new ParseGeoPoint(start_lat, start_long));

        mMap.addMarker(new MarkerOptions().position(new LatLng(start_lat, start_long)).title("Marker").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        CameraUpdate center= CameraUpdateFactory.newLatLng(new LatLng(start_lat,start_long));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);

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
         Toast.makeText(MapActivity.this, message, Toast.LENGTH_LONG).show();
         locationManager.removeUpdates(myListener);
         myListener = null;
         locationManager = null;
     }

    public void connectLocations(List<ParseGeoPoint> locationTrac)
    {
//        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
//                .getMap();
//
//        mMap.addMarker(new MarkerOptions().position(new LatLng(locationTrac.get(0).getLatitude(),locationTrac.get(0).getLongitude())).title("Marker"));
        PolylineOptions polyLineOptions ;
        for (int j = 0; j < locationTrac.size()-1; j++){
            polyLineOptions = new PolylineOptions();
            LatLng src = new LatLng(locationTrac.get(j).getLatitude(),locationTrac.get(j).getLongitude());
            LatLng dest = new LatLng(locationTrac.get(j+1).getLatitude(),locationTrac.get(j+1).getLongitude());
            polyLineOptions.add(new LatLng(src.latitude, src.longitude),
                    new LatLng(dest.latitude, dest.longitude)).width(7).color(Color.BLUE).geodesic(true);
            mMap.addPolyline(polyLineOptions);

        }
    }


}
