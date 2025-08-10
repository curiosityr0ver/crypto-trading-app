package com.trading.service;

import com.trading.entities.Wallet;
import com.trading.entities.WalletTransation;

import java.util.List;

public class TransactionServiceImpl implements TransactionService {
    @Override
    public List<WalletTransation> getTransactionsByWallet(Wallet wallet) {
        return List.of();
    }
}
