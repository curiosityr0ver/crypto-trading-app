package com.curiosity.crypto.service;

import com.curiosity.crypto.domain.ORDER_STATUS;
import com.curiosity.crypto.domain.ORDER_TYPE;
import com.curiosity.crypto.model.*;
import com.curiosity.crypto.repository.OrderItemRepository;
import com.curiosity.crypto.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WalletService walletService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AssetService assetService;

    @Override
    public Order createOrder(User user, OrderItem orderItem, ORDER_TYPE orderType) {
        double price = orderItem.getCoin().getCurrentPrice() * orderItem.getQuantity();
        Order order = new Order();
        order.setUser(user);
        order.setOrderItem(orderItem);
        order.setOrderType(orderType);
        order.setPrice(BigDecimal.valueOf(price));
        order.setTimeStamp(LocalDateTime.now());
        order.setStatus(ORDER_STATUS.PENDING);

        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long orderId) throws EntityNotFoundException {
        return orderRepository.findById(orderId).orElseThrow(() ->
                new EntityNotFoundException("Order with id " + orderId + " not found")
        );
    }

    @Override
    public List<Order> getAllOrdersOfUser(Long userId, ORDER_TYPE orderType, String assetSymbol) {
        return orderRepository.findByUserId(userId);
    }

    private OrderItem createOrderItem(Coin coin, double quantity, double buyPrice, double sellPrice) {
        OrderItem orderItem = new OrderItem();
        orderItem.setCoin(coin);
        orderItem.setQuantity(quantity);
        orderItem.setBuyPrice(buyPrice);
        orderItem.setSellPrice(sellPrice);
        return orderItem;
    }

    @Transactional
    public Order buyAsset(Coin coin, double quantity, User user) throws Exception {
        if(quantity <= 0) {
            throw new IllegalArgumentException("Quantity should be greater than 0");
        }
        double buyPrice = coin.getCurrentPrice();

        OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, 0);
        Order order = createOrder(user, orderItem, ORDER_TYPE.BUY);
        orderItem.setOrder(order);

        walletService.payOrderPayment(order, user);

        order.setStatus(ORDER_STATUS.SUCCESS);
        order.setOrderType(ORDER_TYPE.BUY);

        Order savedOrder = orderRepository.save(order);

//        create assets
        Asset oldAsset = assetService.findAssetByUserIdAndCoinId(user.getId(), order.getOrderItem().getCoin().getId());

        if(oldAsset == null) {
            assetService.createAsset(user, orderItem.getCoin(), orderItem.getQuantity());
        } else {
            assetService.updateAsset(oldAsset.getId(), quantity);
        }
        return savedOrder;
    }


    @Transactional
    public Order sellAsset(Coin coin, double quantity, User user) throws Exception {
        if(quantity <= 0) {
            throw new IllegalArgumentException("Quantity should be greater than 0");
        }
        double sellPrice = coin.getCurrentPrice();
        Asset assetToSell = assetService.findAssetByUserIdAndCoinId(user.getId(), coin.getId());
        double buyPrice = assetToSell.getBuyPrice();

        if(assetToSell != null) {

            OrderItem orderItem = createOrderItem(coin, quantity, 0, sellPrice);

            Order order = createOrder(user, orderItem, ORDER_TYPE.SELL);
            orderItem.setOrder(order);

            if(assetToSell.getQuantity()>=quantity){
                order.setStatus(ORDER_STATUS.SUCCESS);
                order.setOrderType(ORDER_TYPE.SELL);
                Order savedOrder = orderRepository.save(order);

                walletService.payOrderPayment(order, user);

                Asset updateAsset = assetService.updateAsset(assetToSell.getId(), -quantity);

                if(updateAsset.getQuantity()*coin.getCurrentPrice()<=1) {
                    assetService.deleteAsset(assetToSell.getId());
                }
                return savedOrder;
            }
            throw new IllegalArgumentException("Insufficient quantity to sell");
        }
         throw new Exception("Asset not found/already sold");
    }

    @Override
    @Transactional
    public Order processOrder(Coin coin, double quantity, ORDER_TYPE orderType, User user) throws Exception {
        if(orderType.equals(ORDER_TYPE.BUY)){
            return buyAsset(coin,quantity,user);
        }else if(orderType.equals(ORDER_TYPE.SELL)){
            return sellAsset(coin, quantity, user);
        }

        throw new IllegalArgumentException("Invalid order type");
    }
}
