package com.project.myver.service;

//import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.myver.dao.MemberDAO;
import com.project.myver.dto.MemberDTO;

@Service
public class MemberService {
	
	@Autowired
	private MemberDAO memDAO;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	// 21.04.18 암호화
	public String encode(String pw) {
		return passwordEncoder.encode(pw);
	}
	
	// 21.04.18 회원가입
	public void join(MemberDTO memdto) {
		memdto.setPw(encode(memdto.getPassword()));
		memDAO.join(memdto);
	}
	
	//아이디 중복확인
	public int getMemberID(MemberDTO memdto) {
		return memDAO.getMemberID(memdto);
	}
		
	// 21.04.17 최근 접속일 갱신
	public void logDate(String id) {
		memDAO.logDate(id);
	}
	
	// 21.04.18 전화번호로 아이디 찾기
	public String findIdByPhone(int phone) {
		String id = memDAO.findIdByPhone(phone);
		return id;
	}
}