package com.project.myver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.project.myver.dto.MemberDTO;

/* 21.02.08 생성
 * 참고사이트 : https://velog.io/@hellas4/2019-11-12-0911-%EC%9E%91%EC%84%B1%EB%90%A8
 */

/* AuthenticationProvider : form에서 입력한 로그인 정보와 DB에서 가져온(SecurityUserService)사용자의 정보를 비교해주는 인터페이스.
 * authenticate() 메서드를 오버라이딩 하게 되는데, 사용자가 form에서 입력한 로그인정보를 담고 있는 Authentication 객체를 가지고 있음. */
public class SecurityAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private SecurityUserService securityUserSVC;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String id = (String)authentication.getPrincipal();
		String pw = (String)authentication.getCredentials();
		
		MemberDTO user = (MemberDTO)securityUserSVC.loadUserByUsername(id);
		
		if(!passwordEncoder.matches(pw,user.getPassword())) {
			System.out.println("SecurityAuthenticationProvider.authenticate - 비밀번호 틀림~");
			throw new BadCredentialsException(id);
		}
		
		//return new UsernamePasswordAuthenticationToken(id, pw, user.getAuthorities());
		System.out.println(user.getAuthorities().toString());
		return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
