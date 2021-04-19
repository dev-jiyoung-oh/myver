package com.project.myver.controller;
/*https://sjh836.tistory.com/165 [빨간색코딩]*/

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import javax.inject.Inject;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.project.myver.dto.MemberDTO;
import com.project.myver.service.MemberService;

@Controller
public class MemberController { //extends SimpleUrlAuthenticationSuccessHandler
	@Autowired
	private MemberService memSVC;

	// 21.04.17 회원가입 폼
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join() {
		return "/join.noHead";
	}
	
	// 21.04.18 회원가입
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public ModelAndView join(MemberDTO memdto, ModelAndView mv) {
		System.out.println("join - " + memdto.toString());
		memSVC.join(memdto);
		RedirectView rv = new RedirectView("./joinSuccess");
		mv.setView(rv);
		return mv;
	}
	
	// 21.02.12 로그인폼
	@RequestMapping(value = "/login")
	public String loginPage() throws Exception{
		return "/login.noHead";
	}
	
	// 아이디 중복확인
	@ResponseBody
	@RequestMapping("/idChk")
	public String idChk(MemberDTO memdto) {
		String data=null;
		int isUsed = memSVC.getMemberID(memdto);
		
		if (isUsed == 0) {
			data="success";
		} else if(isUsed == 1) {
			data="fail";
		} else {
			System.out.println("동일한 아이디가 여러개 존재합니다. id="+memdto.getUsername()+", cnt="+isUsed);
			data = "fail";
		}
		return data;
	}
	
	// 21.04.18 전화번호로 아이디 찾기 폼
	@RequestMapping(value = "/findId", method = RequestMethod.GET)
	public String findId() {
		return "/common/find/findIdFrm";
	}
	
	// 21.04.18 전화번호로 아이디 찾기
	@RequestMapping(value = "/findId", method = RequestMethod.POST)
	public ModelAndView findId(MemberDTO memdto, ModelAndView mv, HttpSession session) {
		String id = memSVC.findIdByPhone(memdto.getPhone());
		
		session.setAttribute("ID", id);
		
		mv.setViewName("/common/find/foundId");
		
		return mv;
	}
	
	// 21.04.19 비밀번호 찾기 
	/* 1. 아이디 입력 폼
	 * 2. 아이디 검색 기능 -> 비동기통신 (성공: 3으로 / 실패: alert)
	 * 3. 본인인증(전화번호 입력) 폼 -> 비동기통신 (성공: 4로 / 실패: alert)
	 * 4. 비밀번호 재설정 폼
	 * --> 로그인 폼으로
	 */
	
//	@Override 
//	public void onAuthenticationSuccess(
//			HttpServletRequest request, HttpServletResponse response, 
//			Authentication auth) throws IOException, ServletException { 
//		request.getSession().setMaxInactiveInterval(60*60); //세셔타입아웃 수정 //1시간 
//		memSVC.login(((MemberDTO) auth.getPrincipal())); 
//		super.onAuthenticationSuccess(request, response, auth); 
//	}

}