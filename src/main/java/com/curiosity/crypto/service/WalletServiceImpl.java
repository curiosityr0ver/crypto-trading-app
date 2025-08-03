package com.curiosity.crypto.service;

import com.curiosity.crypto.model.Order;
import com.curiosity.crypto.model.User;
import com.curiosity.crypto.model.Wallet;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {
    @Override
    public Wallet getUserWallet(User user) {
        return null;
    }

    @Override
    public Wallet addBalance(Wallet wallet, Long amount) {
        return null;
    }

    @Override
    public Wallet findWalletById(Long id) {
        return null;
    }

    @Override
    public Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) {
        return null;
    }

    @Override
    public Wallet payOrderPayment(Order order, User user) {
        return null;
    }
}
