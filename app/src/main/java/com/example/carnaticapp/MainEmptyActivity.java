package com.example.carnaticapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carnaticapp.ui.login.LoginActivity;

public class MainEmptyActivity extends AppCompatActivity {

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        Intent activityIntent;
        // go straight to main if a token is stored
        activityIntent = new Intent(this, LoginActivity.class);
        startActivity(activityIntent);

        finish();

    }

}

