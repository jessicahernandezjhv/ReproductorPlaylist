package com.example.reproductor_playlist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseError;
import com.google.firebase.storage.FirebaseStorage;

import org.w3c.dom.Text;

import java.io.IOException;

public class SongDetailView extends AppCompatActivity {
    FirebaseStorage firebaseStorage;
    Bitmap bigCoverBitmap;
    MediaPlayer musicPlayer;
    TextView song, artist;
    ImageView cover;
    ImageButton playBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail_view);
        getIncomingIntent();
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("cover_url") &&
                getIntent().hasExtra("song_name") &&
                getIntent().hasExtra("artist_name") &&
                getIntent().hasExtra("song_url")) {

            String coverURL = getIntent().getStringExtra("cover_url");
            String songName = getIntent().getStringExtra("song_name");
            String artistName = getIntent().getStringExtra("artist_name");
            String songURL = getIntent().getStringExtra("song_url");

            setViewElements(coverURL, songName, artistName);
            loadMusic(songURL);
        }
    }

    private void setViewElements(String coverURL, String songName, String artistName) {
        song = findViewById(R.id.detailSongNameID);
        song.setText(songName);
        artist = findViewById(R.id.detailArtistNameID);
        artist.setText(artistName);
        cover = findViewById(R.id.detailBigCoverID);
        playBtn = findViewById(R.id.playBtnID);

        firebaseStorage = FirebaseStorage.getInstance();
        firebaseStorage.getReferenceFromUrl(coverURL)
                .getBytes(1024 * 1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                bigCoverBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                cover.setImageBitmap(bigCoverBitmap);
            }
        });

        cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (musicPlayer.isPlaying()) {
                    musicPlayer.pause();
                    setFadeAnimation(playBtn);
                    cover.animate().scaleY((float) 1.0).scaleX((float) 1.0).setDuration(1000);

                }
            }
        });

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    musicPlayer.start();
                    cover.animate().scaleY((float) 1.6).scaleX((float) 1.6).setDuration(1000);
                    setFadeOutAnimation(playBtn);
                    //imageView.animate().scaleXBy((float) -1.0).scaleYBy((float) -1.0); // Volver a poner la imagen como estaba
            }
        });
    }

    private void setFadeAnimation(View view){
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000);
        view.startAnimation(anim);
        view.setVisibility(View.VISIBLE);
    }

    private void setFadeOutAnimation(View view){
        AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
        anim.setDuration(1000);
        view.startAnimation(anim);
        view.setVisibility(View.INVISIBLE);
    }

    private void loadMusic(String songURL) {
        musicPlayer = new MediaPlayer();
        musicPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            musicPlayer.setDataSource(songURL);
            musicPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        musicPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        musicPlayer.stop();
    }
}

