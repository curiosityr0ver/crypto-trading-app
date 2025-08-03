package com.curiosity.crypto.service;

import com.curiosity.crypto.model.Order;
import com.curiosity.crypto.model.User;
import com.curiosity.crypto.model.Wallet;
import com.curiosity.crypto.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public Wallet getUserWallet(User user) {
        Wallet wallet = walletRepository.findByUserId(user.getId());
        if(wallet == null){
            wallet = new Wallet();
            wallet.setUser(user);
            walletRepository.save(wallet);
        }
        return wallet;
    }

    @Override
    public Wallet addBalance(Wallet wallet, Long amount) {
        BigDecimal balance = wallet.getBalance();
        BigDecimal newBalance = balance.add(BigDecimal.valueOf(amount));
        wallet.setBalance(newBalance);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findWalletById(Long id) throws Exception {
        Optional<Wallet> wallet = walletRepository.findById(id);

        if(wallet.isPresent()){
            return wallet.get();
        }
        throw new Exception("Wallet Not Found !");
    }

    @Override
    public Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception {
        Wallet senderWallet = getUserWallet(sender);

        if(senderWallet.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0){
            throw new Exception("Insufficient Balance...");
        }
        BigDecimal newBalance = senderWallet.getBalance().subtract(BigDecimal.valueOf(amount));
        senderWallet.setBalance(newBalance);
        walletRepository.save(senderWallet);

        BigDecimal receiverBalance = receiverWallet.getBalance();
        receiverWallet.setBalance(receiverBalance.subtract(BigDecimal.valueOf(amount)));
        walletRepository.save(receiverWallet);
        return senderWallet;
    }

    @Override
    public Wallet payOrderPayment(Order order, User user) {
        Wallet wallet = getUserWallet(user);

//        if(order.getOrder())
    }
}
