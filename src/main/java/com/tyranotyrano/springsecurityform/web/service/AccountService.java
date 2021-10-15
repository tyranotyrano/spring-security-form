package com.tyranotyrano.springsecurityform.web.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tyranotyrano.springsecurityform.domain.account.Account;
import com.tyranotyrano.springsecurityform.domain.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService implements UserDetailsService {

	private final AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findByUserName(username);
		if (account == null) {
			throw new UsernameNotFoundException(username);
		}

		return User.builder()
				   .username(account.getUsername())
				   .password(account.getPassword())
				   .roles(account.getRole())
				   .build();
	}
}
