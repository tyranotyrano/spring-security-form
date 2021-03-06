package com.tyranotyrano.springsecurityform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by cyj0619 on 2021-10-20
 */
@Configuration
@EnableWebSecurity
@Order(Ordered.LOWEST_PRECEDENCE - 100)
public class AccountSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/account/**")
		    .authorizeRequests()
		    .anyRequest().authenticated();
	}
}
