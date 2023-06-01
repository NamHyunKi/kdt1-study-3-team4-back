package com.example.demo.Account.service;

import com.example.demo.Account.controller.form.AccountLoginResponseForm;
import com.example.demo.Account.entity.RoleType;
import com.example.demo.Account.service.request.AccountLoginRequest;
import com.example.demo.Account.service.request.AccountRegisterRequest;

public interface AccountService {
    Boolean register(AccountRegisterRequest request);

    AccountLoginResponseForm login(AccountLoginRequest request);

    RoleType lookup(String accountToken);

    Long findAccountId(String accountToken);
}