package com.example.springboot_jpa.user.service;

import com.example.springboot_jpa.user.domain.Nickname;
import com.example.springboot_jpa.user.domain.User;
import com.example.springboot_jpa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
