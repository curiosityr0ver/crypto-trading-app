package com.curiosity.crypto.service;

import com.curiosity.crypto.model.User;
import com.curiosity.crypto.model.Withdrawal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WithdrawalService {

    Withdrawal requestWithdrawal(Long amount, User user);

    Withdrawal proceedWithWithdrawal(Long withdrawalId, boolean accept) throws Exception;

    List<Withdrawal> getUsersWithdrawalHistory(User user);

    List<Withdrawal> getAllWithdrawalRequest();
}
