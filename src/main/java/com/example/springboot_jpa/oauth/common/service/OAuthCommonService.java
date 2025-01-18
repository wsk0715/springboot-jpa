package com.example.springboot_jpa.oauth.common.service;

import com.example.springboot_jpa.common.exception.SpringbootJpaException;
import com.example.springboot_jpa.common.util.NicknameGenerator;
import com.example.springboot_jpa.oauth.constants.OAuthProvider;
import com.example.springboot_jpa.oauth.properties.OAuthGoogleProperties;
import com.example.springboot_jpa.oauth.properties.OAuthKakaoProperties;
import com.example.springboot_jpa.user.domain.Nickname;
import com.example.springboot_jpa.user.domain.User;
import com.example.springboot_jpa.user.service.UserService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties({OAuthGoogleProperties.class, OAuthKakaoProperties.class})
public class OAuthCommonService {

	private final OAuthGoogleProperties googleProperties;

	private final OAuthKakaoProperties kakaoProperties;

	private final UserService userService;


	public User createTempUser(OAuthProvider provider) {
		// 1. 랜덤 닉네임 생성, 중복 확인
		String tmpNickname = NicknameGenerator.generateRandom(provider);
		while (userService.existsByNickname(new Nickname(tmpNickname))) {
			tmpNickname = NicknameGenerator.generateRandom(provider);
		}

		// 2. 임시 유저 생성
		Nickname nickname = new Nickname(tmpNickname);
		User user = User.create(nickname);
		userService.save(user);

		return user;
	}

	public String getAccessToken(OAuthProvider provider, String code) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// 액세스 토큰 요청 파라미터 설정
		String body, tokenUrl;
		switch (provider) {
			case GOOGLE:
				body = googleProperties.getBody(code);
				tokenUrl = googleProperties.tokenUrl();
				break;
			case KAKAO:
				body = kakaoProperties.getBody(code);
				tokenUrl = kakaoProperties.tokenUrl();
				break;
			default:
				throw new SpringbootJpaException("올바르지 않은 OAuth 서비스 제공자입니다.");
		}

		HttpEntity<String> entity = new HttpEntity<>(body, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Map> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, entity, Map.class);

		return (String) response.getBody().get("access_token");
	}

	public Map<String, Object> getUserInfo(OAuthProvider provider, String accessToken) {
		// 사용자 정보를 요청할 url 설정
		String userInfoUrl;
		switch (provider) {
			case GOOGLE:
				userInfoUrl = googleProperties.userInfoUrl();
				break;
			case KAKAO:
				userInfoUrl = kakaoProperties.userInfoUrl();
				break;
			default:
				throw new SpringbootJpaException("올바르지 않은 OAuth 서비스 제공자입니다.");
		}

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		System.out.println("액세스 토큰 확인 중");
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Map> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, Map.class);
		System.out.println("액세스 토큰 확인 완료");

		return response.getBody();
	}

}
