package com.cos.security1.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	// 구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("userRequest : " + userRequest);
		System.out.println("getClientRegistration() : " + userRequest.getClientRegistration());
		System.out.println("getTokenValue : " + userRequest.getAccessToken().getTokenValue());
		
		OAuth2User oauth2User = super.loadUser(userRequest);
		System.out.println("getAuthorities : " + oauth2User.getAttributes());
		
		// 강제로 회원가입을 진행
		String provider = userRequest.getClientRegistration().getRegistrationId();	// google
		String providerId = oauth2User.getAttribute("sub");					// google에서 제공하는 id
		String username = provider + "_" + providerId;							// username은 google_googleID 
		String password = bCryptPasswordEncoder.encode("구글로그인");
		String email = oauth2User.getAttribute("email");
		String role = "ROLE_USER";
		
		User userEntity = userRepository.findByUsername(username);
		
		if(userEntity == null) {
			userEntity = User.builder()
							.username(username)
							.password(password)
							.email(email)
							.role(role)
							.provider(provider)
							.providerId(providerId)
							.build();
			userRepository.save(userEntity);
		}
		
		return new PrincipalDetails(userEntity, oauth2User.getAttributes());
	}
}
