package com.example.springboot_jpa.oauth.common.service;

import com.example.springboot_jpa.exception.SpringbootJpaException;
import com.example.springboot_jpa.oauth.constants.OAuthProvider;
import com.example.springboot_jpa.user.domain.Nickname;
import com.example.springboot_jpa.user.domain.User;
import com.example.springboot_jpa.user.service.UserService;
import com.example.springboot_jpa.util.NicknameGenerator;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OAuthCommonService {

	@Value("${security.oauth.google.clientId}")
	private String GOOGLE_CLIENT_ID;

	@Value("${security.oauth.google.clientSecret}")
	private String GOOGLE_CLIENT_SECRET;

	@Value("${security.oauth.google.redirectUri}")
	private String GOOGLE_REDIRECT_URI;

	@Value("${security.oauth.google.tokenUrl}")
	private String GOOGLE_TOKEN_URL;

	@Value("${security.oauth.google.userInfoUrl}")
	private String GOOGLE_USERINFO_URL;

	@Value("${security.oauth.kakao.clientId}")
	private String KAKAO_CLIENT_ID;

	@Value("${security.oauth.kakao.redirectUri}")
	private String KAKAO_REDIRECT_URI;

	@Value("${security.oauth.kakao.tokenUrl}")
	private String KAKAO_TOKEN_URL;

	@Value("${security.oauth.kakao.userInfoUrl}")
	private String KAKAO_USERINFO_URL;

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
				body = "code=" + code +
					   "&client_id=" + GOOGLE_CLIENT_ID +
					   "&client_secret=" + GOOGLE_CLIENT_SECRET +
					   "&redirect_uri=" + GOOGLE_REDIRECT_URI +
					   "&grant_type=authorization_code";
				tokenUrl = GOOGLE_TOKEN_URL;
				break;
			case KAKAO:
				body = "code=" + code +
					   "&client_id=" + KAKAO_CLIENT_ID +
					   "&redirect_uri=" + KAKAO_REDIRECT_URI +
					   "&grant_type=authorization_code";
				tokenUrl = KAKAO_TOKEN_URL;
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
				userInfoUrl = GOOGLE_USERINFO_URL;
				break;
			case KAKAO:
				userInfoUrl = KAKAO_USERINFO_URL;
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
