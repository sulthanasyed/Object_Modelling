package com.crio.jukebox.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.crio.jukebox.entities.Playlist;

public class PlaylistRepository implements IPlaylistRepository {

    private final Map<String, Playlist> playlistMap;
    private Integer autoIncrement = 0;

    public PlaylistRepository() {
        playlistMap = new HashMap<>();
    }

    @Override
    public Playlist save(Playlist entity) {
        if(entity.getId() == null){
            autoIncrement++;
            Playlist playlist = new Playlist(Integer.toString(autoIncrement), entity.getOwnerId(), entity.getPlaylistName(), entity.getSongIdsList());
            playlistMap.put(playlist.getId(),playlist);
            return playlist;
        }
        playlistMap.put(entity.getId(),entity);
        return entity;
    }

    @Override
    public List<Playlist> findAll() {
        return new ArrayList<>(this.playlistMap.values());
    }

    @Override
    public Optional<Playlist> findById(String id) {
        return Optional.ofNullable(playlistMap.get(id));
    }

    @Override
    public boolean existsById(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void delete(Playlist entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteById(String id) {
        this.playlistMap.remove(id);
        
    }

    @Override
    public long count() {
        // TODO Auto-generated method stub
        return 0;
    }

    
}
