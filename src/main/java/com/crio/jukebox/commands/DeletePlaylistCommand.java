package com.crio.jukebox.commands;

import java.util.List;
import com.crio.jukebox.exceptions.PlaylistNotFoundException;
import com.crio.jukebox.services.IPlaylistService;

public class DeletePlaylistCommand implements ICommand {

    private final IPlaylistService  playlistService;

    public DeletePlaylistCommand(IPlaylistService playlistService){
        this.playlistService = playlistService;
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            String ownerId = tokens.get(1);
            String playlistId = tokens.get(2);
            playlistService.deletePlaylist(ownerId, playlistId);
            System.out.println("Delete Successful");
        } catch(PlaylistNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
