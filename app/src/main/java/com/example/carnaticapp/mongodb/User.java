package com.example.carnaticapp.mongodb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class User {
    public String username;
    public String password;
    public List<Concert> concerts;
    public String profile;
    public User(String username, String password){
        this.username = username;
        this.concerts = new ArrayList<Concert>();
        this.password = password;
        this.profile = "";
    }

    public void changeProfile(String profile){
        this.profile = profile;
    }

    public void addConcert(Concert concert){
        this.concerts.add(concert);
    }
}
