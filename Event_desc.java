package com.example.mycseapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Event_desc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_desc);
        Intent i = getIntent() ;
        final String id = i.getStringExtra(EXTRA_MESSAGE) ;
        TextView t = findViewById(R.id.des) ;
        TextView t2 = findViewById(R.id.imported) ;
        t2.setText(Events.title);
        t.setText(Events.des);
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Events") ;
        try{
        StorageReference dwn = FirebaseStorage.getInstance().getReferenceFromUrl("gs://mycseapp.appspot.com/Events" ).child(id);
        final File localFile = File.createTempFile("images", "jpg");
        dwn.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(), "Downloaded", Toast.LENGTH_LONG).show();

                        final Bitmap myBitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        mDatabase.child(id).child("text").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                               String des = dataSnapshot.getValue().toString() ;
                                ImageView show = findViewById(R.id.Show) ;
                                show.setImageBitmap(myBitmap);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        }) ;



                        // Successfully downloaded data to local file
                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), "Download Failed", Toast.LENGTH_LONG).show();


                // Handle failed download
                // ...
            }
        });
    } catch (IOException e) {
        e.printStackTrace();
    }

    }
}
