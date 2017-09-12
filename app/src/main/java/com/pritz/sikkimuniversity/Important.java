package com.pritz.sikkimuniversity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Important extends AppCompatActivity {
    TextView dat_,info_;
    ImageView immm;
    String dat;
    String  nam;
    String inf;
    String img;
FloatingActionButton s,d;

    DatabaseReference mref = FirebaseDatabase.getInstance().getReference().child("Importantnotifications");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important);


        getSupportActionBar().setTitle("........");
        SharedPreferences sharedPreferences = getSharedPreferences("keyvalue", Context.MODE_PRIVATE);

        dat_=(TextView)findViewById(R.id.dat);
        info_=(TextView)findViewById(R.id.info);
        immm=(ImageView)findViewById(R.id.imm);
        s=(FloatingActionButton)findViewById(R.id.sharee);
        d=(FloatingActionButton)findViewById(R.id.download);
        String key = sharedPreferences.getString("secret_key","");
        mref.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dat= (String) dataSnapshot.child("date").getValue();
                nam=(String)dataSnapshot.child("name").getValue();
                inf=(String)dataSnapshot.child("message").getValue();
                img=(String)dataSnapshot.child("image").getValue();
                Picasso.with(Important.this).load(img).resize(500,600).into(immm);

                getSupportActionBar().setTitle(nam);
                dat_.setText(dat);
                info_.setText(nam+"\n\n"+inf);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(img));
                startActivity(i);
            }
        });
    s.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
       intent.setType("text/plain");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,"From SU-Connect Notice Board\n\n"+dat+"\n"+nam+"\n\n"+inf+"\n\nDownload image\n\n"+img);
        startActivity(Intent.createChooser(intent, "Share via"));
    }
});

    }
}