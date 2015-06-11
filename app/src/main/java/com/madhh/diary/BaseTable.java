package com.madhh.diary;
// Armor.java
import com.parse.ParseObject;

import android.text.format.Time;

import com.parse.ParseClassName;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by debasish on 6/8/2015.
 */
@ParseClassName("BaseTable")
public class BaseTable extends ParseObject {

    String objectId;
    private String date;
    //private String username;

    public BaseTable(){
        setDate();
        setUser();
    }
    public void setObjectId(String objectId){
        this.objectId = objectId;
    }

    public void setUser() {
        //this.username = username;
        put("author", ParseUser.getCurrentUser());
    }
    public Time getDate() {
        //return time;
        return null;
    }
    public void setDate() {
        this.date = getToday();
        put("date", this.date);
    }
    public void addEvent(int event){
        addAll("events", Arrays.asList(event));
        //put("events", event);
    }
    public String getToday(){
        DateFormat dateFormat = new SimpleDateFormat("MMM/dd/yyyy");
        Date date = new Date();
        String dateStr = dateFormat.format(date);

        return dateStr;
    }
}
