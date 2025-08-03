package com.curiosity.crypto.service;

import com.curiosity.crypto.model.Order;
import com.curiosity.crypto.model.User;
import com.curiosity.crypto.model.Wallet;

import java.math.BigDecimal;

public interface WalletService {
    Wallet getUserWallet(User user);
    Wallet addBalance(Wallet wallet, Long amount);
    Wallet findWalletById(Long id);
    Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount);
    Wallet payOrderPayment(Order order, User user);
}
