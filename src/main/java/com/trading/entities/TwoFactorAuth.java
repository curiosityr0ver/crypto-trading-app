package com.trading.entities;

import com.trading.domain.VerificationType;

import lombok.Data;

@Data
public class TwoFactorAuth 
{
	private boolean isEnable = false;
	
	private VerificationType sendTo;
}
