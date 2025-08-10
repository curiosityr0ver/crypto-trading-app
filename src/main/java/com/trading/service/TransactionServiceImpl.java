package com.trading.service;

import com.trading.entities.Wallet;
import com.trading.entities.WalletTransation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Override
    public List<WalletTransation> getTransactionsByWallet(Wallet wallet) {
        return List.of();
    }
}
