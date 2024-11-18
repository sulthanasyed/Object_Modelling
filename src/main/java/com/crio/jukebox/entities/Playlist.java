package com.crio.jukebox.entities;

import java.util.List;

public class Playlist extends BaseEntity{
    private String ownerId;
    private String playlistName;
    private List<String> songIdsList;
    
    public Playlist(String ownerId, String playlistName, List<String> songIdsList) {
        this.ownerId = ownerId;
        this.playlistName = playlistName;
        this.songIdsList = songIdsList;
    }

    public Playlist(String id, String ownerId, String playlistName, List<String> songIdsList) {
        this(ownerId, playlistName, songIdsList);
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public List<String> getSongIdsList() {
        return songIdsList;
    }

    public void setSongIdsList(List<String> songIdsList) {
        this.songIdsList = songIdsList;
    }

    
}
