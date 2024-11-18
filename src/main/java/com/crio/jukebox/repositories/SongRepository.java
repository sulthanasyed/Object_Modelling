package com.crio.jukebox.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.crio.jukebox.entities.Song;

public class SongRepository implements ISongRepository{

    private final Map<String, Song> songsMap;
    private Integer autoIncrement = 0;

    public SongRepository() {
        songsMap = new HashMap<>();
    }

    @Override
    public Song save(Song entity) {
        if(entity.getId() == null){
            autoIncrement++;
            Song song = new Song(Integer.toString(autoIncrement),entity.getName(), entity.getArtist(), entity.getAlbumName(), entity.getGenre());
            songsMap.put(song.getId(),song);
            return song;
        }
        songsMap.put(entity.getId(),entity);
        return entity;
    }

    @Override
    public List<Song> findAll() {
        return new ArrayList<>(this.songsMap.values());
    }

    @Override
    public Optional<Song> findById(String id) {
        return Optional.ofNullable(songsMap.get(id));
    }

    @Override
    public boolean existsById(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void delete(Song entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteById(String id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public long count() {
        // TODO Auto-generated method stub
        return 0;
    }
     
}
