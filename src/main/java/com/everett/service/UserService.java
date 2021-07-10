package com.everett.service;

import com.everett.exceptions.InvalidInputException;
import com.everett.models.User;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserService {

    private final Pattern passwordValidation = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");

    public UserService() {};

    public boolean validateUser(User user) {
        if(!checkValidUsername(user.getUsername())) {
            throw new InvalidInputException("Username must be between 8 and 36 letters long.");
        };
        if(!checkValidPassword(user.getPassword())) {
            throw new InvalidInputException(("Password must be between 8 and 24 characters, include at least 1 letter, 1 number, and one special character"));
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

    private boolean isUsernameAvailable(String username) {
        return true;
    }

    private boolean isEmailAvailable(String email) {
        return true;
    }

}
