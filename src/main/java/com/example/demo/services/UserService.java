package com.example.demo.services;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepositories userRepositories;

    public List<User> getAllUser(){
        return userRepositories.findAll();
    }

    public User createUser(User user) {
        return userRepositories.save(user);
    }

    public Optional<User> getUserById(Long id){
        return userRepositories.findById(id);
    }
    public User updateUserById(Long id, User user){
        user.setId(id);
        return userRepositories.save(user);
    }
}
