package com.example.carnaticapp;

import android.os.Bundle;

import com.example.carnaticapp.firebasefirstore.Artist;
import com.example.carnaticapp.firebasefirstore.ConnectToDB;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import androidx.appcompat.widget.Toolbar;


import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter<String> arrayAdapter;
    final ConnectToDB connection = new ConnectToDB();
    HashMap<String, Artist> artists = new HashMap<>();
    private TextView mHeaderView;
 //   private ArrayList<Artist> mArtistsList;
    ArtistsAdapter mArtistAdapter;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mHeaderView = (TextView) findViewById(R.id.my_header);
        ListView listView = (ListView)findViewById(R.id.my_list);
        mHeaderView.setText("Artists");
        mArtistAdapter = new ArtistsAdapter(this,  new ArrayList<>(artists.values()));
//        this.setContentView(listView);
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
                           //   arrayAdapter.addAll( new ArrayList<>(artists.keySet()));
                           //   arrayAdapter.notifyDataSetChanged();
                                mArtistAdapter.addAll( new ArrayList<>(artists.values()));
                                mArtistAdapter.notifyDataSetChanged();

                                System.out.println(" Notified data changed");



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
                arrayAdapter.getFilter().filter(newText);
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
