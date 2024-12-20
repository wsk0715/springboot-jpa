package com.example.springboot_jpa.oauth.google.service;

import com.example.springboot_jpa.oauth.common.service.OAuthCommonService;
import com.example.springboot_jpa.oauth.constants.OAuthProvider;
import com.example.springboot_jpa.oauth.google.domain.OAuthGoogle;
import com.example.springboot_jpa.oauth.google.repository.OAuthGoogleRepository;
import com.example.springboot_jpa.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OAuthGoogleService {

	private final OAuthCommonService oAuthCommonService;

	private final OAuthGoogleRepository oAuthGoogleRepository;


	@Transactional
	public boolean init(String code) {
		String accessToken = oAuthCommonService.getAccessToken(OAuthProvider.GOOGLE, code);
		String googleSub = (String) oAuthCommonService.getUserInfo(OAuthProvider.GOOGLE, accessToken)
													  .get(OAuthProvider.GOOGLE.getIdentifier());

		// 소셜 로그인 정보 존재하지 않을 시
		if (!oAuthGoogleRepository.existsByCode(googleSub)) {
			// 1. 임시 사용자 생성
			User user = oAuthCommonService.createTempUser(OAuthProvider.GOOGLE);
			Long userId = user.getId();  // 임시 사용자 id

			// 2. 임시 사용자와 구글 식별값 연결
			OAuthGoogle oauth = OAuthGoogle.create(userId, googleSub);
			oAuthGoogleRepository.save(oauth);

			return false;
		}

		return true;
	}

}
