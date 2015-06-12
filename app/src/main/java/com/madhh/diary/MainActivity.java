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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {

  //  private List<String> posts;

    private MenuItem track_menu_off;
    private MenuItem track_menu_on;

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
        String[] demoDates = {"Jan 1st", "Jan 2nd", "Jan 3rd", "Jan 4th", "Jan 5th",
                "Jan 6th", "Jan 7th", "Jan 8th", "Jan 9th", "Jan 10th",
                "Jan 11th", "Jan 12th", "Jan 13th", "Jan 14th", "Jan 15th"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_item_layout, demoDates);
        setListAdapter(adapter);

        refreshPostList();
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
                refreshPostList();
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

    private void refreshPostList() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
        query.whereEqualTo("author", ParseUser.getCurrentUser());

        //setProgressBarIndeterminateVisibility(true);

        // fetching data from Parse.com and parsing note data
//        query.findInBackground(new FindCallback<ParseObject>() {
//
//            @SuppressWarnings("unchecked")
//            @Override
//            public void done(List<ParseObject> postList, ParseException e) {
//              setProgressBarIndeterminateVisibility(false);
//                if (e == null) {
//                    // If there are results, update the list of posts
//                    // and notify the adapter
//                    posts.clear();
//                    for (ParseObject post : postList) {
//                        Note note = new Note(post.getObjectId(),
//                                post.getString("title"),
//                                post.getString("content"),
//                                post.getParseGeoPoint("parseGeoPoint"));
//                        posts.add(note);
//                    }
//                    ((ArrayAdapter<Note>) getListAdapter())
//                            .notifyDataSetChanged();
//                } else {
//
//                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
//                }
//            }
//        });

    }

}