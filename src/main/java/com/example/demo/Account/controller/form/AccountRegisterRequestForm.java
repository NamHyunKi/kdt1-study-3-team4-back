package com.example.demo.Account.controller.form;

import com.example.demo.Account.entity.RoleType;
import com.example.demo.Account.service.request.AccountRegisterRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccountRegisterRequestForm {

    final private String email;
    final private String password;
    final private RoleType roleType;

    public AccountRegisterRequest toAccountRegisterRequest () {

        return new AccountRegisterRequest(
                email, password, roleType);
    }
}