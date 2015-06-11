package com.madhh.diary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.HashMap;
import java.util.List;

public class MapRetrive extends FragmentActivity implements GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private List<Note> posts;
    private Marker marker;
    private HashMap<Marker,String[]> mMarkers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_retrive);
       // mMap.setOnMarkerClickListener(this);
        setUpMapIfNeeded();
        refreshPostList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
      //  refreshPostList();
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
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
      //  mMap.setOnInfoWindowClickListener(this);
    }


//    public void fetchData()
//    {
//        ParseQuery<BaseTable> query = ParseQuery.getQuery(Note.class);
////query.whereEqualTo("date", ParseUser.getCurrentUser().get("date"));
//        query.whereEqualTo("date", getToday());
//        query.findInBackground(new FindCallback<BaseTable>() {
//            @Override
//            public void done(List<BaseTable> results, ParseException e) {
//                Toast.makeText(getApplicationContext(), "this is my Toast message!!! =)", Toast.LENGTH_LONG).show();
//                for (BaseTable a : results) {
//// ...
//                    ArrayList<Integer> events = new ArrayList<Integer>();
//                    events = (ArrayList<Integer>) a.get("events");
//                    events.add(23);
//                    a.put("events", events);
////a.addEvent(22);
//                    Toast.makeText(getApplicationContext(), "this is my Toast message22!!! =)", Toast.LENGTH_LONG).show();
//                    a.saveInBackground();
//                }
//            }
//        });
//    }


    private void refreshPostList() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
        query.whereEqualTo("author", ParseUser.getCurrentUser());

        setProgressBarIndeterminateVisibility(true);
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                .getMap();

        //mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);

        query.findInBackground(new FindCallback<ParseObject>() {

            @SuppressWarnings("unchecked")
            @Override
            public void done(List<ParseObject> postList, ParseException e) {
                setProgressBarIndeterminateVisibility(false);
                if (e == null) {
                    // If there are results, update the list of posts
                    // and notify the adapter
                    //posts.clear();
                    System.out.println("====================================================" + postList);
                    double lat;
                    double longt;
                    mMarkers = new HashMap<Marker, String[]>();
                    for (ParseObject post : postList) {
                        System.out.println("---------------------------------------" + post.getParseGeoPoint("geoPoint"));
                        lat = post.getParseGeoPoint("geoPoint").getLatitude();
                        longt= post.getParseGeoPoint("geoPoint").getLongitude();
                        String[] data =
                                {
                                post.getObjectId().toString(),
                                post.getString("title").toString(),
                                post.getString("content").toString()
                            };
                        String str = post.getString("content").toString();
                       // String snip = str.substring(10, 15);
                       // System.out.println("-------------------------------------------------------"+snip);
                        marker =  mMap.addMarker(new MarkerOptions().position(new LatLng(lat, longt)).title(post.getString("title")).icon(BitmapDescriptorFactory.fromResource(R.drawable.notes)).snippet("Click To Open" ));
                        mMarkers.put(marker,data);
                        System.out.println("-----------------------------------------------------------------" + post.getString("title"));
//                        Note note = new Note(post.getObjectId(), post
//                                .getString("title"), post.getString("content"), post.getParseGeoPoint("parseGeoPoint"));
                       // posts.add(note);
                        CameraUpdate center= CameraUpdateFactory.newLatLng(new LatLng(lat, longt));
                        CameraUpdate zoom=CameraUpdateFactory.zoomTo(100);

                        mMap.moveCamera(center);
                        mMap.animateCamera(zoom);
                    }
                } else {

                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });

    }

//    @Override
//    public boolean onMarkerClick(Marker marker) {
//
//        Toast.makeText(this, marker.getTitle(), Toast.LENGTH_LONG).show();
//        return true;
//
//    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, marker.getTitle(), Toast.LENGTH_LONG).show();
        //Note note = posts.get(position);
        System.out.println(mMarkers.get(marker));
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtra("noteId", mMarkers.get(marker)[0]);
        intent.putExtra("noteTitle",mMarkers.get(marker)[1]);
        intent.putExtra("noteContent",mMarkers.get(marker)[2]);
        startActivity(intent);
        // When touch InfoWindow on the market, display another screen.
       // Intent intent = new Intent(this, Another.class);
       // startActivity(intent);
    }


}
