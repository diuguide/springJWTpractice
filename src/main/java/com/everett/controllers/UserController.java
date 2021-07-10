package com.everett.controllers;

import com.everett.exceptions.EmailTakenException;
import com.everett.exceptions.InvalidInputException;
import com.everett.exceptions.UsernameNotAvailableException;
import com.everett.models.User;
import com.everett.repos.UserRepository;
import com.everett.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping(value="/getAll", produces = APPLICATION_JSON_VALUE)
//    public List<User> getAllUsers(HttpServletRequest req) {
//        return userRepo.findAll();
//    }

    @PostMapping(value = "/register", consumes= APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity addUser(@RequestBody User user, HttpServletResponse resp) {

        try {
            return ResponseEntity.ok(userService.register(user));
        } catch (InvalidInputException | UsernameNotAvailableException | EmailTakenException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
