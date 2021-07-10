package com.everett.service;

import com.everett.exceptions.EmailTakenException;
import com.everett.exceptions.InvalidInputException;
import com.everett.exceptions.UsernameNotAvailableException;
import com.everett.models.User;
import com.everett.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class UserService {

    private final Pattern passwordValidation = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
    private final Pattern emailPattern = Pattern.compile("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    private UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    };

    public User register(User user) {
        if(validateUser(user)) {
            return userRepo.save(user);
        }
        return null;
    }

    public boolean validateUser(User user) {
        if(!checkValidUsername(user.getUsername())) {
            throw new InvalidInputException("Username must be between 8 and 36 letters long.");
        };
        if(!checkValidPassword(user.getPassword())) {
            throw new InvalidInputException(("Password must be between 8 and 24 characters, include at least 1 letter, 1 number, and one special character"));
        }
        if(!checkValidEmail(user.getEmail())) {
            throw new InvalidInputException("Please enter a valid email address.");
        }
        if(!isUsernameAvailable(user.getUsername())) {
            throw new UsernameNotAvailableException("Username is not available!");
        }
        if(!isEmailAvailable(user.getEmail())) {
            throw new EmailTakenException("Email is not available!");
        }
        return true;

    }

    private boolean checkValidUsername(String username) {
        if(username == null || username.length() < 8 || username.length() > 36) {
            return false;
        }
        return true;
    }

    private boolean checkValidPassword(String password) {
        if(password == null || password.length() < 8 || password.length() > 24) {
            return false;
        }
        return passwordValidation.matcher(password).matches();
    }

    private boolean checkValidEmail(String email) {
        return emailPattern.matcher(email).matches();
    }

    private boolean isUsernameAvailable(String username) {
        Optional<User> user = userRepo.findUserByUsername(username);
        if(user.isPresent()) {
            return false;
        }
        return true;
    }

    private boolean isEmailAvailable(String email) {
        Optional<User> user = userRepo.findUserByEmail(email);
        if(user.isPresent()) {
            return false;
        }
        return true;
    }

}
