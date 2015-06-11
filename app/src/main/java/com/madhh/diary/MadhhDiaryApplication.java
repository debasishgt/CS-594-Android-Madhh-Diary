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
		Parse.initialize(this, "ZWM5rHWPSPQNZdEn5Rh0qtJ4Z5MOG3sK0CZ4k2HX", "RSidu4Jlrxu809Kj5guL1RXjJYHd2PN178yTT2lN");

//		ParseObject testObject = new ParseObject("TestObject");
//		testObject.put("foo", "bar");
//		testObject.saveInBackground();
	}

}
