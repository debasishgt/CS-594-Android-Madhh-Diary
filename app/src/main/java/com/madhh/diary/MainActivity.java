package com.madhh.diary;

import java.util.List;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.madhh.diary.adapter.TabsPagerAdapter;
import com.parse.ParseUser;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
//public class MainActivity extends ListActivity implements ActionBar.TabListener {

	private List<Note> posts;
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;

	// Tab titles
	private String[] tabs = { "Events", "Notes", "Setting" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_main);
		
		ParseUser currentUser = ParseUser.getCurrentUser();
		//redirect the user to login if he hasn't login
		if (currentUser == null) {
			loadLoginView();
		}

		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		if(actionBar==null) {
			System.out.println("actionBar is Null");
			//actionBar = getSupportActionBar();
		}
		else
			System.out.println("actionBar is NOT Null");

//		viewPager.setAdapter(mAdapter);
//		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});


//		shifted to GamesFragment
//		posts = new ArrayList<Note>();
//		ArrayAdapter<Note> adapter = new ArrayAdapter<Note>(this,
//				R.layout.list_item_layout, posts);
//		setListAdapter(adapter);
//
//		refreshPostList();
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
			//refreshPostList();
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

		case R.id.action_pic: {
			Intent picIntent = new Intent(this, PicActivity.class);
			startActivity(picIntent);
			// Do something when user selects Settings from Action Bar overlay
			break;
		}
			case R.id.action_map: {
				Intent mapIntent = new Intent(this, MapActivity.class);
				startActivity(mapIntent);
				// Do something when user selects Settings from Action Bar overlay
				break;
			}
		}

		return super.onOptionsItemSelected(item);
	}
	
	//@Override
//	protected void onListItemClick(ListView l, View v, int position, long id) {
//
//		Note note = posts.get(position);
//		Intent intent = new Intent(this, EditNoteActivity.class);
//		intent.putExtra("noteId", note.getId());
//		intent.putExtra("noteTitle", note.getTitle());
//		intent.putExtra("noteContent", note.getContent());
//		startActivity(intent);
//
//	}


	//moved to GamesFragment
/*	private void refreshPostList() {

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
						Note note = new Note(post.getObjectId(), post
								.getString("title"), post.getString("content"));
						posts.add(note);
					}
				//	((ArrayAdapter<Note>) getListAdapter())
				//			.notifyDataSetChanged();
				} else {

					Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
				}
			}
		});

	}

	*/

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

	}
}
