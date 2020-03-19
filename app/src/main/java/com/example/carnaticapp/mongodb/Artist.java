package com.example.carnaticapp.mongodb;


import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;

import java.util.ArrayList;
import java.util.List;

public class Artist {
    public static String username;
    public static String password;
    public static String type;
    public static String gender;
    public static List<Concert> concerts;
    public static String profile;
    public Artist(String Username, String Gender, String Type){
        username = Username;
        gender = Gender;
        type = Type;
        concerts = new ArrayList<Concert>();
        password = "";
        profile = "";
    }

    public void changeProfile(String profile){
        this.profile = profile;
    }

    public void addConcert(Concert concert){
        this.concerts.add(concert);
    }

}
