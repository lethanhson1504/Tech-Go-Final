package com.example.techgo;

import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.DatabaseReference;

public class Location_db {
    public double lat;
    public double lng;
    public String id;
    public Location_db(double lat,double lng){
        this.lat = lat;
        this.lng = lng;
    }

    public void createLocation(DatabaseReference ref){
        String key = ref.child("users").push().getKey();
        id = key;
        ref.child("location").child(key).setValue(this);
    }

    public double GetLat(){
        return lat;
    }

    public double GetLng(){
        return lng;
    }
}

