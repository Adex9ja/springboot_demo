package com.example.demo.services;

import com.example.demo.entities.User;
import com.example.demo.exceptions.UserExistsException;
import com.example.demo.exceptions.UserNotFoundException;
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

    public User createUser(User user) throws UserExistsException{
        User existingUser = userRepositories.findByUsername(user.getUsername());
        if(existingUser != null){
            throw new UserExistsException("User already exists in repository");
        }
        return userRepositories.save(user);
    }

    public Optional<User> getUserById(Long id) throws UserNotFoundException {
        Optional<User> user = userRepositories.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException("User not found in user repository");
        }
        return user;
    }
    public User updateUserById(Long id, User user) throws UserNotFoundException{
        Optional<User> optionalUser = userRepositories.findById(id);
        if(!optionalUser.isPresent()){
            throw new UserNotFoundException("User not found in user repository, provide the correct user ID");
        }
        user.setId(id);
        return userRepositories.save(user);
    }

    public void deleteUserById(Long id) throws UserNotFoundException {
        Optional<User> optionalUser = userRepositories.findById(id);
        if(!optionalUser.isPresent()){
            throw new UserNotFoundException("User not found in user repository, provide the correct user ID");
        }

        userRepositories.deleteById(id);
    }

    public User findUserByUsername(String username){
        return userRepositories.findByUsername(username);
    }
}
