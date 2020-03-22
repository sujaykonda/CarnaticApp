package com.example.carnaticapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.example.carnaticapp.firebasefirstore.Artist;
import com.example.carnaticapp.firebasefirstore.Concert;
import com.example.carnaticapp.firebasefirstore.ConnectToDB;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
import android.widget.LinearLayout;
import android.widget.ListView;

import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
   // ArrayAdapter<String> arrayAdapter;
    final ConnectToDB connection = new ConnectToDB();
    static HashMap<String, Artist> artists = new HashMap<>();
    static HashMap<String, Concert> concerts = new HashMap<>();
    ListView listView;
 //   private ArrayList<Artist> mArtistsList;
    ArtistsAdapter mArtistAdapter;

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void startLocService(){
        Intent intent = new Intent(MainActivity.this, LocationService.class);
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
                                    Artist artist = document.toObject(Artist.class);
                                    System.out.println(artist.getUsername());
                                    artists.put(artist.getUsername(), artist);
                                }
                                mArtistAdapter.addAll(new ArrayList<>(artists.values()));
                                mArtistAdapter.notifyDataSetChanged();

                                System.out.println(" Notified data changed");


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



      /*  List<String> myList = new ArrayList<>();
        myList.add("saxophone");
        myList.add("violinist");*/



      /* FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */
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
