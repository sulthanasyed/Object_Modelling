package com.crio.jukebox.services;

import com.crio.jukebox.entities.Song;
import com.crio.jukebox.repositories.ISongRepository;

public class SongService implements ISongService{

    private ISongRepository songRepository;

    public SongService(ISongRepository songRepository) {
        this.songRepository = songRepository;
    }
    
    @Override
    public void addSongDetails(String no, String songName, String genre, String album, String artist) {
        Song s = new Song(songName, artist, album, genre);
        songRepository.save(s);
    }
    
}
