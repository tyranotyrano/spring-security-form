package com.tyranotyrano.springsecurityform.domain.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;

@Entity
@Table(name = "account")
@Data
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String username;

	private String password;

	private String role;

	public void encodePassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.password);
	}
}
