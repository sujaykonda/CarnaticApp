package com.example.carnaticapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.example.carnaticapp.firebasefirstore.Artist;
import com.example.carnaticapp.firebasefirstore.Concert;
import com.example.carnaticapp.firebasefirstore.ConnectToDB;
import com.example.carnaticapp.ui.profile.ProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    private String userId;
    final ConnectToDB connection = new ConnectToDB();
    static HashMap<String, Artist> artists = new HashMap<>();
    static HashMap<String, Concert> concerts = new HashMap<>();
    ListView listView;
    ArtistsAdapter mArtistAdapter;
    public static Artist artist;

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void startLocService(){
        Intent intent = new Intent(MainActivity.this, LocationService.class);
        System.out.println("Sujay :" + intent);
        startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startLocService();
                }else{
                    Toast.makeText(this, "Give me perms", Toast.LENGTH_LONG).show();
                }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent sendToMain = getIntent();
        userId = sendToMain.getStringExtra("userId");
        if(Build.VERSION.SDK_INT >= 23){
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                startLocService();
            }
        }else{
            startLocService();
        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      //  super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_bottom_navigation);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_profile, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        listView = (ListView)findViewById(R.id.my_list);
        mArtistAdapter = new ArtistsAdapter(this,  new ArrayList<>(artists.values()));

    /*    arrayAdapter =new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                new ArrayList<>(artists.keySet()));*/


      //  listView.setAdapter(arrayAdapter);
        listView.setAdapter(mArtistAdapter);
        connection.getDBInstance().collection("Artists")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Artist nextArtist = document.toObject(Artist.class);
                                    System.out.println(nextArtist.getUserId());
                                    artists.put(nextArtist.getUserId(), nextArtist);
                                }
                                mArtistAdapter.addAll(new ArrayList<>(artists.values()));
                                mArtistAdapter.notifyDataSetChanged();
                                System.out.println(" Notified data changed");
                                setArtist();
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                });
        connection.getDBInstance().collection("Concerts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Concert concert = document.toObject(Concert.class);
                                concerts.put(concert.getName(), concert);
                            }
                            System.out.println(concerts);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        }
    public void setArtist() {
        if(artists.containsKey(userId)){
            artist = artists.get(userId);
        }else{
            artist = new Artist(userId);
            Map<String, Object> newArtist = new HashMap<>();
            newArtist.put("firstName", null);
            newArtist.put("lastName", null);
            newArtist.put("gender", null);
            newArtist.put("category", null);
            newArtist.put("teacherName", null);
            newArtist.put("schoolName", null);
            newArtist.put("userId", userId);

            // Add a new document with a generated ID
            connection.getDBInstance().collection("Artists")
                    .add(newArtist)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            sendToNewProfile(artist.getUserId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
        }
        Bundle artistBundle = new Bundle();
        artistBundle.putString("firstName", artist.getFirstName());
        artistBundle.putString("lastName", artist.getLastName());
        artistBundle.putString("category", artist.getCategory());
        artistBundle.putString("contact", artist.getContact());
        artistBundle.putString("schoolName", artist.getSchoolName());
        artistBundle.putString("teacherName", artist.getTeacherName());

        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setArguments(artistBundle);
    }
    public void sendToNewProfile(String userId){
        Intent activityIntent = new Intent(this, NewProfileActivity.class).putExtra("userId", userId);
        startActivity(activityIntent);
        Toast.makeText(MainActivity.this, "Welcome! Please set your Profile.", Toast.LENGTH_SHORT).show();
    }



    public static HashMap<String, Artist> getArtists() {
        return artists;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_settings);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listView.setVisibility(View.VISIBLE);
                mArtistAdapter.clear();
                mArtistAdapter.addAll(new ArrayList<>(artists.values()));
                mArtistAdapter.notifyDataSetChanged();
                mArtistAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
