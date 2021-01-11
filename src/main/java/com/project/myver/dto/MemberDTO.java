package com.project.myver.dto;

import java.sql.Date;
//import java.time.LocalDate;
// 에러내용, 400에러 
/*400에러, 클라이언트 오류로서 인지된 어떤 문제로 인하여, 서버가 해당 요청을 처리할 수 없거나, 처리하지 않을 것입니다. 
(예: 잘못된 요청 문법, 유효하지 않은 요청 메시지 framing, 또는 신뢰할 수 없는 요청 라우팅).*/
//새로 알아낸 내용-> post방식은 String타입을 제외한 자료형은 null을 허용하지 않음
//sql.Date만 오라클과 호환된다.
//다른 Date와 관련한 자료형은 post방식으로 허용되지않음
public class MemberDTO {
	private int memeber_no;	//회원번호
	private String id;		//아이디
	private String pw;		//비밀번호
	private String name;	//이름
	private String nick;	//닉네임
	private int phone;		//전화번호
	private Date date;		//가입일
	private Date log_date;	//최근접속일
	private String auth;	//권한 : 일반 0, 관리자 1 
	
	public int getMemeber_no() {
		return memeber_no;
	}
	public void setMemeber_no(int memeber_no) {
		this.memeber_no = memeber_no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
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
	
	@Override
	public String toString() {
		return "MemberDTO [memeber_no=" + memeber_no + ", id=" + id + ", pw=" + pw + ", name=" + name + ", nick=" + nick
				+ ", phone=" + phone + ", date=" + date + ", log_date=" + log_date + ", auth=" + auth + "]";
	}
	
	
	
}
