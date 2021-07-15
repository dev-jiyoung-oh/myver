package com.project.myver.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.myver.dto.MemberDTO;

@Repository
public class MemberDAO extends SqlSessionDaoSupport {
	
	@Autowired
	SqlSessionTemplate session;

	// 21.04.18 회원가입
	public int join(MemberDTO memDTO) {
		int i = session.insert("member.join", memDTO);
		
		if(i==0) {
			System.out.println(memDTO.getUsername()+" - 회원가입 실패");
		}else {
			System.out.println(memDTO.getUsername()+" - 회원가입 성공");
		}
		
		return memDTO.getMember_no();
	}
	
	// 21.04.21 아이디 존재 확인
	public int getIdCnt(String id) {
		int result = session.selectOne("member.getIdCnt", id);
		
		if(result==1) {
			System.out.println("MemberDAO.getIdCnt() - " + id+" 유저 존재함");
		}else {
			System.out.println("MemberDAO.getIdCnt() - " + id+" 유저 " + result + "명");
		}
		
		return result;
	}
	
	// 21.04.17 최근 접속일 갱신
	public void updateLog_date(String id) {
		int i = session.update("member.updateLog_date", id);
		
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
			System.out.println("MemberDAO.getById - 해당 아이디 없음.");
		}else {
			System.out.println("MemberDAO.getById - " + memdto.toString());
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

	// 21.05.03 회원 아이디로 회원 번호 가져오기
	public int selectMember_noById(String id) {
		int member_no = session.selectOne("member.selectMember_noById", id);
		return member_no;
	}


}
