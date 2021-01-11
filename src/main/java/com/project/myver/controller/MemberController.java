package com.project.myver.controller;

//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.inject.Inject;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.project.myver.dto.MemberDTO;
import com.project.myver.service.MemberService;

@Controller
public class MemberController {
	@Autowired
	private MemberService memSVC;

	//메인화면에서 회원가입폼 이동
	@RequestMapping("/join")
	public String join() {
		return "/member/join/joinFrm";
	}
	
	//회원가입
	@RequestMapping("/joinTry")
	public ModelAndView join(MemberDTO memdto,ModelAndView mv) {
		memSVC.join(memdto);
		RedirectView rv=new RedirectView("./joinSuccess.com");
		mv.setView(rv);
		return mv;
	}
	
	//아이디 중복확인
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
			System.out.println("동일한 아이디가 여러개 존재합니다. id="+memdto.getId()+", cnt="+isUsed);
			data = "fail";
		}
		return data;
	}
}