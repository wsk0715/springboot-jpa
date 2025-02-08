package com.example.springboot_jpa.oauth;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springboot_jpa.BaseSpringBootTest;
import com.example.springboot_jpa.auth.service.AuthService;
import com.example.springboot_jpa.oauth.properties.OAuthGoogleProperties;
import com.example.springboot_jpa.oauth.properties.OAuthKakaoProperties;
import com.example.springboot_jpa.oauth.service.OAuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@EnableConfigurationProperties(OAuthGoogleProperties.class)
class OAuthIntegrationTest extends BaseSpringBootTest {

	private final String providerGoogle = "google";
	private final String providerKakao = "kakao";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OAuthGoogleProperties oAuthGoogleProperties;

	@MockBean
	private OAuthKakaoProperties oAuthKakaoProperties;

	@MockBean
	private OAuthService oAuthService;

	@MockBean
	private AuthService authService;


	@Test
	@DisplayName("구글 로그인 요청에 대해 인증 URL이 담긴 응답을 반환한다.")
	void authenticationGoogle() throws Exception {
		// given
		String expectedUrl = oAuthGoogleProperties.getUrl();

		// when & then
		mockMvc.perform(get("/oauth/google"))
			   .andExpect(status().isOk())
			   .andExpect(jsonPath("$.*", hasItem(expectedUrl)));
	}

	@Test
	@DisplayName("구글 code를 입력하면 JWT 토큰을 반환한다.")
	void callbackGoogle() throws Exception {
	}

	@Test
	@DisplayName("카카오 로그인 요청에 대해 인증 URL이 담긴 응답을 반환한다.")
	void authenticationKakao() throws Exception {
		// given
		String expectedUrl = oAuthKakaoProperties.getUrl();

		// when & then
		mockMvc.perform(get("/oauth/kakao"))
			   .andExpect(status().isOk())
			   .andExpect(jsonPath("$.*", hasItem(expectedUrl)));
	}

	@Test
	@DisplayName("카카오 code를 입력하면 JWT 토큰을 반환한다.")
	void callbackKakao() throws Exception {
	}

}
