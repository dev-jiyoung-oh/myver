package com.project.myver.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.myver.dto.MemberDTO;

public class MemberDAO extends SqlSessionDaoSupport {
	
	@Autowired
	SqlSessionTemplate session;

	// 21.04.18 회원가입
	public void join(MemberDTO memdto) {
		int i = session.insert("member.join", memdto);
		
		if(i==0) {
			System.out.println(memdto.getUsername()+" - 회원가입 실패");
		}else {
			System.out.println(memdto.getUsername()+" - 회원가입 성공");
		}
	}
	
	// 21.04.21 아이디 존재 확인
	public int getIDCnt(String id) {
		int result = session.selectOne("member.getIDCnt", id);
		
		if(result==1) {
			System.out.println("MemberDAO.getIDCnt() - " + id+" 유저 존재함");
		}else {
			System.out.println("MemberDAO.getIDCnt() - " + id+" 유저 " + result + "명");
		}
		
		return result;
	}
	
	// 21.04.17 최근 접속일 갱신
	public void logDate(String id) {
		int i = session.update("member.logDate", id);
		
		if(i==0) {
			System.out.println(id+" - 최근 접속일 갱신 실패");
		}else {
			System.out.println(id+" - 최근 접속일 갱신 성공");
		}
	}

	// 21.01.16 아이디로 회원 정보 가져오기
	public MemberDTO getById(String id) {
		MemberDTO memdto = session.selectOne("member.getById", id);
		
		if(memdto==null) { 
			System.out.println("MemberDAO.getbyId - 해당 아이디 없음.");
		}else {
			System.out.println("MemberDAO.getbyId - " + memdto.toString());
		}
		return memdto;
	}
	
	// 21.04.18 전화번호로 아이디 찾기
	public String findIdByPhone(int phone) {
		String id = session.selectOne("member.getIdByPhone", phone);
		return id;
	}

	// 21.04.25 비밀번호 변경
	public int changePw(MemberDTO memDTO) {
		int i = session.update("member.changePw", memDTO);
		return i;
	}


}
