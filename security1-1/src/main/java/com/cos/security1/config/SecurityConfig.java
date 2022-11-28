package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	// 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.(@Bean 사용 시)
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/user/**").authenticated()													// "/user/**"로 들어오게 되면 인증 필요
			.antMatchers("/manager/**").access("hasRole('RoLe_ADMIN') or hasRole('RoLe_MANAGER')")		// 'RoLe_ADMIN' 혹은 'RoLe_MANAGER'의 권한이 있는 사람만 들어올 수 있음
			.antMatchers("/admin/**").access("hasRole('RoLe_ADMIN')")
			.anyRequest().permitAll()
			.and()
			.formLogin()
			.loginPage("/loginForm");
			
		return http.build();
	}
}
