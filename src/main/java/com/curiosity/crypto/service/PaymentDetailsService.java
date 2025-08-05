package com.curiosity.crypto.service;

import com.curiosity.crypto.model.PaymentDetails;
import com.curiosity.crypto.model.User;

public interface PaymentDetailsService {
    public PaymentDetails addPaymentDetails(String accountNumber,
                                            String accountHolderName,
                                            String ifsc,
                                            String bankName,
                                            User user
    );

    public PaymentDetails getUsersPaymentDetails(User user);


}