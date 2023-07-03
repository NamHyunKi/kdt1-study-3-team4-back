package com.example.demo.Account.controller.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccountCheckEmailForm {
    final private String email;
    final private String userToken;
}