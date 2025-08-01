package com.curiosity.crypto.controller;

import com.curiosity.crypto.config.JwtProvider;
import com.curiosity.crypto.model.TwoFactorAuth;
import com.curiosity.crypto.model.TwoFactorOTP;
import com.curiosity.crypto.model.User;
import com.curiosity.crypto.repository.UserRepository;
import com.curiosity.crypto.respose.AuthResponse;
import com.curiosity.crypto.service.CustomerUserDetailsService;
import com.curiosity.crypto.service.EmailService;
import com.curiosity.crypto.service.TwoFactorOtpService;
import com.curiosity.crypto.utils.otpUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    private TwoFactorOtpService twoFactorOtpService;

    @Autowired
    private EmailService emailService;

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

//        marking user to be authenticated after signup, so that they don't have to login again
        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setStatus(true);
        authResponse.setMessage("registered successfully");

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
//         return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) throws MessagingException {

        String username = user.getEmail(), password = user.getPassword();
        Authentication auth;
        try {
            auth = authenticate(username, password);
        } catch (BadCredentialsException | UsernameNotFoundException e) {
            AuthResponse authResponse = new AuthResponse();
            authResponse.setStatus(false);
            authResponse.setMessage(e.getMessage());

            return new ResponseEntity<>(authResponse, HttpStatus.UNAUTHORIZED);
        }

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);

        User savedUser = userRepository.findByEmail(user.getEmail());

        if (user.getTwoFactorAuth().isEnabled()) {
            AuthResponse authResponse = new AuthResponse();
            authResponse.setJwt("Two Factor Authentication is enabled");
            authResponse.setTwoFactorAuthEnable(true);
            String otp = otpUtils.generateOtp();

            TwoFactorOTP oldTwoFactorOtp = twoFactorOtpService.findByUser(savedUser.getId());

            if (oldTwoFactorOtp != null) {
                twoFactorOtpService.deleteTwoFactorOtp(oldTwoFactorOtp);

            }

            TwoFactorOTP newTwoFactorOtp = twoFactorOtpService.createTwoFactorOtp(savedUser, otp, jwt);

            emailService.sendVerificationOtpEmail(username, otp);
            authResponse.setSession(newTwoFactorOtp.getId());
            return new ResponseEntity<>(authResponse, HttpStatus.ACCEPTED);
        }

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setStatus(true);
        authResponse.setMessage("login successfully");

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }


    private Authentication authenticate(String username, String password) {


        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        if (!password.equals(userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @PostMapping("/")
    public ResponseEntity<AuthResponse> verifyLoginOTP(
            @PathVariable String otp,
            @RequestParam String id) {

        TwoFactorOTP twoFactorOtp = twoFactorOtpService.findById(otp);

        if (twoFactorOtpService.verifyTwoFactorOtp(twoFactorOtp, otp)) {
            AuthResponse authResponse = new AuthResponse();

            authResponse.setStatus(true);
            authResponse.setTwoFactorAuthEnable(true);
            authResponse.setJwt(twoFactorOtp.getJwt());
            authResponse.setMessage("Two Factor Authentication verified successfully");
            return new ResponseEntity<>(authResponse, HttpStatus.OK);

        }
        AuthResponse authResponse = new AuthResponse();
        authResponse.setStatus(false);
        authResponse.setMessage("login failed, invalid otp");
        return new ResponseEntity<>(authResponse, HttpStatus.UNAUTHORIZED);


    }
}