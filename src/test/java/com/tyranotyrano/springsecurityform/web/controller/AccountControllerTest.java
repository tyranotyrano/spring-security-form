package com.tyranotyrano.springsecurityform.web.controller;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.tyranotyrano.springsecurityform.domain.account.Account;
import com.tyranotyrano.springsecurityform.web.controller.annotation.WithAdminAccount;
import com.tyranotyrano.springsecurityform.web.controller.annotation.WithUserAccount;
import com.tyranotyrano.springsecurityform.web.service.AccountService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AccountControllerTest {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private AccountService accountService;

	@Test
	@WithAnonymousUser
	void index_by_anonymous() throws Exception {
		mockMvc.perform(get("/"))
			   .andDo(print())
			   .andExpect(status().isOk());
	}

	@Test
	@WithUserAccount
	void index_by_user() throws Exception {
		mockMvc.perform(get("/"))
			   .andDo(print())
			   .andExpect(status().isOk());
	}

	@Test
	@WithAdminAccount
	void index_by_admin() throws Exception {
		mockMvc.perform(get("/").with(user("admin").roles("ADMIN")))
			   .andDo(print())
			   .andExpect(status().isOk());
	}

	@Test
	@WithUserAccount
	void info_by_user() throws Exception {
		mockMvc.perform(get("/info"))
			   .andDo(print())
			   .andExpect(status().isOk());
	}

	@Test
	@WithAdminAccount
	void info_by_admin() throws Exception {
		mockMvc.perform(get("/info"))
			   .andDo(print())
			   .andExpect(status().isOk());
	}

	@Test
	@WithUserAccount
	void dashboard_by_user() throws Exception {
		mockMvc.perform(get("/dashboard"))
			   .andDo(print())
			   .andExpect(status().isOk());
	}

	@Test
	@WithAdminAccount
	void dashboard_by_admin() throws Exception {
		mockMvc.perform(get("/dashboard"))
			   .andDo(print())
			   .andExpect(status().isOk());
	}

	@Test
	@WithUserAccount
	void admin_by_user() throws Exception {
		mockMvc.perform(get("/admin").with(user("tyrano").roles("USER")))
			   .andDo(print())
			   .andExpect(status().isForbidden()); // 403 : 일반 유저는 admin 페이지 접근 불가
	}

	@Test
	@WithAdminAccount
	void admin_by_admin() throws Exception {
		mockMvc.perform(get("/admin").with(user("admin").roles("ADMIN")))
			   .andDo(print())
			   .andExpect(status().isOk());
	}

	@Test
	public void login_success() throws Exception {
		// given
		String username = "tyrano";
		String password = "123";

		// when
		Account account = createUserAccount(username, password);

		// then
		mockMvc.perform(formLogin().user(account.getUsername()).password(password))
			   .andExpect(authenticated());
	}

	@Test
	public void login_fail() throws Exception {
		// given
		String username = "tyrano";
		String password = "123";

		// when
		Account account = createUserAccount(username, password);

		// then
		mockMvc.perform(formLogin().user(account.getUsername()).password("fail_password"))
			   .andExpect(unauthenticated());
	}

	private Account createUserAccount(String username, String password) {
		Account account = accountService.create(Account.create(username, password, "USER"));
		em.flush();
		em.clear();

		return account;
	}
}