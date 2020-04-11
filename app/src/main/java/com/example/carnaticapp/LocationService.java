package com.example.carnaticapp;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationService extends Service {
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate(){
        super.onCreate();
System.out.println("OnCreate Sujay");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        System.out.println("Sujay :" + fusedLocationProviderClient);
        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult){
                System.out.println("Sujay locationResults:" + locationResult);
                super.onLocationResult(locationResult);
                Location loc = locationResult.getLastLocation();
                System.out.println("Sujay locationResult loc:" + loc);
                System.out.println("lat " + loc.getLatitude() + "long " + loc.getLongitude());
            }
        };

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println(" Sujay onStart" + startId);
        requestLocation();
        System.out.println(" Sujay onStart request Location");
        return super.onStartCommand(intent, flags, startId);
    }

    private void requestLocation(){
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        System.out.println(" Sujay onStart request Location success");
    }
}
