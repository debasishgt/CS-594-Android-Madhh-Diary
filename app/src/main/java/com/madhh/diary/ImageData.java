package com.madhh.diary;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("ImageData")
public class ImageData extends ParseObject{

    //ParseFile file;
    //String datetime;
    //String flash;
    double gps_latitude;
    //String gps_latitude_ref;
    double gps_longitude;

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
        put("picName", this.picName);
    }

    String picName;
    //String gps_longitude_ref;
    //String img_length;
    //String img_width;
    //String make;
    //String model;
    //String orientation;
    //String white_balance;

    ParseFile file;

    public ImageData(){

    };

    public void setUser(){
        put("author", ParseUser.getCurrentUser());
    }

    public ParseFile getFile() {
        return file;
    }

    public void setFile(ParseFile file) {
        //this.file = file;
        put("imageFile",file);
    }

//    public String getDatetime() {
//        return datetime;
//    }

//    public void setDatetime(String datetime) {
//        //this.datetime = datetime;
//        put("datetime",datetime);
//    }

    public double getGps_latitude() {
        return gps_latitude;
    }

    public void setGps_latitude(double gps_latitude) {
        //this.gps_latitude = gps_latitude;
        put("gps_latitude",gps_latitude);
    }

    public double getGps_longitude() {
        return gps_longitude;
    }

//    public String getGps_latitude_ref() {
//        return gps_latitude_ref;
//    }

//    public void setGps_latitude_ref(String gps_latitude_ref) {
//        put("gps_latitude_ref",gps_latitude_ref);
//    }

//    public String getGps_longitude_ref() {
//        return gps_longitude_ref;
//    }

//    public void setGps_longitude_ref(String gps_longitude_ref) {
//        put("gps_longitude_ref",gps_longitude_ref);
//    }

    public void setGps_longitude(double gps_longitude) {
        //this.gps_longitude = gps_longitude;
        put("gps_longitude",gps_longitude);
    }

//    public String getImg_length() {
//        return img_length;
//    }

//    public void setImg_length(String img_length) {
//        //this.img_length = img_length;
//        put("img_length",img_length);
//    }

//    public String getImg_width() {
//        return img_width;
//    }

//    public void setImg_width(String img_width) {
//        //this.img_width = img_width;
//        put("img_width",img_width);
//    }

//    public String getModel() {
//        return model;
//    }

//    public void setModel(String model) {
//        //this.model = model;
//        put("model",model);
//    }

//    public String getMake() {
//        return make;
//    }

//    public void setMake(String make) {//this.make = make;
//        put("make",make);
//    }

//    public String getOrientation() {
//        return orientation;
//    }

//    public void setOrientation(String orientation) {
//        //this.orientation = orientation;
//        put("orientation",orientation);
//    }

//    public String getWhite_balance() {
//        return white_balance;
//    }

//    public void setWhite_balance(String white_balance) {
//        //this.white_balance = white_balance;
//        put("white_balance",white_balance);
//    }
}