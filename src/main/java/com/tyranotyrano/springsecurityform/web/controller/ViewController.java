package com.tyranotyrano.springsecurityform.web.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

	@GetMapping("/")
	public String index(Model model, Principal principal) {
		if (principal == null) {
			// 로그인 안한 사람
			model.addAttribute("message", "index : Hello Spring Security!!!");
		}
		else {
			// 로그인 한 사람
			model.addAttribute("message", "index : " + principal.getName());
		}


		return "index";
	}

	@GetMapping("/info")
	public String info(Model model) {
		// Info : 로그인 여부에 상관없이 아무나 접근가능
		model.addAttribute("message", "info : 아무나 접근 가능");
		return "info";
	}

	@GetMapping("/dashboard")
	public String dashboard(Model model, Principal principal) {
		// Dashboard : 로그인한 사용자만 접근가능 -> Principal 필요
		model.addAttribute("message", "dashboard : " + principal.getName());
		return "dashboard";
	}

	@GetMapping("/admin")
	public String admin(Model model, Principal principal) {
		// admin : 관리자만 접근 가능 -> Principal 필요
		model.addAttribute("message", "admin : " + principal.getName());
		return "admin";
	}
}
