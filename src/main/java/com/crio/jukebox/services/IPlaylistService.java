package com.crio.jukebox.services;

import java.util.List;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;

public interface IPlaylistService {

    public Playlist createPlaylist(String ownerId, String playlistName, List<String> songIds);

    public void deletePlaylist(String ownerId, String playlistId);

    public Song playPlaylist(String ownerId, String playlistId);
    
    public Playlist modifyPlaylist(String subCommand, String ownerId, String playlistId, List<String> songIds);

    public Song playSong(String ownerId, String subCommand);
    
}
