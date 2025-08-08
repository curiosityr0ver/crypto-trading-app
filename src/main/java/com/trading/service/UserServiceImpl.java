package com.trading.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.config.JwtProvider;
import com.trading.domain.VerificationType;
import com.trading.entities.TwoFactorAuth;
import com.trading.entities.User;
import com.trading.repository.UserRepository;

@Service
//@RestController
public class UserServiceImpl implements UserService
{
	
	@Autowired
	private UserRepository userRepository;

	
	public User findUserByProfileByJwt(String jwt) throws Exception 
	{
		String email = JwtProvider.getEmailFromJwtToken(jwt);
		
		User user = userRepository.findByEmail(email);
		
		if (user == null)
		{
			throw new Exception("User not found");
		}
	
		return user;
	}

	
	public User findUserByEmail(String email) throws Exception
	{
		User user = userRepository.findByEmail(email);
		
		if (user == null)
		{
			throw new Exception("User not found");
		}
	
		return user;
	}
	
	

	public User findUserById(Long userId) throws Exception 
	{
		Optional<User> user = userRepository.findById(userId);
		
		if (user.isEmpty())
		{
			throw new Exception("User not found");
		}
		
		return user.get();
	}
	

	

	public User updatePassword(User user, String newPassword)
	{
		user.setPassword(newPassword);
		
		return userRepository.save(user);
	}


	public User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo, User user) 
	{
		TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
		twoFactorAuth.setEnable(true);
		twoFactorAuth.setSendTo(verificationType);
		
		user.setTwoFactorAuth(twoFactorAuth);
		
		return userRepository.save(user);
	}

}
