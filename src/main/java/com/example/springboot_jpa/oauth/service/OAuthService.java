package com.example.springboot_jpa.oauth.service;

import com.example.springboot_jpa.oauth.dto.OAuthResult;
import org.springframework.transaction.annotation.Transactional;

public interface OAuthService {

	@Transactional
	OAuthResult init(String code);

}
