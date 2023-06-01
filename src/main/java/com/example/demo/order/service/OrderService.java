package com.example.demo.order.service;

import com.example.demo.order.dto.OrderDTO;
import com.example.demo.order.service.request.OrderRegisterRequest;

import java.util.List;

public interface OrderService {
    List<OrderDTO> list(String userToken);

    OrderDTO read(Long orderId);

    int delete(Long orderId);

    Boolean register(Long id, OrderRegisterRequest orderRegisterRequest);
}
