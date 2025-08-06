package com.curiosity.crypto.service;

import com.curiosity.crypto.domain.PAYMENT_METHOD;
import com.curiosity.crypto.model.PaymentOrder;
import com.curiosity.crypto.model.User;
import com.curiosity.crypto.respose.PaymentResponse;

public interface PaymentService {

    PaymentOrder createOrder(User user, Long amount, PAYMENT_METHOD paymentMethod);

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    Boolean ProccedPaymentOrder (PaymentOrder paymentOrder,
                                 String paymentId) throws RazorpayException;

    PaymentResponse createRazorpayPaymentLink(User user,
                                              Long Amount,
                                              Long orderId) throws RazorpayException;

    PaymentResponse createStripePaymentLink(User user, Long Amount,
                                            Long orderId) throws StripeException;
}