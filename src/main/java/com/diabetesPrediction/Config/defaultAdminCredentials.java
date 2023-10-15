package com.diabetesPrediction.Config;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.diabetesPrediction.Model.User;
import com.diabetesPrediction.Repository.UserRepo;
import jakarta.annotation.PostConstruct;

@Component
public class defaultAdminCredentials {

	private final UserRepo userRepository;
	private final PasswordEncoder passwordEncoder;

	public defaultAdminCredentials(UserRepo userRepository, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@PostConstruct
	public void initDefaultAdmin() {
		User defaultUser = new User();
		defaultUser.setId(1);
		defaultUser.setName("DocFinder");
		defaultUser.setEmail("admin@gmail.com");
		defaultUser.setState("-");
		defaultUser.setMobile("-");
		defaultUser.setPassword(passwordEncoder.encode("password"));
		defaultUser.setRole("ROLE_ADMIN");
		defaultUser.setCreateYear("-");
		userRepository.save(defaultUser);
	}
}
