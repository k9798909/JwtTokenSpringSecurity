package com.example.SpringSecurity.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.SpringSecurity.repository.UsersRepository;

@Service
public class UsersService implements UserDetailsService {
	@Autowired
	UsersRepository usersRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return usersRepository
				.findByUsername(username)
				.stream().findAny()
				.orElseThrow(() -> new UsernameNotFoundException("username " + username + " is not found"));
	}
}
