package com.tyranotyrano.springsecurityform.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

import com.tyranotyrano.springsecurityform.web.filter.LoggingFilter;
import com.tyranotyrano.springsecurityform.web.service.AccountService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AccountService accountService;

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(new LoggingFilter(), WebAsyncManagerIntegrationFilter.class);

		http.authorizeRequests()
			.mvcMatchers("/", "/info", "/account/tmp/**", "/signup").permitAll()
			.mvcMatchers("/admin").hasRole("ADMIN")
			.mvcMatchers("/user").hasRole("USER")
			.mvcMatchers("/anonymous").anonymous()
			.anyRequest().authenticated()
			.expressionHandler(expressionHandler());

		http.formLogin()
			.loginPage("/login").permitAll();

		http.httpBasic();

		http.logout().logoutSuccessUrl("/");

		http.rememberMe()
			.userDetailsService(accountService)
			.key("remember-me-sample");

		http.exceptionHandling()
			.accessDeniedHandler((request, response, accessDeniedException) -> {
				UserDetails principal = (UserDetails)SecurityContextHolder.getContext()
																		  .getAuthentication()
																		  .getPrincipal();
				String username = principal.getUsername();
				System.out.println(username + " is denied to access " + request.getRequestURI());
				response.sendRedirect("/access-denied");
			});

		// http.sessionManagement()
		// 	.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
		// 	.sessionFixation()
		// 	.changeSessionId()
		// 	.invalidSessionUrl("/login")
		// 	.maximumSessions(1)
		// 	.expiredUrl("/login")
		// 	.maxSessionsPreventsLogin(false);

		// http.csrf().disable();

		// SecurityContextHolder Strategy ??????
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	/**
	 * ???????????? ?????? ???????????? Hierarchy ??? ????????? SecurityExpressionHandler ??? ?????????.
	 *   - ?????? ???????????? : ADMIN > USER > ANONYMOUS
	 * */
	private SecurityExpressionHandler expressionHandler() {
		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
		roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

		DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
		handler.setRoleHierarchy(roleHierarchy);

		return handler;
	}

	/**
	 * ???????????? ?????? ???????????? Hierarchy ??? ????????? AccessDecisionManager ??? ?????????.
	 *   - ?????? ???????????? : ADMIN > USER > ANONYMOUS
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
