package com.everett.controllers;

import com.everett.models.User;
import com.everett.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepo;

    @Autowired
    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping(value="/getAll", produces = APPLICATION_JSON_VALUE)
    public List<User> getAllUsers(HttpServletRequest req) {
        return userRepo.findAll();
    }

    @PostMapping(value = "/addUser", consumes= APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public User addUser(@RequestBody User user) {
        return userRepo.save(user);
    }
}
