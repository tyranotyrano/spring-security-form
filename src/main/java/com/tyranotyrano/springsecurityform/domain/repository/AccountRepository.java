package com.tyranotyrano.springsecurityform.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tyranotyrano.springsecurityform.domain.account.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	Account findByUsername(String username);

	@Query("SELECT a FROM Account a WHERE a.username = ?#{principal.username}")
	Account findCurrentUser();
}
