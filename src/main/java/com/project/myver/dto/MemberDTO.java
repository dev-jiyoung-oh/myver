package com.project.myver.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/* UserDetails 인터페이스 : Security에서 사용자의 정보를 담는 인터페이스
 * 이 인터페이스를 구현하게 되면 이제 Security 인증, 인가 관련 작업에서 우리가 구현한 인터페이스를 기준으로 작업할 수 있음. */
public class MemberDTO implements UserDetails{
	
	// security fields
	private int member_no;	// 회원번호
	private String id;		// 아이디
	private String pw;		// 비밀번호
	private String name;	// 이름
	private String nick;	// 닉네임
	private int phone;		// 전화번호
	private Date date;		// 가입일
	private Date log_date;	// 최근접속일
	private String auth;	// 권한 : 일반 "MEMBER" , 관리자 "ADMIN" 
	
	
	
	// 기본적인 오버라이딩 메소드들 오버라이딩
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(auth));
        return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getUsername() {
		return id;
	}
	
	@Override
	public String getPassword() {
		return pw;
	}

	
	// 추가적인 회원 정보 컬럼의 getter, setter
	public String getId() {
		return id;
	}

	public String getPw() {
		return pw;
	}

	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}

	public int getMember_no() {
		return member_no;
	}

	public void setMemeber_no(int member_no) {
		this.member_no = member_no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getLog_date() {
		return log_date;
	}

	public void setLog_date(Date log_date) {
		this.log_date = log_date;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	
	
	// toString 메소드
	@Override
	public String toString() {
		return "MemberDTO [member_no=" + member_no + ", id=" + id + ", pw=" + pw + ", name=" + name + ", nick=" + nick
				+ ", phone=" + phone + ", date=" + date + ", log_date=" + log_date + ", auth=" + auth + "]";
	}
	
}
