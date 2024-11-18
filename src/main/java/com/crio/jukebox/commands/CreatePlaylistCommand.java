package com.crio.jukebox.commands;

import java.util.ArrayList;
import java.util.List;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.services.IPlaylistService;

public class CreatePlaylistCommand implements ICommand{
    
    private final IPlaylistService playlistService;

    public CreatePlaylistCommand(IPlaylistService playlistService){
        this.playlistService = playlistService;
    }

    @Override
    public void execute(List<String> tokens) {
        try {
        String ownerId = tokens.get(1);
        String playlistName = tokens.get(2);
        List<String> songIds = new ArrayList<>();
        for(int i=3; i < tokens.size(); i++) {
            songIds.add(tokens.get(i));
        }
        Playlist createdPlaylist = playlistService.createPlaylist(ownerId, playlistName, songIds);
        System.out.println("Playlist ID - " + createdPlaylist.getId());
    } catch (SongNotFoundException ex) {
        System.out.println(ex.getMessage());
    }
    }
    
}
