package com.example.demo.Account.service;

import com.example.demo.Account.controller.form.AccountLoginResponseForm;
import com.example.demo.Account.entity.Account;
import com.example.demo.Account.entity.AccountRole;
import com.example.demo.Account.entity.Role;
import com.example.demo.Account.entity.RoleType;
import com.example.demo.Account.repository.*;
import com.example.demo.Account.service.request.AccountLoginRequest;
import com.example.demo.Account.service.request.AccountRegisterRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    final private AccountRepository accountRepository;
    final private AccountRoleRepository accountRoleRepository;
    final private RoleRepository roleRepository;

    final private UserTokenRepository userTokenRepository = UserTokenRepositoryImpl.getInstance();

    @Override
    public Boolean register(AccountRegisterRequest request) {
        final Optional<Account> maybeAccount = accountRepository.findByEmail(request.getEmail());
        if (maybeAccount.isPresent()) {
            return false;
        }

        final Account account = accountRepository.save(request.toAccount());

        final Role role = roleRepository.findByRoleType(
                request.getRoleType()).get();
        final AccountRole accountRole =
                new AccountRole(account, role);
        accountRoleRepository.save(accountRole);

        return true;
    }

    @Override
    @Transactional
    public AccountLoginResponseForm login(AccountLoginRequest request) {
        final Optional<Account> maybeAccount = accountRepository.findByEmail(request.getEmail());
        if (maybeAccount.isEmpty()) {
            return null;
        }

        final Account account = maybeAccount.get();
        if (account.getPassword().equals(request.getPassword())) {
            final String userToken = UUID.randomUUID().toString();
            userTokenRepository.save(userToken, account.getId());

            final Role role = accountRoleRepository.findRoleInfoByAccount(account);
            log.info(role.getRoleType().name());

            return new AccountLoginResponseForm(userToken, role.getRoleType().name());
        }

        return null;
    }

    @Override
    public RoleType lookup(String accountToken) {
        final Long accountId = userTokenRepository.findAccountIdByToken(accountToken);
        final Optional<Account> maybeAccount = accountRepository.findById(accountId);

        if (maybeAccount.isEmpty()) {
            return null;
        }

        final Account account = maybeAccount.get();
        final Role role = accountRoleRepository.findRoleInfoByAccount(account);

        log.info("roleType: " + role.getRoleType());

        return role.getRoleType();
    }

    @Override
    public Long findAccountId(String userToken) {
        return userTokenRepository.findAccountIdByToken(userToken);
    }

}