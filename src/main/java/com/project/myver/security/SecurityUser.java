package com.project.myver.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUser implements UserDetails{
	
	// domain fields(principal: 보호할 사용자 중요 데이터)
	private int no;
	
	// security fields
	private Collection<? extends GrantedAuthority> authorities;
	private String id;  // credential
	private String pw;  // credential
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// ROLE
		return authorities;
	}

	@Override
	public String getPassword() {
		return pw;
	}

	@Override
	public String getUsername() {
		return id;
	}
	
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	// 계정에 대한 디테일한 설정 -----------------------------------
	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

}
