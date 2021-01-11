package com.project.myver.service;

import javax.servlet.http.HttpSession;

//import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.myver.dao.MemberDAO;
import com.project.myver.dto.MemberDTO;

public class MemberService {
	@Autowired
	private MemberDAO memDAO;
	
	//일반로그인
	public MemberDTO login(MemberDTO memdto, HttpSession session, int cnt) {
		System.out.println("MemberService");
		MemberDTO result = memDAO.login(memdto);
			//날짜
//			java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
			if(result==null) {
				//로그인실패
				cnt=cnt+1;
				session.setAttribute("mcnt", cnt);
				System.out.println(cnt + "번 째 로그인 실패");
			}else{
				//로그인성공
				System.out.println("로그인 성공");
				session.setAttribute("MNO",result.getMemeber_no());
				session.setAttribute("MID",result.getId());
				session.setAttribute("MPW", result.getPw());
				session.setAttribute("MNAME", result.getName());
				session.setAttribute("MNICK", result.getNick());
				session.setAttribute("MPHONE", result.getPhone());
				session.setAttribute("MDATE", result.getDate());
				session.setAttribute("MLOGDATE", result.getLog_date());
				session.setAttribute("MAUTH", result.getAuth());
				memDAO.logDate(result);
		}
			return result;
	}
	
	//로그아웃
	public void logout(HttpSession session) {
		if(session.getAttribute("MID")!=null) {
			System.out.println(session.getAttribute("MID") + " logout");
			session.invalidate();
		}else {
			System.out.println("null logout");
		}
	}

	// 회원가입
	public void join(MemberDTO memdto) {
		memDAO.join(memdto);
	}
	
	//아이디 중복확인
	public int getMemberID(MemberDTO memdto) {
		return memDAO.getMemberID(memdto);
	}
		


}