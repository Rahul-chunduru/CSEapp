package com.example.saiganesh.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Main2Activity extends AppCompatActivity {
    private  DatabaseReference mDatabase ;
      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
          Intent  i = getIntent() ;
          String message = i.getStringExtra(EXTRA_MESSAGE);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);


        mDatabase = FirebaseDatabase.getInstance().getReference();

        EditText r = (EditText) findViewById(R.id.E1) ;

        final TextView t = (TextView)findViewById(R.id.textView) ;

       final String b = modify(message) ;
        DatabaseReference R = mDatabase.child("Email").child(b) ;
         ValueEventListener Listen =  new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 String s = dataSnapshot.getValue().toString();
                 t.setText(s);
             }

             @Override
             public void onCancelled(DatabaseError databaseError) {
                 t.setText("unable");
             }
         } ;
        R.addValueEventListener( Listen );





    }
    String modify(String a)
    {
        String z = "" ;
        while( a.charAt(0) != '@')
        {
            z = z + a.charAt(0) ;
            a = a.substring(1) ;
        }
        return z;
    }
}

