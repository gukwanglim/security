package com.cos.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import com.cos.jwt.filter.MyFilter1;
import com.cos.jwt.filter.MyFilter3;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final CorsFilter corsFilter;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.addFilterBefore(new MyFilter3(), BasicAuthenticationFilter.class)
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilter(corsFilter)		// controller에서 @CrossOrigin을 사용하여 필터를 걸어줄 수 있지만, 인증이 필요 없는 경우에만 사용 가능하다. 
			.formLogin().disable()
			.httpBasic().disable()
			.authorizeRequests()
			.antMatchers("/api/v1/user/**")
			.access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
			.antMatchers("/api/v1/manager/**")
			.access(" hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
			.antMatchers("/api/v1/admin/**")
			.access(" hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll();
		
		return http.build(); 
	}

}
