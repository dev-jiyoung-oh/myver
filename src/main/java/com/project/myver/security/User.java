package com.project.myver.security;

import com.project.myver.dto.MemberDTO;

public class User extends MemberDTO {
	private Account account;
	
	public User(MemberDTO memberDTO) {
		super(memberDTO.getUsername(), null, memberDTO.getAuthorities()));
		this.memberDTO = memberDTO;
	}
}
