package com.project.myver.dao;

import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.myver.dto.MemberDTO;
import com.project.myver.security.SecurityUser;

public class MemberDAO extends SqlSessionDaoSupport {
	@Autowired
	SqlSessionTemplate session;

	/* 21.01.12 로그인
	public MemberDTO login(MemberDTO memdto) {
		System.out.println("id:" + memdto.getId() + ", pw:" + memdto.getPw());
		MemberDTO result = session.selectOne("member.login", memdto);
		return result;
	}
	*/
	// 21.01.12 회원가입
	public void join(MemberDTO memdto) {
		System.out.println("MemberDAO - join");
		session.insert("member.join", memdto);
	}
	
	// 21.01.12 아이디 중복확인
	public int getMemberID(MemberDTO memdto) {
		return session.selectOne("member.getMemberID", memdto);
	}
	
	// 21.01.12 최근 접속일 갱신
	public void logDate(MemberDTO result) {
		// 오류해결 java.math.BigDecimal cannot be cast to java.lang.Integer
		// 1. int mno =Integer.parseInt(String.valueOf(result.get("MNO"))); -(SQL.xml에서 int로 받을 시)
		// 2. session.update("member.logDate", result.get("MNO")); -(SQL.xml에서 HashMap으로 받을 시)
		session.update("member.logDate", result.getMemeber_no());
	}

	// 21.01.16 아이디로 회원 정보 가져오기
	public MemberDTO getById(String id) {
		MemberDTO memdto = session.selectOne("member.getById", id);
		return memdto;
	}


}
