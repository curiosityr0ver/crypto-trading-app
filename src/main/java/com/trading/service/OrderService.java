package com.trading.service;

import java.util.List;

import com.trading.domain.OrderType;
import com.trading.entities.Coin;
import com.trading.entities.Order;
import com.trading.entities.OrderItem;
import com.trading.entities.User;

public interface OrderService 
{
	Order createOrder(User user, OrderItem orderItem, OrderType orderType);
	
	Order getOrderById(Long orderId) throws Exception;
	
	List<Order> getAllOrderOfUser(Long userId, OrderType orderType, String assetSymbol);

	Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;
	
	
	
}
