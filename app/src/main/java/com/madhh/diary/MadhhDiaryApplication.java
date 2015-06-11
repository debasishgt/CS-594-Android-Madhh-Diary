package com.madhh.diary;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class MadhhDiaryApplication extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		ParseObject.registerSubclass(EventBase.class);
		ParseObject.registerSubclass(BaseTable.class);
		ParseObject.registerSubclass(RouteInfo.class);
		ParseObject.registerSubclass(ImageData.class);
		Parse.initialize(this, "mJj4V3I4DTkHIffqgOurzLbQWbtykNKJXYi961Il", "qpNff3yWgN9b5R8xolXkYWuq553SDa1LIPFqvJN5");

//		ParseObject testObject = new ParseObject("TestObject");
//		testObject.put("foo", "bar");
//		testObject.saveInBackground();
	}

}
