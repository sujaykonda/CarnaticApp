package com.example.carnaticapp.firebasefirstore;


import java.util.ArrayList;
import java.util.List;

public class Artist {
    private String username;
    private String type;
    private String gender;

    public Artist(){}

    public Artist(String username, String gender, String type){
        this.username = username;
        this.gender = gender;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public String getType() {
        return type;
    }

    public String getGender() {
        return gender;
    }
}
