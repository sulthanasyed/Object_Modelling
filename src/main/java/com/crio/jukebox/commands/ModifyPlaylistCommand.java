package com.crio.jukebox.commands;

import java.util.ArrayList;
import java.util.List;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.exceptions.PlaylistNotFoundException;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.exceptions.SongNotPartOfPlaylistException;
import com.crio.jukebox.services.IPlaylistService;

public class ModifyPlaylistCommand implements ICommand{

    private final IPlaylistService playlistService;

    public ModifyPlaylistCommand(IPlaylistService playlistService){
        this.playlistService = playlistService;
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            String subCommand = tokens.get(1);
            String ownerId = tokens.get(2);
            String playlistId = tokens.get(3);
            List<String> songIds = new ArrayList<>();
            for (int i = 4; i < tokens.size(); i++) {
                songIds.add(tokens.get(i));
            }
            Playlist modifiedPlaylist =
                    playlistService.modifyPlaylist(subCommand, ownerId, playlistId, songIds);
            System.out.println("Playlist ID - " + modifiedPlaylist.getId());
            System.out.println("Playlist Name - " + modifiedPlaylist.getPlaylistName());
            System.out.println("Song IDs - " + String.join(" ", modifiedPlaylist.getSongIdsList()));
        } catch (PlaylistNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (SongNotPartOfPlaylistException ex) {
            System.out.println(ex.getMessage());
        } catch (SongNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
