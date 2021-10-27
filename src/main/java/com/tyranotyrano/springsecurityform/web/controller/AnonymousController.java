package com.tyranotyrano.springsecurityform.web.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnonymousController {

	@GetMapping("/anonymous")
	public String anonymous(Model model, Principal principal) {
		model.addAttribute("principal", principal);
		return "anonymous";
	}
}
