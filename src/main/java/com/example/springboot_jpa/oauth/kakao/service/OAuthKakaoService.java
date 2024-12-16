package com.example.springboot_jpa.oauth.kakao.service;

import static com.example.springboot_jpa.util.NicknameGenerator.generateRandomNickname;

import com.example.springboot_jpa.oauth.kakao.domain.OAuthKakao;
import com.example.springboot_jpa.oauth.kakao.repository.OAuthKakaoRepository;
import com.example.springboot_jpa.user.domain.Nickname;
import com.example.springboot_jpa.user.domain.User;
import com.example.springboot_jpa.user.service.UserService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OAuthKakaoService {

	@Value("${security.oauth.kakao.clientId}")
	private String KAKAO_CLIENT_ID;

	@Value("${security.oauth.kakao.redirectUri}")
	private String KAKAO_REDIRECT_URI;

	@Value("${security.oauth.kakao.tokenUrl}")
	private String KAKAO_TOKEN_URL;

	@Value("${security.oauth.kakao.userInfoUrl}")
	private String KAKAO_USERINFO_URL;

	private final UserService userService;

	private final OAuthKakaoRepository oAuthKakaoRepository;


	@Transactional
	public boolean init(String code) {
		String accessToken = getAccessToken(code);
		String kakaoId = String.valueOf(getUserInfo(accessToken).get("id"));  // id - 카카오 고유 식별자

		// 소셜 로그인 정보 존재하지 않을 시
		if (!oAuthKakaoRepository.existsByCode(kakaoId)) {
			// 1. 임시 사용자 생성
			User user = createTempUser();
			Long userId = user.getId();  // 임시 사용자 id

			// 2. 임시 사용자와 카카오 id 식별값 연결
			OAuthKakao oauth = OAuthKakao.create(userId, kakaoId);
			oAuthKakaoRepository.save(oauth);

			return false;
		}

		return true;
	}

	public String getAccessToken(String code) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// 액세스 토큰 요청 파라미터 설정
		String body = "code=" + code +
					  "&client_id=" + KAKAO_CLIENT_ID +
					  "&redirect_uri=" + KAKAO_REDIRECT_URI +
					  "&grant_type=authorization_code";

		HttpEntity<String> entity = new HttpEntity<>(body, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Map> response = restTemplate.exchange(KAKAO_TOKEN_URL, HttpMethod.POST, entity, Map.class);

		return (String) response.getBody().get("access_token");
	}

	public Map<String, Object> getUserInfo(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		System.out.println("액세스 토큰 확인 중");
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Map> response = restTemplate.exchange(KAKAO_USERINFO_URL, HttpMethod.GET, entity, Map.class);
		System.out.println("액세스 토큰 확인 완료");

		return response.getBody();
	}

	private User createTempUser() {
		// 1. 랜덤 닉네임 생성, 중복 확인
		String tmpNickname = generateRandomNickname("kakao");
		while (userService.existsByNickname(new Nickname(tmpNickname))) {
			tmpNickname = generateRandomNickname("kakao");
		}

		// 2. 임시 유저 생성
		User user = User.create(tmpNickname);
		userService.save(user);

		return user;
	}

}
