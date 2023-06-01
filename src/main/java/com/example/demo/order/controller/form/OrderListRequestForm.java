package com.example.demo.order.controller.form;

import lombok.Getter;

@Getter
public class OrderListRequestForm {
    final private String userToken;

    public OrderListRequestForm(String userToken) {
        this.userToken = userToken;
    }
}
