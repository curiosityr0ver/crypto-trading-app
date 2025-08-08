package com.trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.trading.entities.Order;
import com.trading.entities.PaymentOrder;
import com.trading.entities.User;
import com.trading.entities.Wallet;
import com.trading.entities.WalletTransation;
import com.trading.service.OrderService;
import com.trading.service.PaymentService;
import com.trading.service.UserService;
import com.trading.service.WalletService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/wallet")
public class WalletController
{
	@Autowired
	private WalletService walletService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private PaymentService paymentService;
	
	
	@GetMapping("/")
	public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception
	{
		User user = userService.findUserByProfileByJwt(jwt);
		
		Wallet wallet = walletService.getUserWallet(user);
		
		return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
	}
	
	
	@PutMapping("/{walletId}/transfer")
	public ResponseEntity<Wallet> walletToWalletTransfer(@RequestHeader("Authorization") String jwt,
														 @PathVariable	Long walletId,
														 @RequestBody WalletTransation req) throws Exception
	{
		User senderUser = userService.findUserByProfileByJwt(jwt);
		
		Wallet receiverWallet = walletService.findByWalletId(walletId);
		
		Wallet wallet = walletService.walletToWalletTransfer(senderUser, 
															receiverWallet, 
															req.getAmount());
		
		
		return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
	}

	
	@PutMapping("/order/{orderId}/pay")
	public ResponseEntity<Wallet> payOrderPayment(@RequestHeader("Authorization") String jwt,
												@PathVariable	Long orderId
												)  throws Exception
	{
		User user = userService.findUserByProfileByJwt(jwt);
		
		Order order = orderService.getOrderById(orderId);
		
		Wallet wallet = walletService.payOrderPayment(order, user);
		
		return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
	}
	
	
	@PutMapping("/deposit")
	public ResponseEntity<Wallet> addBalanceToWallet(@RequestHeader("Authorization") String jwt,
												@RequestParam("order_id") Long orderId,
												@RequestParam("payment_id") String paymentId
												)  throws Exception
	{
		User user = userService.findUserByProfileByJwt(jwt);
		
		
		Wallet wallet = walletService.getUserWallet(user);
		
		PaymentOrder order = paymentService.getPaymentOrderById(orderId);
		
		Boolean status = paymentService.proceedPaymentOrder(order, paymentId);
		
//		PaymentResponse response = new PaymentResponse();
//		response.setPayment_url("Deposite success");

		if(wallet.getBalance()==null){
			wallet.setBalance(BigDecimal.valueOf(0));
		}

		if (status)
		{
			wallet = walletService.addBalanceToWallet(wallet, order.getAmount());
		}
		
		
		return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
	}

}
