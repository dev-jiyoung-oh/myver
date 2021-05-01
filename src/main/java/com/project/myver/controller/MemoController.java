package com.project.myver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.project.myver.dto.MemberDTO;
import com.project.myver.dto.MemoDTO;
import com.project.myver.service.MemoService;

@Controller
@RequestMapping("/memo")
public class MemoController {
	
	@Autowired
	private MemoService memoSVC;
	
	// 21.04.26 쪽지 리스트
	@RequestMapping(value = "/list")
	public ModelAndView memoList(MemberDTO memdto, ModelAndView mv) {
		//String id = memSVC.findIdByPhone(memdto.getPhone());
		
		//mv.addObject("ID",id);
		
		mv.setViewName("/member/memo/list");
		
		return mv;
	}
	
	// 21.04.29 쪽지 작성 폼
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public ModelAndView memoWriteFrm(ModelAndView mv) {
		//String id = memSVC.findIdByPhone(memdto.getPhone());
		
		//mv.addObject("ID",id);
		
		mv.setViewName("member/memo/write");
		
		return mv;
	}
	
	// 21.05.01 쪽지 작성
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public ModelAndView memoWrite(
				ModelAndView mv,
				MultipartHttpServletRequest mtfRequest,
				MemoDTO memoDTO) {
		
		String path = "D:\\jy_project\\myver\\workspace\\upload\\";
		List<MultipartFile> fileList = mtfRequest.getFiles("file");
		System.out.println("length : " + memoDTO.getContent().getBytes().length);
		System.out.println(memoDTO.toString());
		
        for (MultipartFile mf : fileList) {
            String originFileName = mf.getOriginalFilename(); // 원본 파일명
            long fileSize = mf.getSize(); // 파일 사이즈
            
            System.out.println("originFileName : " + originFileName);
            System.out.println("fileSize : " + fileSize);
        }


		
		//mv.addObject("ID",id);
		
		mv.setViewName("member/memo/write");
		
		return mv;
	}
}
