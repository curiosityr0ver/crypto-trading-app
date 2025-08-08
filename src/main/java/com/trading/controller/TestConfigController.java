package com.trading.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestConfigController {

    @Value("${razorpay.api.key}")
    private String razorpayKey;

    @Value("${razorpay.api.secret}")
    private String razorpaySecret;

    @GetMapping("/test-keys")
    public String testKeys() {
        return "Razorpay Key: " + razorpayKey + " | Secret: " + razorpaySecret;
    }
}