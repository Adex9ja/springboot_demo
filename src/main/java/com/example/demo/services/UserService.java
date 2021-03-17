package com.example.demo.services;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepositories userRepositories;

    public List<User> getAllUser(){
        return userRepositories.findAll();
    }
}
