package com.tyranotyrano.springsecurityform.web.service;

import javax.annotation.security.RolesAllowed;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextService {

	@Secured("ROLE_USER")
	// @Secured({"ROLE_USER", "ROLE_ADMIN"})
	// @RolesAllowed("ROLE_USER")
	// @PreAuthorize("hasRole('USER')")
	// @PostAuthorize("isAuthenticated() and ((returnObject.name == principal.username) or hasRole('ROLE_USER'))")
	public void confirmAuthentication() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		System.out.println("===================================");
		System.out.println(authentication);
		System.out.println(userDetails.getUsername());
	}
}
