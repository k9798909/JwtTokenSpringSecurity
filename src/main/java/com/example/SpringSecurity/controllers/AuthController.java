package com.example.SpringSecurity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringSecurity.model.Users;
import com.example.SpringSecurity.utils.JwtTokenUtils;

@RestController
public class AuthController {
	@Autowired
    private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginReq req) {
        try {
        	//用ProviderManager進行認證
            Authentication authenticate = authenticationManager
                .authenticate(
                    new UsernamePasswordAuthenticationToken(
                    		req.username(), req.password()
                    )
                );
            
            // principal 認證前:username 認證後:Users object、credentials 認證前:密碼 認證後:null、authorities 認証前:null，認証後:權限
            Users users = (Users) authenticate.getPrincipal();

            return ResponseEntity.ok()
                .header(
                    HttpHeaders.AUTHORIZATION,
                    JwtTokenUtils.generateToken(users.getUsername())
                )
                .body("成功");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
	}
	
	public record LoginReq(String username, String password) {
	}
	
	@GetMapping("/hello")
	public ResponseEntity<String> test() {
		return ResponseEntity.ok("hello");
	}
	
}
