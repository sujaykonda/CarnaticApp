package com.example.carnaticapp.mongodb;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoClient;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.core.auth.StitchUser;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteFindIterable;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCursor;
import com.mongodb.stitch.core.auth.providers.anonymous.AnonymousCredential;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteUpdateOptions;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteUpdateResult;

import org.bson.Document;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;


public class ConnectToDB {

    public static HashMap<String, Artist> artists = new HashMap<>();

    public static void instantiate() {

        artists.clear();

        final StitchAppClient client =
                Stitch.initializeDefaultAppClient("carnaticapp-fptif");

        final RemoteMongoClient mongoClient =
                client.getServiceClient(RemoteMongoClient.factory, "mongodb-atlas");

        final RemoteMongoCollection<Document> coll =
                mongoClient.getDatabase("test").getCollection("Artists");

        coll.find().forEach(document -> {
            Artist artist = new Artist(document.get("Username").toString(), document.get("Gender").toString(), document.get("Type").toString());
            System.out.println(artist.username);
            artists.put(artist.username, artist);
        });

        System.out.println(ConnectToDB.artists.keySet());
    }
}