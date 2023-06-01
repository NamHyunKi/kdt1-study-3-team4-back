package com.example.demo.Account.dto;

import com.example.demo.Account.entity.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {
    private Long accountId;
    private String email;

    public AccountDTO(Account account) {
        this.accountId = account.getAccountId();
        this.email = account.getEmail();
    }
}