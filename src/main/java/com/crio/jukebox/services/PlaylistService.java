package com.crio.jukebox.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.exceptions.PlaylistNotFoundException;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.exceptions.SongNotPartOfPlaylistException;
import com.crio.jukebox.repositories.IPlaylistRepository;
import com.crio.jukebox.repositories.ISongRepository;

public class PlaylistService implements IPlaylistService{

    private IPlaylistRepository playlistRepository;
    private ISongRepository songRepository;
    private static String currentPlaylistId;
    private static String currentSongId;

    public PlaylistService(IPlaylistRepository playlistRepository, ISongRepository songRepository) {
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
    }
    
    @Override
    public Playlist createPlaylist(String ownerId, String playlistName, List<String> songIds) {
        List<String> allSongIds = songRepository.findAll().stream().map(s->s.getId()).collect(Collectors.toList());
        if(!allSongIds.containsAll(songIds)) {
            throw new SongNotFoundException("Some Requested Songs Not Available. Please try again.");
        }
        Playlist playlist = new Playlist(ownerId, playlistName, songIds);
        return playlistRepository.save(playlist);
    }

    @Override
    public void deletePlaylist(String ownerId, String playlistId) {
        Optional<Playlist> playlist = playlistRepository.findById(playlistId);
        if(playlist.isEmpty()) {
            throw new PlaylistNotFoundException("Playlist Not Found");
        }
        playlistRepository.deleteById(playlistId);
    }

    @Override
    public Song playPlaylist(String ownerId, String playlistId) {
        Optional<Playlist> playlist = playlistRepository.findById(playlistId);
        if(playlist.isEmpty()){
            throw new PlaylistNotFoundException("Playlist Not Found");
        } else if(playlist.get().getSongIdsList() != null && playlist.get().getSongIdsList().isEmpty()) {
            throw new PlaylistNotFoundException("Playlist is empty.");
        }
        else{
            List<String> songIdsList = playlist.get().getSongIdsList();
            String firstSongId = songIdsList.get(0);
            Optional<Song> firstSong = songRepository.findById(firstSongId);
            currentPlaylistId = playlistId;
            currentSongId = firstSongId;
            return firstSong.get();
        }
    }

    @Override
    public Playlist modifyPlaylist(String subCommand, String ownerId, String playlistId,
            List<String> songIds) {
        Optional<Playlist> playlist = playlistRepository.findById(playlistId);
        if(playlist.isEmpty()){
            throw new PlaylistNotFoundException("Playlist Not Found");
        }
        List<String> songIdsList = playlist.get().getSongIdsList();
        if(subCommand.equalsIgnoreCase("ADD-SONG")){
            List<String> allSongIds = songRepository.findAll().stream().map(s->s.getId()).collect(Collectors.toList());
            if(!allSongIds.containsAll(songIds)) {
                throw new SongNotFoundException("Some Requested Songs Not Available. Please try again.");
            }
            for(String eachSong : songIds){
                songIdsList.add(eachSong);
            }
        }
        else{
            if(!songIdsList.containsAll(songIds)) {
                throw new SongNotPartOfPlaylistException("Some Requested Songs for Deletion are not present in the playlist. Please try again.");
            }
            for(String eachSong: songIds){
                songIdsList.remove(eachSong);
            }
        }
        return playlist.get();
    }
    
    @Override
    public Song playSong(String ownerId, String subCommand) {
        Optional<Playlist> currentPlaylist = playlistRepository.findById(currentPlaylistId);
        if(currentPlaylist.isEmpty()) {
            throw new PlaylistNotFoundException("Playlist Not Found");
        }
        List<String> songIdsList = currentPlaylist.get().getSongIdsList();
        String selectedSongId = currentSongId;
        if(subCommand.equalsIgnoreCase("NEXT")){
            for(int i=0; i<songIdsList.size(); i++) {
                if(currentSongId.equalsIgnoreCase(songIdsList.get(i))) {
                    if(i == songIdsList.size()-1) {
                        selectedSongId = songIdsList.get(0);
                        break;
                    }
                    selectedSongId = songIdsList.get(i+1);
                    break;
                }
            }
        }
        else if(subCommand.equalsIgnoreCase("BACK")){
            for(int i=0; i<songIdsList.size(); i++) {
                if(currentSongId.equalsIgnoreCase(songIdsList.get(i))) {
                    if(i == 0) {
                        selectedSongId = songIdsList.get(songIdsList.size()-1);
                        break;
                    }
                    selectedSongId = songIdsList.get(i-1);
                    break;
                }
            }
        }
        else{
            if(songIdsList.contains(subCommand)) {
                selectedSongId = subCommand;
            } else {
                throw new SongNotPartOfPlaylistException("Given song id is not a part of the active playlist");
            }
        }
        currentSongId = selectedSongId;
        return songRepository.findById(selectedSongId).get();
    }
}
