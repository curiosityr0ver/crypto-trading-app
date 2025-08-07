package com.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trading.entities.VerificationCode;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long>
{

	VerificationCode findByUserId(Long userId);
	
}
