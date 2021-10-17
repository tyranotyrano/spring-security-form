package com.tyranotyrano.springsecurityform.web.controller.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithMockUser;

@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(username = "tyrano", roles = "USER")
public @interface WithUserAccount {
}
