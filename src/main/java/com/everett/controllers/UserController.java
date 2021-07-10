package com.everett.controllers;

import com.everett.exceptions.InvalidInputException;
import com.everett.models.User;
import com.everett.repos.UserRepository;
import com.everett.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepo;
    private UserService userService;

    @Autowired
    public UserController(UserRepository userRepo, UserService userService) {
        this.userRepo = userRepo;
        this.userService = userService;
    }

    @GetMapping(value="/getAll", produces = APPLICATION_JSON_VALUE)
    public List<User> getAllUsers(HttpServletRequest req) {
        return userRepo.findAll();
    }

    @PostMapping(value = "/register", consumes= APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity addUser(@RequestBody User user, HttpServletResponse resp) {

        try {
            if(userService.validateUser(user)) {
                return ResponseEntity.ok(userRepo.save(user));
            };
        } catch (InvalidInputException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.internalServerError().body("Something Went Wrong, please try again");
    }
}
