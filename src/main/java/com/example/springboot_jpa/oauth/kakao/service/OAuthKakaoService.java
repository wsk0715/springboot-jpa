package com.example.springboot_jpa.oauth.kakao.service;

import com.example.springboot_jpa.common.constants.OAuthProvider;
import com.example.springboot_jpa.oauth.common.service.OAuthCommonService;
import com.example.springboot_jpa.oauth.kakao.domain.OAuthKakao;
import com.example.springboot_jpa.oauth.kakao.repository.OAuthKakaoRepository;
import com.example.springboot_jpa.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OAuthKakaoService {

	private final OAuthCommonService oAuthCommonService;

	private final OAuthKakaoRepository oAuthKakaoRepository;


	@Transactional
	public boolean init(String code) {
		String accessToken = oAuthCommonService.getAccessToken(OAuthProvider.KAKAO, code);
		String kakaoId = String.valueOf(oAuthCommonService.getUserInfo(OAuthProvider.KAKAO, accessToken)
														  .get(OAuthProvider.KAKAO.getIdentifier()));

		// 소셜 로그인 정보 존재하지 않을 시
		if (!oAuthKakaoRepository.existsByCode(kakaoId)) {
			// 1. 임시 사용자 생성
			User user = oAuthCommonService.createTempUser(OAuthProvider.KAKAO);
			Long userId = user.getId();  // 임시 사용자 id

			// 2. 임시 사용자와 카카오 id 식별값 연결
			OAuthKakao oauth = OAuthKakao.create(userId, kakaoId);
			oAuthKakaoRepository.save(oauth);

			return false;
		}

		return true;
	}

}
