package com.example.reproductor_playlist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.ViewHolderSongs> {
    ArrayList<SongsData> songsList;
    FirebaseStorage firebaseStorage;
    Bitmap coverBitmap;

    public SongsAdapter(ArrayList<SongsData> songsList) {
        this.songsList = songsList;
    }

    @NonNull
    @Override
    public ViewHolderSongs onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item, null, false);
        return new ViewHolderSongs(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderSongs viewHolderSongs, int i) {
        viewHolderSongs.songName.setText(songsList.get(i).getSongName());
        viewHolderSongs.artistName.setText(songsList.get(i).getArtistName());

        firebaseStorage = FirebaseStorage.getInstance();

        firebaseStorage.getReferenceFromUrl(songsList.get(i).getUrlCover())
                .getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                coverBitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                viewHolderSongs.cover.setImageBitmap(coverBitmap);
            }
        });

        //viewHolderSongs.cover.setImageBitmap(getBitmapFromURL(songsList.get(i).getUrlCover()));
    }

    /*public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }*/

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    public class ViewHolderSongs extends RecyclerView.ViewHolder {
        TextView songName, artistName;
        ImageView cover;

        public ViewHolderSongs(@NonNull View itemView) {
            super(itemView);
            songName = (TextView) itemView.findViewById(R.id.songNameID);
            artistName = (TextView) itemView.findViewById(R.id.artistNameID);
            cover = (ImageView) itemView.findViewById(R.id.cdCoverID);
        }
    }
}
