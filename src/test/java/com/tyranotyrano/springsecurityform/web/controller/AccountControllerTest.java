package com.tyranotyrano.springsecurityform.web.controller;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AccountControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithAnonymousUser
	void index_by_anonymous() throws Exception {
		mockMvc.perform(get("/"))
			   .andDo(print())
			   .andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "tyrano", roles = "USER")
	void index_by_user() throws Exception {
		mockMvc.perform(get("/"))
			   .andDo(print())
			   .andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "admin", roles = "ADMIN")
	void index_by_admin() throws Exception {
		mockMvc.perform(get("/").with(user("admin").roles("ADMIN")))
			   .andDo(print())
			   .andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "tyrano", roles = "USER")
	void admin_by_user() throws Exception {
		mockMvc.perform(get("/admin").with(user("tyrano").roles("USER")))
			   .andDo(print())
			   .andExpect(status().isForbidden()); // 403 : 일반 유저는 admin 페이지 접근 불가
	}

	@Test
	@WithMockUser(username = "admin", roles = "ADMIN")
	void admin_by_admin() throws Exception {
		mockMvc.perform(get("/admin").with(user("admin").roles("ADMIN")))
			   .andDo(print())
			   .andExpect(status().isOk());
	}
}