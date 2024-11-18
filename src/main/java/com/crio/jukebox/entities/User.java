package com.crio.jukebox.entities;

public class User extends BaseEntity{
    private String name;
    // private List<Playlist> playlists;
    
    public User(String name){
     this.name = name;
    }

    public User(String id, String name) {
        this(name);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
}
