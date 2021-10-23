package com.tyranotyrano.springsecurityform.web.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.tyranotyrano.springsecurityform.web.service.AccountService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SignUpControllerTest {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private AccountService accountService;

	@Test
	void signupFormWith_CSRF_TOKEN() throws Exception {
		mockMvc.perform(get("/signup"))
			   .andDo(print())
			   .andExpect(status().isOk())
			   .andExpect(content().string(containsString("_csrf")));
	}

	@Test
	void processSignUpWithout_CSRF_TOKEN() throws Exception {
		mockMvc.perform(post("/signup")
							.param("username", "tyrano")
							.param("password", "123"))
			   .andDo(print())
			   .andExpect(status().is4xxClientError());
	}

	@Test
	void processSignUpWith_CSRF_TOKEN() throws Exception {
		mockMvc.perform(post("/signup")
							.param("username", "tyrano")
							.param("password", "123")
							.with(csrf()))
			   .andDo(print())
			   .andExpect(status().is3xxRedirection());
	}
}