package com.madhh.diary;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends ListActivity {

  //  private List<String> posts;

    private MenuItem track_menu_off;
    private MenuItem track_menu_on;
    private List<String> dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            loadLoginView();
        }
        MadhhDiaryUtil.getMadhhDiaryUtil().toggleTrackState(getSharedPreferences("trac_pref", MODE_PRIVATE), "Off");
       // posts = new ArrayList<String>();

        //create List of Items
        dates = new ArrayList<String>();

        String[] demoDates = {"No connection!!!"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_item_layout, demoDates);
        setListAdapter(adapter);

        refreshDateList();

        if(dates != null)
        {
            adapter = new ArrayAdapter<String>(this, R.layout.list_item_layout, dates);
            setListAdapter(adapter);
        }
        else
        {
            System.out.println("dates list is null");
        }
    }



    public void setTrackMenuButtonState(boolean state){
        if(state) {
            track_menu_off.setVisible(true);
            track_menu_on.setVisible(false);
        }
        else {
            track_menu_off.setVisible(false);
            track_menu_on.setVisible(true);
        }
    }
    private void loadLoginView() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        track_menu_on = menu.findItem(R.id.track_on);
        track_menu_off = menu.findItem(R.id.track_off);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        //track_menu_on = (Button) menu.findItem(R.id.track_on);
        //track_menu_off = (Button) menu.findItem(R.id.track_off);
        setTrackMenuButtonState(MadhhDiaryUtil.getMadhhDiaryUtil().getTrackState(getSharedPreferences("trac_pref", MODE_PRIVATE)));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {

            case R.id.action_refresh: {
                refreshDateList();
                break;
            }

            case R.id.action_new: {
                Intent intent = new Intent(this, EditNoteActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.notes: {
                Intent intent = new Intent(this, NoteListActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.action_logout:
                ParseUser.logOut();
                loadLoginView();
                break;
            case R.id.track_on: {
                // Do something when user selects Settings from Action Bar overlay
                MadhhDiaryUtil.getMadhhDiaryUtil().toggleTrackState(getSharedPreferences("trac_pref", MODE_PRIVATE), "On");
                RouteManager.getRouteManager(this).startTrack();
                break;
            }
            case R.id.track_off: {
                // Do something when user selects Settings from Action Bar overlay
                MadhhDiaryUtil.getMadhhDiaryUtil().toggleTrackState(getSharedPreferences("trac_pref", MODE_PRIVATE), "Off");
                RouteManager.getRouteManager(this).stopTrack();
                break;
            }
            case R.id.action_settings: {
                // Do something when user selects Settings from Action Bar overlay
                break;
            }

            case R.id.action_pic:{
                Intent picIntent = new Intent(this, PicActivity.class);
                startActivity(picIntent);
                // Do something when user selects Settings from Action Bar overlay
                break;
            }
			case R.id.action_map_retrive: {
				Intent mapRetive = new Intent(this, MapRetrive.class);
				startActivity(mapRetive);
				// Do something when user selects Settings from Action Bar overlay
				break;
			}
            case R.id.action_map: {
                Intent mapIntent = new Intent(this, MapActivity.class);
                startActivity(mapIntent);
                // Do something when user selects Settings from Action Bar overlay
                //RouteInfo routeInfo = new RouteInfo();
                //routeInfo.setDate();

//                routeInfo.setParseGeoPoint(new ParseGeoPoint(34.13, -118.12));
//                routeInfo.setParseGeoPoint(new ParseGeoPoint(34.14, -118.13));
//                routeInfo.setParseGeoPoint(new ParseGeoPoint(34.15, -118.14));
//                routeInfo.saveRouteInfo();
//                try {
//                    routeInfo.save();
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        //open new activity here

//        Note note = posts.get(position);
        Intent intent = new Intent(this, EditNoteActivity.class);

        Toast.makeText(this, l.toString(), Toast.LENGTH_SHORT).show();
//        intent.putExtra("message", l.toString());
//        intent.putExtra("noteTitle", note.getTitle());
//        intent.putExtra("noteContent", note.getContent());
//        startActivity(intent);

    }

    //gets all the notes from the Parse DB
    private void refreshDateList() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
        query.whereEqualTo("author", ParseUser.getCurrentUser());

        setProgressBarIndeterminateVisibility(true);

        query.findInBackground(new FindCallback<ParseObject>() {

            @SuppressWarnings("unchecked")
            @Override
            public void done(List<ParseObject> postList, ParseException e) {
                setProgressBarIndeterminateVisibility(false);
                if (e == null) {
                    // If there are results, update the list of posts
                    // and notify the adapter
                    dates.clear();
                    for (ParseObject post : postList)
                    {
//                        System.out.println("Keys Sets: ");
//                        for(String key: post.keySet())
//                            System.out.println(key);

                        Date createdAt = post.getCreatedAt();

                        if(createdAt == null)
                            System.out.println("createdAt is Null");
                        else {
                            dates.add(dateToString(createdAt));
                            System.out.println(dates.get(dates.size()-1));
                        }

                    }
                    ((ArrayAdapter<Note>) getListAdapter())
                            .notifyDataSetChanged();
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }

                System.out.println("dates before sorting:");
                for(String s: dates)
                    System.out.println(s);
                //sort the array and then remove repeated date from the list
                //removing repeated date string from List
                dates = removeDuplicate(dates);

                System.out.println("After sorting: size= " + dates.size());

            }
        });

    }

    // format date to string formatted: MMM/dd/yyyy
    private String dateToString(Date date)
    {
        DateFormat dateFormat = new SimpleDateFormat("MMM/dd/yyyy");
        return dateFormat.format(date);
    }

    //remove duplicate and sorts the list
    private List<String> removeDuplicate(List<String> list)
    {
        System.out.println("List pased to removeDuplicate(): " + list.size());

        for (String s: list)
            System.out.println(s);

        Set<String> set = new HashSet<String>();
        set.addAll(list);
        System.out.println("Size of the Set: " + set.size());
        list.clear();
        list.addAll(set);
        System.out.println("Size of the List: " + list.size());

        //sort the list
        Collections.sort(list, new Comparator<String>() {
            DateFormat f = new SimpleDateFormat("MMM/dd/yyyy");

            @Override
            public int compare(String o1, String o2) {
                try {
                    return f.parse(o1).compareTo(f.parse(o2));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 1;
            }
        });

        System.out.println("Size of the List after sort: " + list.size());

        return list;
    }

}