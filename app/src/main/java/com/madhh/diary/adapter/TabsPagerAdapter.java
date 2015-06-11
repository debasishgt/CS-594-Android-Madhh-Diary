package com.madhh.diary.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.madhh.diary.tabs.GamesFragment;
import com.madhh.diary.tabs.MoviesFragment;
import com.madhh.diary.tabs.TopRatedFragment;


public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		Fragment f;
		switch (index) {
		case 0:
			// Top Rated fragment activity
			return new TopRatedFragment();
		case 1:
			// Games fragment activity
			f = new GamesFragment();
			return f;
		case 2:
			// Movies fragment activity
			return new MoviesFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
