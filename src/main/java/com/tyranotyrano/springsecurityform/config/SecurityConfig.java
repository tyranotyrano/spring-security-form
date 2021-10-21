package com.tyranotyrano.springsecurityform.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.mvcMatchers("/", "/info", "/account/tmp/**").permitAll()
			.mvcMatchers("/admin").hasRole("ADMIN")
			.mvcMatchers("/user").hasRole("USER")
			.anyRequest().authenticated()
			.expressionHandler(expressionHandler());
		http.formLogin();
		http.httpBasic();
	}

	/**
	 * 사용자의 권한 상하관계 Hierarchy 를 설정한 SecurityExpressionHandler 를 만든다.
	 *   - 권한 상하관계 : ADMIN > USER > ANONYMOUS
	 * */
	private SecurityExpressionHandler expressionHandler() {
		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
		roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

		DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
		handler.setRoleHierarchy(roleHierarchy);

		return handler;
	}

	/**
	 * 사용자의 권한 상하관계 Hierarchy 를 설정한 AccessDecisionManager 를 만든다.
	 *   - 권한 상하관계 : ADMIN > USER > ANONYMOUS
	 * */
	private AccessDecisionManager accessDecisionManager() {
		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
		roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

		DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
		handler.setRoleHierarchy(roleHierarchy);

		WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
		webExpressionVoter.setExpressionHandler(handler);

		List<AccessDecisionVoter<? extends Object>> voters = Arrays.asList(webExpressionVoter);
		return new AffirmativeBased(voters);
	}
}
