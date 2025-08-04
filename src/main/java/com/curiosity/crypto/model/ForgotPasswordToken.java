package com.curiosity.crypto.model;

import com.curiosity.crypto.domain.VERIFICATION_TYPE;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

@Entity
@Data
public class ForgotPasswordToken {

    @Id
    private String id;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    private String otp;

    private VERIFICATION_TYPE verificationType;

    private String sendTo;

}
