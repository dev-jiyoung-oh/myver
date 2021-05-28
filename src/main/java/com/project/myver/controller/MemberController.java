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
import com.project.myver.service.BlogService;
import com.project.myver.service.MemberService;

@Controller
public class MemberController { //extends SimpleUrlAuthenticationSuccessHandler
	@Autowired
	private MemberService memSVC;
	@Autowired
	private BlogService blogSVC;
	
	// 21.04.17 회원가입 폼
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join() {
		return "common/join/join";
	}
	
	// 21.04.18 회원가입
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public ModelAndView join(MemberDTO memDTO, ModelAndView mv) {
		System.out.println("join - " + memDTO.toString());
		int member_no = memSVC.join(memDTO);

		// 21.05.19 해당 아이디의 블로그 생성
		int blog_no = blogSVC.insertBlog(member_no, memDTO.getNick());
		
		// 21.05.28 블로그 기본 카테고리('전체보기') 생성
		blogSVC.insertDefaultBlogCategory(blog_no);
		
		RedirectView rv = new RedirectView("./joinSuccess");
		mv.setView(rv);
		return mv;
	}
	
	// 21.02.12 로그인폼
	@RequestMapping(value = "/login")
	public String loginPage() throws Exception{
		return "common/login";
	}
	
	/* 아이디 중복확인
	@ResponseBody
	@RequestMapping("/idChk")
	public String idChk(MemberDTO memdto) {
		String data=null;
		int isUsed = memSVC.getIDCnt(memdto);
		
		if (isUsed == 0) {
			data="success";
		} else if(isUsed == 1) {
			data="fail";
		} else {
			System.out.println("동일한 아이디가 여러개 존재합니다. id="+memdto.getUsername()+", cnt="+isUsed);
			data = "fail";
		}
		return data;
	}*/
	
	// 21.04.18 전화번호로 아이디 찾기 폼
	@RequestMapping(value = "/findId", method = RequestMethod.GET)
	public String findId() {
		return "common/find/findIdFrm";
	}
	
	// 21.04.18 전화번호로 아이디 찾기
	@RequestMapping(value = "/findId", method = RequestMethod.POST)
	public ModelAndView findId(MemberDTO memdto, ModelAndView mv) {
		String id = memSVC.findIdByPhone(memdto.getPhone());
		
		mv.addObject("ID",id);
		
		mv.setViewName("common/find/findId_success");
		
		return mv;
	}
	
	// 21.04.19 비밀번호 찾기 
	/* 1. 아이디 입력 폼
	 * 2. 아이디 검색 -> 비동기통신 (성공: 3으로 / 실패: alert)
	 * 3. 본인인증(전화번호 입력) 폼 -> 
	 * 4. 본인 인증 확인 -> 비동기통신 (성공: 4로 / 실패: alert)
	 * 5. 비밀번호 재설정 폼
	 * 6. 비밀번호 재설정 -> (성공: 완료 페이지 / 실패: 에러 페이지)
	 */
	// 21.04.19 비밀번호 찾기 - 1. 아이디 입력 폼
	@RequestMapping(value = "/findPw", method = RequestMethod.GET)
	public String findPwFrm() {
		return "common/find/findPwFrm";
	}
	
	// 21.04.21 비밀번호 찾기 - 2. 아이디 검색 -> 비동기통신 (성공: 3으로 / 실패: alert)
	@RequestMapping(value = "/findPw", method = RequestMethod.POST)
	@ResponseBody
	public String findPw(MemberDTO memDTO) {
		System.out.println("findPw-id:"+memDTO.getUsername());
		
		String result = "";
		int cnt = memSVC.getIdCnt(memDTO.getUsername());
		
		if(cnt==1) {
			result = "success";
		}else {
			result += cnt;
		}
		
		return result;
	}
	
	// 21.04.19 비밀번호 찾기 - 3. 본인인증(전화번호 입력) 폼
	@RequestMapping(value = "/findPwAuthFrm", method = RequestMethod.POST)
	public ModelAndView findPwAuth(MemberDTO memDTO, ModelAndView mv) {
		System.out.println("findPwAuthFrm-id:" + memDTO.getUsername());
		mv.addObject("ID", memDTO.getUsername());
		
		mv.setViewName("common/find/findPwFrm_auth");
		
		return mv;
	}
	
	// 21.04.22 비밀번호 찾기 - 4. 본인 인증 확인 -> 비동기통신 (성공: 4로 / 실패: alert)
	@RequestMapping(value = "/findPwAuth", method = RequestMethod.POST)
	@ResponseBody
	public String findPwAuth(MemberDTO memDTO) {
		System.out.println("findPwAuth-id:"+memDTO.getUsername());
		
		String result = "";
		String id = memSVC.findIdByPhone(memDTO.getPhone());
		
		if(memDTO.getUsername().equals(id)) {
			result = "success";
		}else {
			result = "fail";
		}
		
		return result;
	}
	
	// 21.04.20 비밀번호 찾기 - 5. 비밀번호 재설정 폼
	@RequestMapping(value = "/findPwChangeFrm", method = RequestMethod.POST)
	public ModelAndView findPwChangeFrm(MemberDTO memDTO, ModelAndView mv) {
		System.out.println("findPwChangeFrm-id:" + memDTO.getUsername());
		mv.addObject("ID", memDTO.getUsername());
		
		mv.setViewName("common/find/findPwFrm_change");
		
		return mv;
	}
	
	// 21.04.20 비밀번호 찾기 - 6. 비밀번호 재설정 -> (성공: 완료 페이지 / 실패: 에러 페이지)
	@RequestMapping(value = "/findPwChange", method = RequestMethod.POST)
	public ModelAndView findPwChange(MemberDTO memDTO, ModelAndView mv) {
		int i = memSVC.changePw(memDTO);
		
		if(i==1) {
			System.out.println(memDTO.getUsername()+" 비밀번호 변경 성공");
			mv.setViewName("common/find/findPw_change_success");
		}else {
			System.out.println(memDTO.getUsername()+" 비밀번호 변경 실패");
			mv.setViewName("common/find/findPw_change_fail");
		}
		
		return mv;
	}
	
	
//	@Override 
//	public void onAuthenticationSuccess(
//			HttpServletRequest request, HttpServletResponse response, 
//			Authentication auth) throws IOException, ServletException { 
//		request.getSession().setMaxInactiveInterval(60*60); //세셔타입아웃 수정 //1시간 
//		memSVC.login(((MemberDTO) auth.getPrincipal())); 
//		super.onAuthenticationSuccess(request, response, auth); 
//	}

}