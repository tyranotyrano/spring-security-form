package com.tyranotyrano.springsecurityform.web.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.tyranotyrano.springsecurityform.common.util.SecurityLogger;

@Service
public class SecurityAsyncService {

	@Async
	public void asyncService() {
		SecurityLogger.log("Async Service");
		System.out.println("Async service is Called.");
	}
}
