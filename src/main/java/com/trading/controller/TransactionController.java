package com.trading.controller;

import com.trading.entities.User;
import com.trading.entities.Wallet;
import com.trading.entities.WalletTransation;
import com.trading.service.TransactionService;
import com.trading.service.UserService;
import com.trading.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    public ResponseEntity<List<WalletTransation>> getUserWallet(
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByProfileByJwt(jwt);

        Wallet wallet = walletService.getUserWallet(user);
        List<WalletTransation> transactionList = transactionService.getTransactionsByWallet(wallet);


        return new ResponseEntity<>(transactionList, HttpStatus.ACCEPTED);
    }

}
