package com.curiosity.crypto.model;

import com.curiosity.crypto.domain.VERIFICATION_TYPE;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class VerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String otp;

    @OneToOne()
    private User user;

    private String email;

    private String mobile;

    private VERIFICATION_TYPE verificationType;
}
