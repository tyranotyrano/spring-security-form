package com.tyranotyrano.springsecurityform.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tyranotyrano.springsecurityform.domain.account.Account;
import com.tyranotyrano.springsecurityform.web.service.AccountService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignUpController {

	private final AccountService accountService;

	@GetMapping
	public String signupForm(Model model) {
		model.addAttribute("account", Account.createEmpty());
		return "signup";
	}

	@PostMapping
	public String processSignUp(@ModelAttribute Account account) {
		account.setRole("USER");
		accountService.create(account);
		return "redirect:/";
	}
}
