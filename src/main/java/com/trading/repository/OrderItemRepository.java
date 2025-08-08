package com.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trading.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>
{

}
