package com.example.carnaticapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carnaticapp.firebasefirstore.ConnectToDB;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;

import java.util.List;

public class NewProfileActivity extends AppCompatActivity {
    private String userId;
    final ConnectToDB connection = new ConnectToDB();
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        setContentView(R.layout.activity_new_profile);
    }

    public void SaveProfile(View view) {
        final EditText firstNameField = (EditText) findViewById(R.id.EditFirstName);
        final String firstName = firstNameField.getText().toString();

        final EditText lastNameField = (EditText) findViewById(R.id.EditLastName);
        final String lastName = lastNameField.getText().toString();

        final EditText categoryField = (EditText) findViewById(R.id.EditCategory);
        final String category = categoryField.getText().toString();

        final EditText contactField = (EditText) findViewById(R.id.EditContact);
        String contact = contactField.getText().toString();

        final EditText schoolField = (EditText) findViewById(R.id.EditSchoolName);
        final String school = schoolField.getText().toString();

        final EditText guruField = (EditText) findViewById(R.id.EditTeacherName);
        final String guru = guruField.getText().toString();
        connection.getDBInstance().collection("Artists").whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                        for(int i = 0; i < documents.size(); i++){
                            documents.get(i).getReference().update("firstName", firstName, "lastName", lastName, "category", category, "schoolName", school, "teacherName", guru);
                        }
                    }
                });
    }


}

