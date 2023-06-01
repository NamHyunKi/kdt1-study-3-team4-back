package com.example.demo.Account.repository;

import com.example.demo.Account.entity.Account;
import com.example.demo.Account.entity.AccountRole;
import com.example.demo.Account.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRoleRepository extends JpaRepository<AccountRole, Long> {

    @Query("select ar.role from AccountRole ar join fetch Role r where ar.account = :account")
    Role findRoleInfoByAccount(Account account);
}