package com.crio.jukebox.commands;

import java.util.List;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.exceptions.SongNotPartOfPlaylistException;
import com.crio.jukebox.services.IPlaylistService;

public class PlaySongCommand implements ICommand{

    private final IPlaylistService playlistService;

    public PlaySongCommand(IPlaylistService playlistService){
        this.playlistService = playlistService;
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            String ownerId = tokens.get(1);
            String subCommand = tokens.get(2);
            Song playingSong = playlistService.playSong(ownerId, subCommand);
            System.out.println("Current Song Playing");
            System.out.println("Song - " + playingSong.getName());
            System.out.println("Album - " + playingSong.getAlbumName());
            System.out.println("Artists - " + playingSong.getArtist());
        } catch (SongNotPartOfPlaylistException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
