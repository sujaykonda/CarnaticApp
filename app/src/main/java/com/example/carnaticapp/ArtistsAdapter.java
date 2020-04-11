package com.example.carnaticapp;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.carnaticapp.firebasefirstore.Artist;
import com.example.carnaticapp.firebasefirstore.ConnectToDB;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

public class ArtistsAdapter extends ArrayAdapter<Artist> {

    List<Artist> artists;
    final ConnectToDB connection = new ConnectToDB();


    public ArtistsAdapter(Context context, List<Artist> object){
        super(context,0, object);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        System.out.println("getView");
           if(convertView == null){
               convertView =  ((Activity)getContext()).getLayoutInflater().inflate(R.layout.content_artist,parent,false);
           }
           TextView nameView = (TextView) convertView.findViewById(R.id.artist_name);
           TextView genderView = (TextView) convertView.findViewById(R.id.artist_gender);
           TextView typeView = (TextView) convertView.findViewById(R.id.artist_type);


            Artist artist = getItem(position);

            nameView.setText(artist.getFirstName()+"    ");
            genderView.setText(artist.getGender()+"    ");
            typeView.setText(artist.getCategory());

      /*  if(convertView == null){
            convertView =  ((Activity)getContext()).getLayoutInflater().inflate(R.layout.content_artist,parent,false);
        }
        final TableLayout tableLayout = (TableLayout)convertView.findViewById(R.id.table_layout_table);
        TableRow tableRow = new TableRow(getContext());
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        tableRow.setLayoutParams(layoutParams);
        Artist artist = getItem(position);

        TextView textView0 = new TextView(getContext());
        textView0.setText(artist.getUsername());
        tableRow.addView(textView0, 0);
        Space space0 = new Space(getContext());
        LayoutParams spaceLp0 = new LayoutParams(30,30);
        tableRow.addView(space0,spaceLp0);

        TextView textView1 = new TextView(getContext());
        textView1.setText(artist.getType());
        tableRow.addView(textView1, 1);

        TextView textView2 = new TextView(getContext());
        textView2.setText(artist.getGender());
        tableRow.addView(textView2, 2);

        tableLayout.addView(tableRow); */

        return convertView;
    }
    Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            List<Artist> tempList = new ArrayList<>();
            artists = new ArrayList<>(MainActivity.getArtists().values());
            //constraint is the result from text you want to filter against.

            if(constraint != null) {
                connection.getDBInstance().collection("Artists").whereGreaterThanOrEqualTo("firstName", constraint.toString())
                        .whereLessThan("firstName", constraint.toString() + '\uf8ff')
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        artists = queryDocumentSnapshots.toObjects(Artist.class);
                        clear();
                        addAll(artists);
                        notifyDataSetChanged();
                    }
                });
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) { }
    };
    @NonNull
    @Override
    public Filter getFilter() {
        return myFilter;
    }
}
