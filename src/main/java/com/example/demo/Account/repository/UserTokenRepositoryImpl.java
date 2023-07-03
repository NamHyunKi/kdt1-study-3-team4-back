package com.example.demo.Account.repository;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@NoArgsConstructor
public class UserTokenRepositoryImpl implements UserTokenRepository {

    private static final UserTokenRepository INSTANCE = new UserTokenRepositoryImpl();
    private final Map<String, Long> userTokenMap = new HashMap<>();

    public static UserTokenRepository getInstance () {
        return INSTANCE;
    }

    @Override
    public void save(String userToken, Long id) {
        userTokenMap.put(userToken, id);
        log.info(String.valueOf(userTokenMap));
    }

    @Override
    public Long findAccountIdByToken(String userToken) {
        return userTokenMap.get(userToken);
    }
}