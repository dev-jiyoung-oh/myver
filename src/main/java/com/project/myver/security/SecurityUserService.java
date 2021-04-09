package com.project.myver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.myver.dao.MemberDAO;
import com.project.myver.dto.MemberDTO;

/* 21.01.16
 * 참고사이트 : https://jungeunlee95.github.io/java/2019/07/17/2-Spring-Security/
 * 
 * 21.02.08
 * 참고사이트 : https://velog.io/@hellas4/2019-11-12-0811-%EC%9E%91%EC%84%B1%EB%90%A8
 */

/* DB에서 유저 정보를 가져오는 역할을 한다.
 * UserDetailsService 인터페이스에서 DB에서 유저정보를 불러오는 중요한 메소드는 loadUserByUsername() 메소드.
 * loadUserByUsername()메소드에서 CustomUserDetails형(내 경우 MemberDTO)으로 사용자의 정보를 받아오면 된다. */
public class SecurityUserService implements UserDetailsService {

	@Autowired
	private MemberDAO memDAO;
	
	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		System.out.println("SecurityUserService.loadUserByUsername - id="+id);
		MemberDTO memDTO = memDAO.getById(id);
		
		if(memDTO == null) {
			System.out.println("SecurityUserService.loadUserByUsername - memDTO==null");
			throw new UsernameNotFoundException(id);
		}else {
			System.out.println("SecurityUserService.loadUserByUsername - "+memDTO.toString());
		}
		
		// 여기서 return된 user는 SecurityContext의 Authentication에 등록되어 인증 정보를 갖고 있는다
		return memDTO; 
	}

}
