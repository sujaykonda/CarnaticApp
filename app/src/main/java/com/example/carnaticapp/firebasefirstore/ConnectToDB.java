package com.example.carnaticapp.firebasefirstore;



import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

import static android.content.ContentValues.TAG;


public class ConnectToDB {


    private FirebaseFirestore db;


    public ConnectToDB(){
        db = FirebaseFirestore.getInstance();

    }

    public FirebaseFirestore getDBInstance(){
      return db;
    }

}