package com.example.springboot_jpa.oauth.repository;

import com.example.springboot_jpa.oauth.domain.OAuth;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuthRepository extends JpaRepository<OAuth, Long> {

	Boolean existsByCode(String code);

	Optional<OAuth> findByCode(String code);

}
