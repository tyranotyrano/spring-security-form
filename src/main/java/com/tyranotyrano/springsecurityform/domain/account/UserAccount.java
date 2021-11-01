package com.tyranotyrano.springsecurityform.domain.account;

import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class UserAccount extends User {

	private final Account account;

	private UserAccount(Account account) {
		super(account.getUsername(), account.getPassword(), account.getGrantedAuthorities());
		this.account = account;
	}

	public static UserAccount create(Account account) {
		return new UserAccount(account);
	}
}
