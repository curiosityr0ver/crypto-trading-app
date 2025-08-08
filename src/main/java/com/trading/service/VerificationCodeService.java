package com.trading.service;

import com.trading.domain.VerificationType;
import com.trading.entities.User;
import com.trading.entities.VerificationCode;

public interface VerificationCodeService 
{
	VerificationCode sendVerificationCode(User user, VerificationType verificationType);
	
	VerificationCode getVerificationCodeById(Long id) throws Exception;
	
	VerificationCode getVerificationCodeByUser(Long userId);
	
	void deleteVerificationCodeById(VerificationCode code);
	
	

}
