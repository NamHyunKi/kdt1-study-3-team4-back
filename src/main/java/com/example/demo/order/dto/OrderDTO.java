package com.example.demo.order.dto;

import com.example.demo.Account.dto.AccountDTO;
import com.example.demo.product.dto.ProductDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
    private Long orderId;
    private AccountDTO account;
    private ProductDTO product;
    private String orderStatus;
    private String deliveryStatus;
    private LocalDateTime createDate;

    public OrderDTO(Long orderId, AccountDTO account, ProductDTO product, String orderStatus, String deliveryStatus, LocalDateTime createDate) {
        this.orderId = orderId;
        this.account = account;
        this.product = product;
        this.orderStatus = orderStatus;
        this.deliveryStatus = deliveryStatus;
        this.createDate = createDate;
    }
}