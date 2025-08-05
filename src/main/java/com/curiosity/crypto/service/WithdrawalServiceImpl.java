package com.curiosity.crypto.service;

import com.curiosity.crypto.domain.WITHDRAWAL_STATUS;
import com.curiosity.crypto.model.User;
import com.curiosity.crypto.model.Withdrawal;
import com.curiosity.crypto.repository.WithdrawalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WithdrawalServiceImpl implements WithdrawalService {

    @Autowired
    private WithdrawalRepository withdrawalRepository;

    @Override
    public Withdrawal requestWithdrawal(Long amount, User user) {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAmount(amount);
        withdrawal.setStatus(WITHDRAWAL_STATUS.PENDING);
        withdrawal.setDate(LocalDateTime.now());
        withdrawal.setUser(user);
        return withdrawalRepository.save(withdrawal);
    }

    @Override
    public Withdrawal proceedWithWithdrawal(Long withdrawalId, boolean accept) throws Exception {
        Optional<Withdrawal> withdrawalOptional = withdrawalRepository.findById(withdrawalId);

        if (withdrawalOptional.isEmpty()) {
            throw new Exception("Withdrawal Not Found");
        }

        Withdrawal withdrawal = withdrawalOptional.get();


        withdrawal.setDate(LocalDateTime.now());

        if (accept) {
            withdrawal.setStatus(WITHDRAWAL_STATUS.SUCCESS);
        } else {
            withdrawal.setStatus(WITHDRAWAL_STATUS.DECLINE);
        }

        return withdrawalRepository.save(withdrawal);
    }

    @Override
    public List<Withdrawal> getUsersWithdrawalHistory(User user) {
        return withdrawalRepository.findByUserId(user.getId());
    }

    @Override
    public List<Withdrawal> getAllWithdrawalRequest() {
        return withdrawalRepository.findAll();
    }
}
