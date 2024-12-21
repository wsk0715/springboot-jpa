package com.example.springboot_jpa.oauth.kakao.repository;

import com.example.springboot_jpa.oauth.kakao.domain.OAuthKakao;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuthKakaoRepository extends JpaRepository<OAuthKakao, Long> {

	Boolean existsByCode(String code);

	Optional<OAuthKakao> findByCode(String code);

}
