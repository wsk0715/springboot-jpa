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

	public boolean existsByNickname(Nickname nickname) {
		return userRepository.existsByNickname(nickname);
	}

	@Transactional
	public void updateNickname(Long userId, String nickname) {
		User user = userRepository.findById(userId).orElse(null);

		if (user == null) {
			throw new SpringbootJpaException("해당 사용자가 존재하지 않습니다.");
		}
		
		if (userRepository.existsByNickname(new Nickname(nickname))) {
			throw new SpringbootJpaException("이미 존재하는 닉네임입니다.");
		}

		user.updateNickname(nickname);
	}

}
