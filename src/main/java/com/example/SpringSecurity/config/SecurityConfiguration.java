package com.example.SpringSecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.SpringSecurity.filter.JwtTokenFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	@Autowired
	UserDetailsService userDetailsService;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtTokenFilter jwtTokenFilter) throws Exception {
		http.cors(cors -> cors.disable())
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(authorize -> authorize
		            .requestMatchers("/login").permitAll()
		            .anyRequest().authenticated()
		    )
			.httpBasic(Customizer.withDefaults())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			 //UsernamePasswordAuthenticationFilter會在使用formlogin時註冊，沒有註冊也沒關係順序會在FilterOrderRegistration定義
			.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
			;
		return http.build();
	}
	
	
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
    	AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        //DaoAuthenticationProvider預設驗證帳號的方式，此段在設定DaoAuthenticationProvider驗證帳號用的service以及密碼編碼。
        authenticationManagerBuilder.userDetailsService(userDetailsService)
        							.passwordEncoder(passwordEncoder);
        //提供ProviderManager認證
        return authenticationManagerBuilder.build();
    }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public JwtTokenFilter jwtTokenFilter() {
		return new JwtTokenFilter();
	}

}
