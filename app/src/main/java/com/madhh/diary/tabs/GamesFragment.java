package com.madhh.diary.tabs;

import android.annotation.TargetApi;
import android.app.ListFragment;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.madhh.diary.Note;
import com.madhh.diary.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

//public class GamesFragment extends Fragment {
public class GamesFragment extends Fragment {

	private List<Note> posts;
	private ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_games, container, false);
		rootView.findViewById(R.id.action_settings);
		//listView = (ListView) rootView.findViewById(R.id.dateList);
		listView = (ListView) rootView.findViewById(R.id.dateList);
		posts = new ArrayList<Note>();


		//cut paste from MainActivity
		ArrayAdapter<Note> adapter = new ArrayAdapter<Note>(this.getActivity(),
				R.layout.list_item_layout, posts);
		//	setListAdapter(adapter);

		listView.setAdapter(adapter);
		refreshPostList();
		return rootView;
	}

	//connects to internet and updates the notes from parse
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	private void refreshPostList() {

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
		query.whereEqualTo("author", ParseUser.getCurrentUser());

		getParentFragment().getActivity().setProgressBarIndeterminateVisibility(true);

		query.findInBackground(new FindCallback<ParseObject>() {

			@SuppressWarnings("unchecked")
			@Override
			public void done(List<ParseObject> postList, ParseException e) {
				getParentFragment().getActivity().setProgressBarIndeterminateVisibility(false);
				if (e == null) {
					// If there are results, update the list of posts
					// and notify the adapter
					posts.clear();
					for (ParseObject post : postList) {
						Note note = new Note(post.getObjectId(), post
								.getString("title"), post.getString("content"));
						posts.add(note);
					}
					if(listView.getAdapter() == null)
						System.out.println("listView is null");

					((ArrayAdapter<Note>) listView.getAdapter()).notifyDataSetChanged();

				} else {

					Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
				}
			}
		});

	}
}
