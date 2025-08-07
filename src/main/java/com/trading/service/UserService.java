package com.trading.service;

import com.trading.domain.VerificationType;
import com.trading.entities.User;

public interface UserService 
{
	User findUserByProfileByJwt(String jwt) throws Exception;
	
	User findUserByEmail(String email) throws Exception;
	
	User findUserById(Long userId) throws Exception;
	
	User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo, User user);
	
	User updatePassword(User user, String newPassword);
}

