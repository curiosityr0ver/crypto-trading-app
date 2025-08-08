package com.trading.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.trading.domain.VerificationType;
import com.trading.entities.ForgotPasswordToken;
import com.trading.entities.User;
import com.trading.repository.ForgotPasswordRepository;

@Service
public class ForgotPasswordImpl implements ForgotPasswordService
{
	
	private ForgotPasswordRepository passwordRepository;

	
	
	
	public ForgotPasswordToken createToken(User user, String id, String otp, VerificationType verificationType,
			String sendTo) 
	{
		ForgotPasswordToken token = new ForgotPasswordToken();
		token.setUser(user);
		token.setSendTo(sendTo);
		token.setVerificationType(verificationType);
		token.setOtp(otp);
		token.setId(id);
		
	return	passwordRepository.save(token);
		 
	}

	
	
	public ForgotPasswordToken findById(String id) 
	{
		Optional<ForgotPasswordToken> token = passwordRepository.findById(id);
		
		return token.orElse(null);
	}

	
	
	public ForgotPasswordToken findByUser(Long userId) 
	{
		
		return passwordRepository.findByUserId(userId);
	}

	
	
	public void deleteToken(ForgotPasswordToken token)
	{
		passwordRepository.delete(token);
	}
	
}
