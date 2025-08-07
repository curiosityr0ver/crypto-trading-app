package com.trading.request;

import com.trading.domain.VerificationType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ForgotPasswordTokenRequest 
{
	private String sendTo;
	
	private VerificationType verificationType;
	
	

}
