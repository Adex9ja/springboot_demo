package com.example.demo.controllers;

import com.example.demo.entities.Order;
import com.example.demo.entities.User;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.;

@RestController
@RequestMapping( value = "/hateoas/users")
@Validated
public class UserHateoasController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public EntityModel<User> getUserById(@PathVariable("id") Long id){
        try {
            Optional<User> userOptional = userService.getUserById(id);
            User user = userOptional.get();
            Long userid = user.getId();
            Link selfLink = WebMvcLinkBuilder.linkTo(this.getClass()).slash(userid).withSelfRel();
            user.add(selfLink);
            EntityModel<User> finalResource = EntityModel.of(user);
            return finalResource;

        }catch (UserNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping
    public CollectionModel<User> getAllUser() throws UserNotFoundException {
        List<User> allUsers = userService.getAllUser();
        for (User user: allUsers) {
            //self link
            Long userid = user.getId();
            Link selfLink = WebMvcLinkBuilder.linkTo(this.getClass()).slash(userid).withSelfRel();
            user.add(selfLink);

            //Relationship with getAllOrder
            CollectionModel<Order> orderCollectionModel = WebMvcLinkBuilder.methodOn(OrderHateoasController.class).getOrders(userid);
            Link ordersLink = WebMvcLinkBuilder.linkTo(orderCollectionModel).withRel("all-orders");
            user.add(ordersLink);
        }

        Link selfLinkGetAllUsers = WebMvcLinkBuilder.linkTo(this.getClass()).withSelfRel();
        CollectionModel<User> finalCollection = CollectionModel.of(allUsers, selfLinkGetAllUsers);
        return finalCollection;
    }

}
