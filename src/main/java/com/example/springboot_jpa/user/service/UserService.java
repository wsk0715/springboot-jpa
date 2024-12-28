package com.example.springboot_jpa.user.service;

import com.example.springboot_jpa.exception.SpringbootJpaException;
import com.example.springboot_jpa.user.domain.Nickname;
import com.example.springboot_jpa.user.domain.User;
import com.example.springboot_jpa.user.repository.UserRepository;
import com.example.springboot_jpa.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	private final JwtTokenUtil jwtTokenUtil;


	public void save(User user) {
		userRepository.save(user);
	}

	public boolean existsByNickname(Nickname nickname) {
		return userRepository.existsByNickname(nickname);
	}

	@Transactional
	public void update(String token, User user) {
		// 토큰을 통해 사용자 확인
		Long currentUserId = jwtTokenUtil.getUserId(token);
		User dbUser = userRepository.findById(currentUserId).orElse(null);

		if (dbUser == null) {
			throw new SpringbootJpaException("해당 사용자가 존재하지 않습니다.");
		}

		// 닉네임 변경
		Nickname nickname = user.getNickname();
		if (userRepository.existsByNickname(nickname)) {
			throw new SpringbootJpaException("이미 존재하는 닉네임입니다.");
		}

		dbUser.updateNickname(nickname);
	}

}
