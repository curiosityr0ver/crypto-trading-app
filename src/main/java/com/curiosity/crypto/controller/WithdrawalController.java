package com.curiosity.crypto.controller;
import com.curiosity.crypto.domain.WALLET_TRANSACTION_TYPE;
import com.curiosity.crypto.model.User;
import com.curiosity.crypto.model.Wallet;
import com.curiosity.crypto.model.WalletTransaction;
import com.curiosity.crypto.model.Withdrawal;
import com.curiosity.crypto.service.UserService;
import com.curiosity.crypto.service.WalletService;
import com.curiosity.crypto.service.WalletTransactionService;
import com.curiosity.crypto.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
public class WithdrawalController {

    @Autowired
    private WithdrawalService withdrawalService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @Autowired
    private WalletTransactionService walletTransactionService;

    @PostMapping("/api/withdrawal/{amount}")
    public ResponseEntity<WalletTransaction> withdrawalRequest(
            @PathVariable Long amount,
            @RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findUserByJwt(jwt);
        Wallet userWallet=walletService.getUserWallet(user);

        Withdrawal withdrawal=withdrawalService.requestWithdrawal(amount,user);
        walletService.addBalance(userWallet, -withdrawal.getAmount());

        WalletTransaction walletTransaction = walletTransactionService.createTransaction(
                userWallet,
                WALLET_TRANSACTION_TYPE.WITHDRAWAL,null,
                "bank account withdrawal",
                withdrawal.getAmount()
        );

        return new ResponseEntity<>(walletTransaction, HttpStatus.OK);
    }

    @PatchMapping("/api/admin/withdrawal/{id}/proceed/{accept}")
    public ResponseEntity<?> proceedWithdrawal(
            @PathVariable Long id,
            @PathVariable boolean accept,
            @RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findUserByJwt(jwt);

        Withdrawal withdrawal=withdrawalService.proceedWithWithdrawal(id,accept);

        Wallet userWallet=walletService.getUserWallet(user);
        if(!accept){
            walletService.addBalance(userWallet, withdrawal.getAmount());
        }

        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    @GetMapping("/api/withdrawal")
    public ResponseEntity<List<Withdrawal>> getWithdrawalHistory(

            @RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findUserByJwt(jwt);

        List<Withdrawal> withdrawal=withdrawalService.getUsersWithdrawalHistory(user);

        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    @GetMapping("/api/admin/withdrawal")
    public ResponseEntity<List<Withdrawal>> getAllWithdrawalRequest(

            @RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findUserByJwt(jwt);

        List<Withdrawal> withdrawal=withdrawalService.getAllWithdrawalRequest();

        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }
}