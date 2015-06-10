package com.madhh.diary;

import com.parse.ParseFile;
import com.parse.ParseObject;

public class Image extends ParseObject{

    ParseFile file;
    String datetime;
    //String flash;
    String gps_latitude;
    //String gps_latitude_ref;
    String gps_longitude;
    //String gps_longitude_ref;
    String img_length;
    String img_width;
    String make;
    String model;
    String orientation;
    String white_balance;

    public ParseFile getFile() {
        return file;
    }

    public void setFile(ParseFile file) {
        //this.file = file;
        put("imageFile",file);
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        //this.datetime = datetime;
        put("datetime",datetime);
    }

    public String getGps_latitude() {
        return gps_latitude;
    }

    public void setGps_latitude(String gps_latitude) {
        //this.gps_latitude = gps_latitude;
        put("gps_latitude",gps_latitude);
    }

    public String getGps_longitude() {
        return gps_longitude;
    }

    public void setGps_longitude(String gps_longitude) {
        //this.gps_longitude = gps_longitude;
        put("gps_longitude",gps_longitude);
    }

    public String getImg_length() {
        return img_length;
    }

    public void setImg_length(String img_length) {
        //this.img_length = img_length;
        put("img_length",img_length);
    }

    public String getImg_width() {
        return img_width;
    }

    public void setImg_width(String img_width) {
        //this.img_width = img_width;
        put("img_width",img_width);
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        //this.model = model;
        put("model",model);
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        //this.make = make;
        put("make",make);
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        //this.orientation = orientation;
        put("orientation",orientation);
    }

    public String getWhite_balance() {
        return white_balance;
    }

    public void setWhite_balance(String white_balance) {
        //this.white_balance = white_balance;
        put("white_balance",white_balance);
    }
}
