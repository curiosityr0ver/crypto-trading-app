package com.curiosity.crypto.request;

import com.curiosity.crypto.domain.ORDER_TYPE;
import lombok.Data;

@Data
public class CreateOrderRequest {
    private String coinId;
    private double quantity;
    private ORDER_TYPE orderType;
}