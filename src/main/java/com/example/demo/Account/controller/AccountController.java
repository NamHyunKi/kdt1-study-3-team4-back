package com.example.demo.Account.controller;

import com.example.demo.Account.controller.form.AccountLoginRequestForm;
import com.example.demo.Account.controller.form.AccountLoginResponseForm;
import com.example.demo.Account.controller.form.AccountRegisterRequestForm;
import com.example.demo.Account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    final private AccountService accountService;

    @PostMapping("/register")
    public Boolean accountRegister (@RequestBody AccountRegisterRequestForm requestForm) {
        return accountService.register(requestForm.toAccountRegisterRequest());
    }

    @PostMapping("/login")
    public AccountLoginResponseForm accountLogin (@RequestBody AccountLoginRequestForm requestForm) {
        return accountService.login(requestForm.toAccountLoginRequest());
    }
}