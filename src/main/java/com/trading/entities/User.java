package com.trading.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trading.domain.USER_ROLE;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.processing.Pattern;

@Entity
@Data
public class User 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String fullName;

	private String email;
	
	
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)    // password will be only writable when we fetch by api it will not display
	@NotBlank(message = "Password is required")
	@Size(min = 8, message = "Password must be at least 8 characters long")
	private String password;
	
	private USER_ROLE roles = USER_ROLE.ROLE_CUSTOMER;
	
	
	@Embedded
	private TwoFactorAuth twoFactorAuth =  new TwoFactorAuth();
}
