package com.tyranotyrano.springsecurityform.domain.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "account")
@Data
public class Account {
	private static String NOOP_PASSWORD_ENCODING = "{noop}";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String username;

	private String password;

	private String role;

	public void encodePassword() {
		this.password = NOOP_PASSWORD_ENCODING + this.password;
	}
}
