package com.madhh.diary;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by debasish on 6/10/2015.
 */
@ParseClassName("RouteInfo")
public class RouteInfo extends ParseObject {

    private String date;
    private ArrayList<ParseGeoPoint> parseGeoPoints;

    private ParseGeoPoint startPoint;
    private ParseGeoPoint stopPoint;

    private String username;

    public RouteInfo(){
        setDate();
        setUsername(ParseUser.getCurrentUser().getUsername());
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate() {
        this.date = getToday();
        put("date", this.date);
    }

    public ArrayList<ParseGeoPoint> getParseGeoPoint() {
        return parseGeoPoints;
    }

    public void setParseGeoPoint(ParseGeoPoint parseGeoPoint) {
        if(parseGeoPoints == null)
            parseGeoPoints = new ArrayList<ParseGeoPoint>();
        this.parseGeoPoints.add(parseGeoPoint);
    }
    public void saveRouteInfo() {
        put("route", parseGeoPoints);
    }

    public ParseGeoPoint getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(ParseGeoPoint startPoint) {
        this.startPoint = startPoint;
    }

    public ParseGeoPoint getStopPoint() {
        return stopPoint;
    }

    public void setStopPoint(ParseGeoPoint stopPoint) {
        this.stopPoint = stopPoint;
    }

    public String getToday(){
        DateFormat dateFormat = new SimpleDateFormat("MMM/dd/yyyy");
        Date date = new Date();
        String dateStr = dateFormat.format(date);

        return dateStr;
    }
}

