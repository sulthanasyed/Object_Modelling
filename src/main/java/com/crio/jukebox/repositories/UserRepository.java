package com.crio.jukebox.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.crio.jukebox.entities.User;

public class UserRepository implements IUserRepository{

    private final Map<String, User> usersMap;
    private Integer autoIncrement = 0;

    public UserRepository() {
        usersMap = new HashMap<>();
    }

    @Override
    public User save(User entity) {
        if(entity.getId() == null){
            autoIncrement++;
            User user = new User(Integer.toString(autoIncrement),entity.getName());
            usersMap.put(user.getId(),user);
            return user;
        }
        usersMap.put(entity.getId(),entity);
        return entity;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(this.usersMap.values());
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(usersMap.get(id));
    }

    @Override
    public boolean existsById(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void delete(User entity) {
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
