package com.example.SpringSecurity.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.SpringSecurity.model.Users;

public interface UsersRepository extends CrudRepository<Users, Long> {
	List<Users> findByUsername(String username);
}
