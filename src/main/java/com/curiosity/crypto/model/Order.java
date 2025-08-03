package com.curiosity.crypto.model;

import com.curiosity.crypto.domain.ORDER_STATUS;
import com.curiosity.crypto.domain.ORDER_TYPE;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private ORDER_TYPE orderType;

    @Column(nullable = false)
    private BigDecimal price;

    private LocalDateTime timeStamp=LocalDateTime.now();

    @Column(nullable = false)
    private ORDER_STATUS status;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private OrderItem orderItem;


}
