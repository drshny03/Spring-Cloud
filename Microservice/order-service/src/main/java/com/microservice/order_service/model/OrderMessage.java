package com.microservice.order_service.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderMessage implements Serializable {
    Long orderId;
    private Long productId;
    private int quantity;

    // Constructors, Getters, and Setters
}