package com.echessa.noteapp;
// Armor.java
import com.parse.ParseObject;
import com.parse.Parse;
import android.app.Application;
import android.text.format.Time;

import com.parse.ParseClassName;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by debasish on 6/8/2015.
 */
@ParseClassName("BaseTable")
public class BaseTable extends ParseObject {

    //Time time;
    int[] events;
    String objectId;
    public BaseTable(){}
    public BaseTable(String objectId, String date){
        setTime(date);
        setObjectId(objectId);
    }
    public void setObjectId(String objectId){
        this.objectId = objectId;
    }
    public Time getDate() {
        //return time;
        return null;
    }
    public void setTime(String dateStr) {
        //time = new Time();
        //time.setToNow();
        put("date", dateStr);
    }

    public void addEvent(int event){
        addAll("events", Arrays.asList(event));
        //put("events", event);
    }
}
