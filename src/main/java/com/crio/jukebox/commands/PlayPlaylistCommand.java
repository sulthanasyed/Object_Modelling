package com.crio.jukebox.commands;

import java.util.List;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.exceptions.PlaylistNotFoundException;
import com.crio.jukebox.services.IPlaylistService;

public class PlayPlaylistCommand implements ICommand {

    private final IPlaylistService playlistService;

    public PlayPlaylistCommand(IPlaylistService playlistService){
        this.playlistService = playlistService;
    }
    @Override
    public void execute(List<String> tokens) {
        try {
            String ownerId = tokens.get(1);
            String playlistId = tokens.get(2);
            Song playingSong = playlistService.playPlaylist(ownerId, playlistId);
            System.out.println("Current Song Playing");
            System.out.println("Song - " + playingSong.getName());
            System.out.println("Album - " + playingSong.getAlbumName());
            System.out.println("Artists - " + playingSong.getArtist());
        } catch(PlaylistNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
