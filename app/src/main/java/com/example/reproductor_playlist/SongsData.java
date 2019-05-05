package com.example.reproductor_playlist;

public class SongsData {
    private String songName;
    private String artistName;
    private String urlCover;
    private String urlSong;

    public SongsData() {
    }

    public SongsData(String songName, String artistName, String urlCover, String urlSong) {
        this.songName = songName;
        this.artistName = artistName;
        this.urlCover = urlCover;
        this.urlSong = urlSong;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getUrlCover() {
        return urlCover;
    }

    public void setUrlCover(String urlCover) {
        this.urlCover = urlCover;
    }

    public String getUrlSong() {
        return urlSong;
    }

    public void setUrlSong(String urlSong) {
        this.urlSong = urlSong;
    }
}
