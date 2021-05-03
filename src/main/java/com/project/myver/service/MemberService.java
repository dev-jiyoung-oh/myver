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
	public void join(MemberDTO memDTO) {
		memDTO.setPw(encode(memDTO.getPassword()));
		memDAO.join(memDTO);
	}
	
	// 21.04.21 아이디 존재 확인
	public int getIDCnt(String id) {
		return memDAO.getIDCnt(id);
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
	
	// 21.04.25 비밀번호 변경
	public int changePw(MemberDTO memDTO) {
		memDTO.setPw(encode(memDTO.getPassword()));
		int i = memDAO.changePw(memDTO);
		return i;
	}

	// 21.05.03 회원 아이디로 회원 번호 가져오기
	public int selectNoById(String id) {
		int member_no = memDAO.selectNoById(id);
		return member_no;
	}
}