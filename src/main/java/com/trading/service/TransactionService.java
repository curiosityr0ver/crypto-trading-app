package com.trading.service;

import com.trading.entities.Wallet;
import com.trading.entities.WalletTransation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {

    List<WalletTransation> getTransactionsByWallet(Wallet wallet);
}
