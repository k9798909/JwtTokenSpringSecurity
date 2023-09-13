package com.example.SpringSecurity;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.SpringSecurity.model.Users;
import com.example.SpringSecurity.repository.UsersRepository;

@SpringBootApplication
public class SpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner start(UsersRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			Users users = new Users();
			users.setUsername("test");
			users.setPassword(passwordEncoder.encode("test"));
			userRepository.save(users);
		};
	}

}
