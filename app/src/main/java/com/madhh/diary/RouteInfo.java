package com.madhh.diary;

import com.parse.ParseClassName;
import com.parse.ParseException;
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


    private ArrayList<ParseGeoPoint> parseGeoPoints;

    private ParseGeoPoint startPoint;
    private ParseGeoPoint stopPoint;

    public RouteInfo(){

    };
    public void setUser(){
        put("author", ParseUser.getCurrentUser());
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
        try {
            this.save();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public ParseGeoPoint getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(ParseGeoPoint startPoint) {
        this.startPoint = startPoint;
        put("startPoint", this.startPoint);
    }

    public ParseGeoPoint getStopPoint() {
        return stopPoint;
    }

    public void setStopPoint(ParseGeoPoint stopPoint) {
        this.stopPoint = stopPoint;
        put("stopPoint", this.stopPoint);
    }


}

