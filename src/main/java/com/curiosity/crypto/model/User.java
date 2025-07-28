package com.curiosity.crypto.model;

import com.curiosity.crypto.domain.TWO_FACTOR_AUTH;
import com.curiosity.crypto.domain.USER_ROLE;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private TWO_FACTOR_AUTH twoFactorAuth =  TWO_FACTOR_AUTH.EMAIL;
    private USER_ROLE role = USER_ROLE.ROLE_CUSTOMER;


}
