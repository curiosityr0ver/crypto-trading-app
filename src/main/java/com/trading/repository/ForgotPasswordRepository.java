package com.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trading.entities.ForgotPasswordToken;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken, String>
{

	ForgotPasswordToken findByUserId(Long userId);
	
	
}
