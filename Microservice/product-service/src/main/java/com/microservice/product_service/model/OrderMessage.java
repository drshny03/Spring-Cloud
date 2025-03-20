package com.microservice.product_service.model;

import lombok.Data;

@Data
public class OrderMessage {
    private String orderId;
    private Long productId;
    private int quantity;

}
