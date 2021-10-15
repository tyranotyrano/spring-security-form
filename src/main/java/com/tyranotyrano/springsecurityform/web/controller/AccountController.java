package com.tyranotyrano.springsecurityform.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tyranotyrano.springsecurityform.domain.account.Account;
import com.tyranotyrano.springsecurityform.web.service.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

	private final AccountService accountService;

	@GetMapping(path = "/tmp/create/{username}/{password}/{role}")
	public Account create(@ModelAttribute Account account) {
		return accountService.create(account);
	}
}
