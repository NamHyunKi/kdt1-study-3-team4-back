package com.example.demo.Account.entity;

import com.example.demo.order.entity.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Getter
@NoArgsConstructor
public class Account {
    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    private String email;
    private String password;

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
    private AccountRole accountRole;

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setAccountRole(AccountRole accountRole) {
        this.accountRole = accountRole;

        accountRole.setAccount(this);
    }

    @JsonIgnore
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Order> orderList = new ArrayList<>();

    public Long getId() {
        return accountId;
    }
}
