package com.tyranotyrano.springsecurityform.web.controller;

import java.security.Principal;
import java.util.concurrent.Callable;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tyranotyrano.springsecurityform.common.util.SecurityLogger;
import com.tyranotyrano.springsecurityform.domain.account.Account;
import com.tyranotyrano.springsecurityform.web.annotaion.CurrentUser;
import com.tyranotyrano.springsecurityform.web.service.SecurityAsyncService;
import com.tyranotyrano.springsecurityform.web.service.SecurityContextService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ViewController {

	private final SecurityContextService securityContextService;
	private final SecurityAsyncService securityAsyncService;

	@GetMapping("/")
	public String index(Model model, @CurrentUser Account account) {
		if (account == null) {
			// 로그인 안한 사람
			model.addAttribute("message", "index : Hello Spring Security!!!");
		}
		else {
			// 로그인 한 사람
			model.addAttribute("message", "index : " + account.getUsername());
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
		securityContextService.confirmAuthentication();
		return "dashboard";
	}

	@GetMapping("/admin")
	public String admin(Model model, Principal principal) {
		// admin : 관리자만 접근 가능 -> Principal 필요
		model.addAttribute("message", "admin : " + principal.getName());
		return "admin";
	}

	@GetMapping("/user")
	public String user(Model model, Principal principal) {
		// user : 일반 사용자 or 관리자만 접근 가능 -> Principal 필요
		model.addAttribute("message", "user : " + principal.getName());
		return "user";
	}

	@GetMapping("/async-handler")
	@ResponseBody
	public Callable<String> asyncHandler() {
		SecurityLogger.log("MVC");
		return () -> {
			SecurityLogger.log("Callable");
			return "async handler";
		};
	}

	@GetMapping("/async-service")
	@ResponseBody
	public String asyncService() {
		SecurityLogger.log("MVC, before async service");
		securityAsyncService.asyncService();
		SecurityLogger.log("MVC, after async service");
		return "Async Service";
	}
}
