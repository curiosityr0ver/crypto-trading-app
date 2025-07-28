package com.curiosity.crypto.controller;

import com.curiosity.crypto.model.User;
import com.curiosity.crypto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/test")
    public String home() {
        return "Hello World!";
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody User user) {
         User newUser = new User();
         newUser.setFullName(user.getFullName());
         newUser.setEmail(user.getEmail());
         newUser.setPassword(user.getPassword());

         User savedUser = userRepository.save(newUser);
         return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
}
