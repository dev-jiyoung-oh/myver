package com.project.myver.service;

import javax.servlet.http.HttpSession;

//import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.myver.dao.MemberDAO;
import com.project.myver.dto.MemberDTO;

@Service
public class MemberService {
	@Autowired
	private MemberDAO memDAO;
	
	//private PasswordEncoder passwordEncoder;
	
//	//일반로그인
//	public MemberDTO login(MemberDTO memdto, HttpSession session, int cnt) {
//		System.out.println("로그인");
//		memDAO.logDate(memdto);
//	
//		return result;
//	}
	
	//로그아웃
	public void logout(HttpSession session) {
		if(session.getAttribute("MID")!=null) {
			System.out.println(session.getAttribute("MID") + " logout");
			session.invalidate();
		}else {
			System.out.println("null logout");
		}
	}

	// 회원가입. 2021.01.14
//	public void join(MemberDTO memdto) {
//		memdto.setPw(passwordEncoder.encode(memdto.getPw()));
//		memDAO.join(memdto);
//	}
	
	//아이디 중복확인
	public int getMemberID(MemberDTO memdto) {
		return memDAO.getMemberID(memdto);
	}

	// 아이디
	public void login(MemberDTO memberDTO) {
		// TODO Auto-generated method stub
		
	}
		


}