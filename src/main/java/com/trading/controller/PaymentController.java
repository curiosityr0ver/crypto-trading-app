package com.trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trading.domain.PaymentMethod;
import com.trading.entities.PaymentOrder;
import com.trading.entities.User;
import com.trading.response.PaymentResponse;
import com.trading.service.PaymentService;
import com.trading.service.UserService;

@RequestMapping("/api/payment")
@RestController
public class PaymentController 
{

	@Autowired
	private UserService userService;
	
	@Autowired
	private PaymentService paymentService;
	
	
	@PostMapping("/{paymentMethod}/amount/{amount}")
	public ResponseEntity<PaymentResponse> paymentHandler(
									@PathVariable PaymentMethod paymentMethod,
									@PathVariable Long amount,
									@RequestHeader("Authorization") String jwt
								) throws Exception
	{
		
		User user= userService.findUserByProfileByJwt(jwt);
		
		PaymentResponse paymentResponse;
		
		PaymentOrder order = paymentService.createOrder(user, amount, paymentMethod);
		
		if (paymentMethod.equals(PaymentMethod.RAZORPAY))
		{
			paymentResponse = paymentService.createRazorpayPaymentLink(user, amount, order.getId());
		}
		else
		{
			paymentResponse = paymentService.createStripePaymentLink(user, amount,order.getId());

		}
		
		return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
		
	}
	
}