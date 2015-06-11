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
		//ParseObject.registerSubclass(EventBase.class);
		Parse.initialize(this, "6rPUbJ12Wa6IHEwYpVzQGEOsR6gofkrJfJJ4EkYO", "45nI4grkEwnZSqHcWkwlhmNR0gNUJwHYle2ZAMCe");

//		ParseObject testObject = new ParseObject("TestObject");
//		testObject.put("foo", "bar");
//		testObject.saveInBackground();
	}

}
