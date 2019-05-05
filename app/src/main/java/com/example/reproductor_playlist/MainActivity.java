package com.example.reproductor_playlist;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<SongsData> songsList;
    RecyclerView songsRecycler;
    FirebaseStorage firebaseStorage;
    FirebaseDatabase firebaseDatabase;
    SongsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songsList = new ArrayList<>();
        songsRecycler = findViewById(R.id.myRecyclerViewID);
        songsRecycler.setLayoutManager(new LinearLayoutManager((this)));
        loadList();
        adapter = new SongsAdapter((songsList));
        songsRecycler.setAdapter(adapter);
    }

    private void loadList(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("playlist").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                SongsData song;
                song = dataSnapshot.getValue(SongsData.class);
                songsList.add(song);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
