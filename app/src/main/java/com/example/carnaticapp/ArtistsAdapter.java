package com.example.carnaticapp;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.carnaticapp.firebasefirstore.Artist;

import java.util.List;

public class ArtistsAdapter extends ArrayAdapter<Artist> {


        public ArtistsAdapter(Context context, List<Artist> object){
            super(context,0, object);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
        /*    if(convertView == null){
                convertView =  ((Activity)getContext()).getLayoutInflater().inflate(R.layout.content_artist,parent,false);
            }

            TextView nameView = (TextView) convertView.findViewById(R.id.artist_name);
            TextView genderView = (TextView) convertView.findViewById(R.id.artist_gender);
            TextView typeView = (TextView) convertView.findViewById(R.id.artist_type);


            Artist artist = getItem(position);

            nameView.setText(artist.getUsername());
            genderView.setText(artist.getGender());
            typeView.setText(artist.getType());
        */
            if(convertView == null){
                convertView =  ((Activity)getContext()).getLayoutInflater().inflate(R.layout.content_artist,parent,false);
            }
            final TableLayout tableLayout = (TableLayout)convertView.findViewById(R.id.table_layout_table);
            TableRow tableRow = new TableRow(getContext());
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
            tableRow.setLayoutParams(layoutParams);
            Artist artist = getItem(position);
            // Add a TextView in the first column.
            TextView textView0 = new TextView(getContext());
            textView0.setText(artist.getUsername());
            tableRow.addView(textView0, 0);

            TextView textView1 = new TextView(getContext());
            textView1.setText(artist.getType());
            tableRow.addView(textView1, 1);
            TextView textView2 = new TextView(getContext());
            textView2.setText(artist.getGender());
            tableRow.addView(textView2, 2);

            tableLayout.addView(tableRow);

            return convertView;


    }
}
