package com.tyranotyrano.springsecurityform.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tyranotyrano.springsecurityform.domain.account.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	Account findByUserName(String username);
}
