package com.example.demo.controllers;

import com.example.demo.entities.Order;
import com.example.demo.entities.User;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repositories.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class OrderController {

    @Autowired
    private UserRepositories userRepositories;

    @GetMapping("/{userid}/orders")
    public List<Order> getOrders(@PathVariable Long userid) throws UserNotFoundException{
        Optional<User> userOptional = userRepositories.findById(userid);
        if(!userOptional.isPresent())
            throw new UserNotFoundException("User not fount");
        return userOptional.get().getOrders();
    }
}
