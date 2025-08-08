package com.trading.service;

import com.trading.domain.VerificationType;
import com.trading.entities.ForgotPasswordToken;
import com.trading.entities.User;

public interface ForgotPasswordService
{
	ForgotPasswordToken createToken(User user, String id, String otp, 
									VerificationType verificationType , String sendTo);
	
	ForgotPasswordToken findById(String id);
	
	ForgotPasswordToken findByUser(Long userId);
	
	void deleteToken(ForgotPasswordToken token);
	
	
	
	
}
