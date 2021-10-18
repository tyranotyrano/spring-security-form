package com.tyranotyrano.springsecurityform.web.service;

import org.springframework.stereotype.Service;

import com.tyranotyrano.springsecurityform.domain.account.Account;
import com.tyranotyrano.springsecurityform.domain.account.AccountContext;

@Service
public class SecurityContextService {

	public void confirmAuthentication() {
		Account account = AccountContext.getAccount();
		System.out.println("===================================");
		System.out.println(account.getUsername());
	}
}
