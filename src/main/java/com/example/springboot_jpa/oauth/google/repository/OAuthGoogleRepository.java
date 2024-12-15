package com.example.springboot_jpa.oauth.google.repository;

import com.example.springboot_jpa.oauth.google.domain.OAuthGoogle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuthGoogleRepository extends JpaRepository<OAuthGoogle, Long> {

	Boolean existsByCode(String code);

}
