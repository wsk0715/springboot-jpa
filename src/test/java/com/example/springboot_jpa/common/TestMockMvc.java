package com.example.springboot_jpa.common;

import com.example.springboot_jpa.auth.annotation.LoginUser;
import com.example.springboot_jpa.user.domain.User;
import org.mockito.Mockito;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class TestMockMvc {

	/**
	 * authService 호출 없이 미리 정의된 사용자 정보를 resolve 하도록 모킹한다.<br>
	 * 컨트롤러 테스트 시 @LoginUser 어노테이션이 붙은 파라미터를 삽입하는 ArgumentResolver를 모킹하여 올바른 인자가 전달되도록 한다.
	 *
	 * @param controller
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public static MockMvc setupMockMvcWithAuthenticatedUser(Object controller, User user) throws Exception {
		// 컨트롤러에 인자를 전달하는 ArgumentResolver 모킹
		HandlerMethodArgumentResolver loginUserArgumentResolver = Mockito.mock(HandlerMethodArgumentResolver.class);

		// supportsParameter 호출 동작 정의: 파라미터의 어노테이션, 타입 확인
		Mockito.when(loginUserArgumentResolver.supportsParameter(Mockito.any(MethodParameter.class)))  // 1. supportsParameter 메서드 호출
			   .thenAnswer(invocation -> {  // 2. supportsParameter 호출 결과 invocation에 대해
				   MethodParameter parameter = invocation.getArgument(0);  // parameter = invocation(supportsParameter)의 0번째 인자 = Mockito.any(MethodParameter.class)

				   // 3. 파라미터에 @LoginUser 어노테이션이 붙어 있고, 타입이 User인지 확인, 결과를 반환
				   return parameter.hasParameterAnnotation(LoginUser.class)  // 3-1. parameter가 @LoginUser인지 확인
						  && parameter.getParameterType().equals(User.class);  // 3-2. parameter가 User 타입인지 확인
			   });

		// resolveArgument 호출 동작 정의: 파라미터로 전달받은 user를 반환하도록 설정
		Mockito.when(loginUserArgumentResolver.resolveArgument(
					   Mockito.any(MethodParameter.class),
					   Mockito.any(ModelAndViewContainer.class),
					   Mockito.any(NativeWebRequest.class),
					   Mockito.any(WebDataBinderFactory.class)))  // 1. resolveArgument 메서드 호출
			   .thenAnswer(invocation -> user);  // 2. resolveArgument 메서드 호출 결과인 invocation을 user 인스턴스에 할당

		// 모킹된 user를 controller에 모킹
		return MockMvcBuilders.standaloneSetup(controller)
							  .setCustomArgumentResolvers(loginUserArgumentResolver)
							  .build();
	}

}
