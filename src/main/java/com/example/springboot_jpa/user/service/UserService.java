package com.example.springboot_jpa.user.service;

import com.example.springboot_jpa.exception.SpringbootJpaException;
import com.example.springboot_jpa.user.domain.Nickname;
import com.example.springboot_jpa.user.domain.User;
import com.example.springboot_jpa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;


	public void save(User user) {
		userRepository.save(user);
	}

	public User findById(Long userId) {
		return userRepository.findById(userId)
							 .orElseThrow(() -> new SpringbootJpaException("해당 사용자가 존재하지 않습니다."));
	}

	public boolean existsByNickname(Nickname nickname) {
		return userRepository.existsByNickname(nickname);
	}

	@Transactional
	public void update(User user, User loginUser) {
		// 사용자 확인
		Long loginUserId = loginUser.getId();
		User dbUser = findById(loginUserId);

		// 닉네임 변경
		Nickname nickname = user.getNickname();
		if (userRepository.existsByNickname(nickname)) {
			throw new SpringbootJpaException("이미 존재하는 닉네임입니다.");
		}

		dbUser.updateNickname(nickname);
	}

	@Transactional
	public void delete(User user) {
		// 사용자 확인
		Long loginUserId = user.getId();
		findById(loginUserId);

		// 사용자 제거
		userRepository.deleteById(loginUserId);
	}

}
