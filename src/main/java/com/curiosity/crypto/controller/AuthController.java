package com.curiosity.crypto.controller;

import com.curiosity.crypto.config.JwtProvider;
import com.curiosity.crypto.model.User;
import com.curiosity.crypto.repository.UserRepository;
import com.curiosity.crypto.respose.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public ResponseEntity<AuthResponse> register(@RequestBody User user) {
        User newUser = new User();
        newUser.setFullName(user.getFullName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());

        User isEmailExists = userRepository.findByEmail(user.getEmail());
        if (isEmailExists != null) {
//             throw new UsernameNotFoundException("Email already exists");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        User savedUser = userRepository.save(newUser);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getEmail(), user.getPassword()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setStatus(true);
        authResponse.setMessage("registered successfully");

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
//         return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
}
