package com.curiosity.crypto.controller;

import com.curiosity.crypto.domain.PAYMENT_METHOD;
import com.curiosity.crypto.model.PaymentOrder;
import com.curiosity.crypto.model.User;
import com.curiosity.crypto.respose.PaymentResponse;
import com.curiosity.crypto.service.PaymentService;
import com.curiosity.crypto.service.UserService;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;

    @PostMapping("/{paymentMethod}/amount/{amount}")
    public ResponseEntity<PaymentResponse> paymentHandler(
            @PathVariable PAYMENT_METHOD paymentMethod,
            @PathVariable Long amount,
            @RequestHeader("Authorization") String jwt) throws Exception, RazorpayException, StripeException {
        User user = userService.findUserByJwt(jwt);
        PaymentResponse paymentResponse = null;

        PaymentOrder paymentOrder = paymentService.createOrder(user, amount, paymentMethod);

        if(paymentMethod.equals(PAYMENT_METHOD.RAZORPAY)){
            paymentResponse = paymentService.createRazorpayPaymentLink(user, amount, paymentOrder.getId());
        }
        return new ResponseEntity<>(paymentResponse, HttpStatus.ACCEPTED);
    }
}
