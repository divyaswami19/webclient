package com.tecsoft.webclient.controller;

import com.tecsoft.webclient.model.User;
import com.tecsoft.webclient.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/webclient-users")
public class WebClientController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id){
        return userService.getUser(id);
    }
    @GetMapping("/")
    public List<User> getUser(){
        return userService.getAllUser();
    }
    @PostMapping("/")
    public User getUser(@RequestBody User user){
        return userService.addUser(user);
    }
}
