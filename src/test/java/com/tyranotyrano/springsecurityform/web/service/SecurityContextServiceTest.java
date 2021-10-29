package com.tyranotyrano.springsecurityform.web.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.tyranotyrano.springsecurityform.domain.account.Account;
import com.tyranotyrano.springsecurityform.web.controller.annotation.WithAdminAccount;
import com.tyranotyrano.springsecurityform.web.controller.annotation.WithUserAccount;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class SecurityContextServiceTest {

	@Autowired
	private AccountService accountService;

	@Autowired
	private SecurityContextService securityContextService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Test
	void confirmAuthenticationTest() {
		Account account = Account.create("tyrano", "123", "USER");
		accountService.create(account);

		UserDetails userDetails = accountService.loadUserByUsername("tyrano");
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, "123");
		Authentication authentication = authenticationManager.authenticate(token);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		securityContextService.confirmAuthentication();
	}

	@Test
	@WithUserAccount
	void confirmAuthenticationTest_By_Mock_User() {
		securityContextService.confirmAuthentication();
	}

	@Test
	@WithAdminAccount
	void confirmAuthenticationTest_By_Mock_Admin() {
		securityContextService.confirmAuthentication();
	}
}