package com.example.reproductor_playlist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    Context mContext;
    Intent intent;

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
    public void onBindViewHolder(@NonNull final ViewHolderSongs viewHolderSongs, final int i) {
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
    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    public class ViewHolderSongs extends RecyclerView.ViewHolder {
        TextView songName, artistName;
        ImageView cover;

        public ViewHolderSongs(@NonNull final View itemView) {
            super(itemView);
            songName = (TextView) itemView.findViewById(R.id.songNameID);
            artistName = (TextView) itemView.findViewById(R.id.artistNameID);
            cover = (ImageView) itemView.findViewById(R.id.cdCoverID);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    intent = new Intent(view.getContext(), SongDetailView.class);
                    intent.putExtra("cover_url", songsList.get(position).getUrlCover());
                    intent.putExtra("song_name", songsList.get(position).getSongName());
                    intent.putExtra("artist_name", songsList.get(position).getArtistName());
                    intent.putExtra("song_url", songsList.get(position).getUrlSong());
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
