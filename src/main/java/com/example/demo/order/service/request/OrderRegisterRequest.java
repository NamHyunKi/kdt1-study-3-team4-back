package com.example.demo.order.service.request;

import lombok.Getter;

@Getter
public class OrderRegisterRequest {
    final private Long productId;

    public OrderRegisterRequest(Long productId) {
        this.productId = productId;
    }
}
