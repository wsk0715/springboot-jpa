package com.example.springboot_jpa.auth.repository;

import com.example.springboot_jpa.auth.domain.Auth;
import com.example.springboot_jpa.auth.domain.vo.AuthLoginId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, Long> {

	Auth findByLoginId(AuthLoginId loginId);

	Auth findByUserId(Long id);

}
