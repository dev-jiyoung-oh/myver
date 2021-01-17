package com.project.myver.security;

import java.util.Arrays;

/* 2021.01.16
 * 
 * 참고사이트 : https://jungeunlee95.github.io/java/2019/07/17/2-Spring-Security/
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.project.myver.dao.MemberDAO;
import com.project.myver.dto.MemberDTO;

@Repository
public class SecurityUserService implements UserDetailsService {

	@Autowired
	private MemberDAO memDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		MemberDTO memDTO = memDAO.getById(username);
		
		SecurityUser securityUser = new SecurityUser();
		
		if(memDTO != null) {
			securityUser.setNo(memDTO.getMemeber_no());
			securityUser.setId(memDTO.getId());
			securityUser.setPw(memDTO.getPw());
			securityUser.setAuthorities(Arrays.asList(new SimpleGrantedAuthority(memDTO.getAuth())));
		}
		
		// 여기서 return된 SecurityUser는 SecurityContext의 Authentication에 등록되어 인증 정보를 갖고 있는다
		return securityUser; 
	}

}
