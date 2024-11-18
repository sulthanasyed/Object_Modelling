package com.crio.jukebox.entities;

public class Song extends BaseEntity {
    
    private String name;

    private String artist;

    private String albumName;

    private String genre;

    public Song(String name, String artist, String albumName, String genre) {
        this.name = name;
        this.artist = artist;
        this.albumName = albumName;
        this.genre = genre;
    }

    public Song(String id, String name, String artist, String albumName, String genre) {
        this(name, artist, albumName, genre);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    
}
