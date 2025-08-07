package com.trading.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trading.domain.OrderType;
import com.trading.entities.Coin;
import com.trading.entities.Order;
import com.trading.entities.User;
import com.trading.request.CreateOrderRequest;
import com.trading.service.CoinService;
import com.trading.service.OrderService;
import com.trading.service.UserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController
{
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CoinService coinService;
	
//	@Autowired
//	private WalletTransactionService walletTransactionService;
//	
	
	@PostMapping("/pay")
	public ResponseEntity<Order> payOrderPayment(@RequestHeader("Authorization") String jwt,
															@RequestBody CreateOrderRequest req) throws Exception
	{
		
		User user = userService.findUserByProfileByJwt(jwt);
		
		Coin coin = coinService.findById(req.getCoinId());
		
		Order order = orderService.processOrder(coin, req.getQuantity(), req.getOrderType(), user);
		
		return ResponseEntity.ok(order);
	}
	
	
	@GetMapping("/{orderId}")
	public ResponseEntity<Order> getOrderById(@RequestHeader("Authorization") String jwt,
															@PathVariable Long orderId) throws Exception
	{
//		if (jwt == null)
//		{
//			throw new Exception("Token missing");
//		}
		
		User user = userService.findUserByProfileByJwt(jwt);
		
		
		Order order = orderService.getOrderById(orderId);
		
		if(order.getUser().getId().equals(user.getId()))
		{
			return ResponseEntity.ok(order);
		}
		else
		{
			throw new Exception("you don't have access");
			//return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
	}
	
	
	
	@GetMapping()
	public ResponseEntity<List<Order>> getAllOrdersForUser(@RequestHeader("Authorization") String jwt,
															@RequestParam(required = false) OrderType order_type,
															@RequestParam(required = false) String asset_symbol) throws Exception
	{

		
		Long userId = userService.findUserByProfileByJwt(jwt).getId();
		
		
		List<Order> userOrder = orderService.getAllOrderOfUser(userId, order_type, asset_symbol);
		
		return ResponseEntity.ok(userOrder);
		
	}
	

}
