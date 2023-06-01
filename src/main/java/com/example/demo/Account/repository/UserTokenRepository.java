package com.example.demo.Account.repository;

public interface UserTokenRepository {

    void save(String userToken, Long id);

    Long findAccountIdByToken(String userToken);
}