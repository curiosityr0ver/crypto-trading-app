package com.curiosity.crypto.service;

import com.curiosity.crypto.domain.ORDER_TYPE;
import com.curiosity.crypto.model.Coin;
import com.curiosity.crypto.model.Order;
import com.curiosity.crypto.model.OrderItem;
import com.curiosity.crypto.model.User;

import java.util.List;

public interface OrderService {

    Order createOrder(User user, OrderItem orderItem, ORDER_TYPE orderType);

    Order getOrderById(Long orderId) throws Exception;

    List<Order> getAllOrdersOfUser(Long userId, ORDER_TYPE orderType, String assetSymbol);

    Order processOrder(Coin coin, double quantity, ORDER_TYPE orderType, User user) throws Exception;
}
