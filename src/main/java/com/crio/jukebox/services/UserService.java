package com.crio.jukebox.services;

import com.crio.jukebox.entities.User;
import com.crio.jukebox.repositories.IUserRepository;

public class UserService implements IUserService{

    private IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public User createUser(String username) {
        User userToCreate = new User(username);
        return userRepository.save(userToCreate);
    }
    
}

