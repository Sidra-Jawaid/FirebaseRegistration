package com.example.sidrajawaid.demofirebase.MainApplicationContent;

import android.graphics.Bitmap;
import android.net.Uri;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class DataModel {
    private String userEmail;
    private String userFullname;
    private String userAge;
    private String userLocation;
    private String userID;
    private Uri userImageUrl;
    //saved array list
   //private ArrayList savedlist;


    public DataModel(String email,String fullname, String age, String location,String id,Uri imageurl) {
        this.userFullname=fullname;
        this.userAge = age;
        this.userLocation = location;
        this.userID=id;
        this.userEmail=email;
        this.userImageUrl=imageurl;
    }

    /*public DataModel(ArrayList array) {
        this.savedlist=array;

    }*/
    public DataModel(String email,String fullname, String age, String location,String id) {
        this.userFullname=fullname;
        this.userAge = age;
        this.userLocation = location;
        this.userID=id;
        this.userEmail=email;

    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getUserID() {
        return userID;
    }

    public String getUserFullname() {
        return userFullname;
    }
    public void setUserFullname(String userFullname) {
        this.userFullname = userFullname;
    }

    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserAge() {
        return userAge;
    }
    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getUserLocation() {
        return userLocation;
    }
    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public Uri getUserImageUrl() {
        return userImageUrl;
    }
    public void setUserImageUrl(Uri userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    /*public ArrayList getSavedlist() {
        return savedlist;
    }
    public void setSavedlist(ArrayList savedlist) {
        this.savedlist = savedlist;
    }*/
}
