package com.curiosity.crypto.service;

import com.curiosity.crypto.domain.WALLET_TRANSACTION_TYPE;
import com.curiosity.crypto.model.Wallet;
import com.curiosity.crypto.model.WalletTransaction;

import java.util.List;

public interface WalletTransactionService {

    WalletTransaction createTransaction(Wallet wallet,
                                               WALLET_TRANSACTION_TYPE type,
                                               String transferId,
                                               String purpose,
                                               Long amount
    );

    List<WalletTransaction> getTransactions(Wallet wallet, WALLET_TRANSACTION_TYPE type);

}
