package com.madhh.diary;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class NoteAppApplication extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		ParseObject.registerSubclass(EventBase.class);
		ParseObject.registerSubclass(BaseTable.class);
		ParseObject.registerSubclass(RouteInfo.class);
		ParseObject.registerSubclass(ImageData.class);
		Parse.initialize(this, "EfXd2CSx5Ew3QyG2L9gJnM9IuDsFPqOmwRzMoapJ", "LxwjWMiUCVOhWfuZcu4rNym1Ezx8ypYpIxx1GMib");

//		ParseObject testObject = new ParseObject("TestObject");
//		testObject.put("foo", "bar");
//		testObject.saveInBackground();
	}

}
