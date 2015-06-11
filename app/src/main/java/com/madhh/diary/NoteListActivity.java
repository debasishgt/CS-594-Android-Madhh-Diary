package com.madhh.diary;

import android.app.ListActivity;
import android.content.Intent;
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

public class NoteListActivity extends ListActivity {

    private List<Note> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_note_list);

        Toast.makeText(this,"this is new activity",Toast.LENGTH_SHORT).show();

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            loadLoginView();
        }

        posts = new ArrayList<Note>();
        ArrayAdapter<Note> adapter = new ArrayAdapter<Note>(this,
                R.layout.list_item_layout, posts);
        setListAdapter(adapter);

        refreshPostList();
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

            case R.id.action_logout:
                ParseUser.logOut();
                loadLoginView();
                break;

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
            case R.id.action_map: {
                //Intent mapIntent = new Intent(this, MapActivity.class);
                //startActivity(mapIntent);
                // Do something when user selects Settings from Action Bar overlay
                RouteInfo routeInfo = new RouteInfo();
                routeInfo.setUser();

                routeInfo.setParseGeoPoint(new ParseGeoPoint(34.13, -118.12));
                routeInfo.setParseGeoPoint(new ParseGeoPoint(34.14, -118.13));
                routeInfo.setParseGeoPoint(new ParseGeoPoint(34.15, -118.14));
                routeInfo.saveRouteInfo();
                try {
                    routeInfo.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Note note = posts.get(position);
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtra("noteId", note.getId());
        intent.putExtra("noteTitle", note.getTitle());
        intent.putExtra("noteContent", note.getContent());
        startActivity(intent);

    }

    private void refreshPostList() {

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
                    posts.clear();
                    for (ParseObject post : postList) {
                        Note note = new Note(post.getObjectId(),
                                post.getString("title"),
                                post.getString("content"),
                                post.getParseGeoPoint("parseGeoPoint"));
                        posts.add(note);
                    }
                    ((ArrayAdapter<Note>) getListAdapter())
                            .notifyDataSetChanged();
                } else {

                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });

    }
}