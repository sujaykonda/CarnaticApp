package com.example.carnaticapp.firebasefirstore;

import android.location.Location;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

public class Concert {
    private Timestamp startTime;
    private Timestamp endTime;
    private List<String> artists;
    private String name;
    public Concert(){}
    public Concert(String name, Timestamp startTime, Timestamp endTime, String[] artists) throws ParseException{
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.artists = Arrays.asList(artists);
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public List<String> getArtists() {
        return artists;
    }

    public String getName() {
        return name;
    }

}
