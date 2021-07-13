package com.example.demo.controllers;

import com.example.demo.entities.Order;
import com.example.demo.entities.User;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class OrderController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/{userid}/orders")
    public List<Order> getOrders(@PathVariable Long userid) throws UserNotFoundException{
        Optional<User> userOptional = userRepository.findById(userid);
        if(!userOptional.isPresent())
            throw new UserNotFoundException("User not found");
        return userOptional.get().getOrders();
    }

    @PostMapping("/{userid}/orders")
    public Order createOrder(@PathVariable Long userid, @RequestBody Order order) throws UserNotFoundException{
        Optional<User> optionalUser = userRepository.findById(userid);
        if(!optionalUser.isPresent())
            throw new UserNotFoundException("User not found");

        User user = optionalUser.get();
        order.setUser(user);
        return orderRepository.save(order);

    }
}
