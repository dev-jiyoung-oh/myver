package com.project.myver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.project.myver.dto.MemberDTO;
import com.project.myver.service.MemoService;

@Controller
@RequestMapping("/memo")
public class MemoController {
	
	@Autowired
	private MemoService memoSVC;
	
	// 21.04.26 메일 리스트
	@RequestMapping(value = "/list")
	public ModelAndView memoList(MemberDTO memdto, ModelAndView mv) {
		//String id = memSVC.findIdByPhone(memdto.getPhone());
		
		//mv.addObject("ID",id);
		
		mv.setViewName("/member/memo/list");
		
		return mv;
	}
	
	// 21.04.26 메일 리스트
		@RequestMapping(value = "/write")
		public ModelAndView memoWrite(ModelAndView mv) {
			//String id = memSVC.findIdByPhone(memdto.getPhone());
			
			//mv.addObject("ID",id);
			
			mv.setViewName("member/memo/write");
			
			return mv;
		}
}
