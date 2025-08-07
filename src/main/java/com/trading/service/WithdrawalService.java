package com.trading.service;

import java.util.List;

import com.trading.entities.User;
import com.trading.entities.Withdrawal;

public interface WithdrawalService 
{
	Withdrawal requestWithdrawal(Long amount, User user);
	
	Withdrawal procedWithdrawal(Long withdrawalId, boolean accept) throws Exception;
	
	List<Withdrawal> getUsersWithdrawalHistory(User user);
	
	List<Withdrawal> getAllWithdrawalRequest();
	
	

}
