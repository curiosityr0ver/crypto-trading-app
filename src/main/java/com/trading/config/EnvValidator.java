package com.trading.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvValidator {

    @Value("${DB_URL:}")
    private String dbUrl;

    @Value("${DB_USERNAME:}")
    private String dbUsername;

    @Value("${DB_PASSWORD:}")
    private String dbPassword;

    @Value("${RAZORPAY_API_SECRET:}")
    private String razorpaySecret;

    @Value("${RAZORPAY_API_KEY:}")
    private String razorpayKey;

    @Value("${STRIPE_API_KEY:}")
    private String stripeKey;

    @PostConstruct
    public void validateEnvVars() {
        checkVar("DB_URL", dbUrl);
        checkVar("DB_USERNAME", dbUsername);
        checkVar("DB_PASSWORD", dbPassword);
        checkVar("RAZORPAY_API_SECRET", razorpaySecret);
        checkVar("RAZORPAY_API_KEY", razorpayKey);
        checkVar("STRIPE_API_KEY", stripeKey);
    }

    private void checkVar(String name, String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required environment variable: " + name);
        }
    }
}