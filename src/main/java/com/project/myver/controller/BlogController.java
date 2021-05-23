package com.project.myver.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.project.myver.dto.BlogDTO;
import com.project.myver.dto.FileDTO;
import com.project.myver.dto.MemberDTO;
import com.project.myver.dto.MemoDTO;
import com.project.myver.service.FileService;
import com.project.myver.service.MemberService;
import com.project.myver.service.BlogService;

@Controller
@RequestMapping("/blog")
public class BlogController {
	
	@Autowired
	private BlogService blogSVC;
	@Autowired
	private MemberService memSVC;
	@Autowired
	private FileService fileSVC;
	
	// 21.05.17 블로그 홈_메인 페이지
	@RequestMapping(value = "/home")
	public ModelAndView home_main(HttpSession session, ModelAndView mv) {
		/* main.jsp에 보낼 정보
		 * 1. 블로그 정보 'blog'table - blog_no, nick, blog_img_no
		 * 2. blog_img_no로 'image'table에서 path, saved_name 가져오기
		 * 3. 오늘 방문자수 'blog_visit'table
		 * 4. 내 소식 리스트
		 * 5. 내가 남긴 글 리스트
		 * 6. 이웃 목록 리스트
		 * 7. 이웃 새글 리스트
		 * 8. 인기글 리스트 가져오기
		 */
		
		if(session.getAttribute("MID")!=null) {
			int member_no = memSVC.selectMember_noById((String)session.getAttribute("MID"));
			
			// 1. 블로그 정보 'blog'table - blog_no, nick, blog_img_no
			BlogDTO blogDTO = blogSVC.selectBlogHomeDataFromBlog(member_no);
			// 2. blog_img_no로 'image'table에서 path, saved_name 가져오기
			
			// 3. 오늘 방문자수 'blog_visit'table
			int today_visit_cnt = blogSVC.todayBlogVisitCount(blogDTO.getBlog_no());
			
			
			mv.addObject("BLOG", blogDTO);
			mv.addObject("TODAYVISIT", today_visit_cnt);
		}
		mv.setViewName("blog/home");
		
		return mv;
	}
	
	// 21.05.19 블로그 (str: id)
	@RequestMapping(value = "/{str}")
	public ModelAndView blog(HttpSession session, ModelAndView mv, @PathVariable("str")String str) {
		// "myver/blog/"로 들어온 경우 블로그 홈으로 이동
		if(str.length()==0) { 
			mv.setViewName("blog/home");
			return mv;
		}
		
		/* 1. str(id)->member_no로 블로그 정보 가져오기
		 * 2. 이미지 번호로 이미지 path, saved_name 가져오기
		 * 3. 카테고리 리스트 가져오기
		 * 4. 이웃 리스트 가져오기
		 * 5. 블로그 글 리스트 가져오기
		 */
		int member_no = memSVC.selectMember_noById(str);
		
		if(member_no == 0) { // member id가 존재하지 않는 경우 (혹은 member id가 관리자인 경우)
			System.out.println("member id==0");
			mv.setViewName("blog/home");
			return mv;
		}
		
		// 21.05.19 member_no로 블로그 정보 가져오기
		BlogDTO blogDTO = blogSVC.selectAllFromBlog(member_no);
		
		// 21.05.23 카테고리 리스트 가져오기
		ArrayList<BlogDTO> categoryList = blogSVC.selectAllFromBlog_category(blogDTO.getBlog_no());
		
		// 이웃 리스트 가져오기
		
		// 블로그 글 리스트 가져오기
		
		mv.addObject("BLOG", blogDTO);
		mv.addObject("str", str);
		mv.setViewName("blog/main");
		return mv;
	}
	/*
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public ModelAndView memoList(HttpSession session, ModelAndView mv, RedirectView rv) {
		//String id = memSVC.findIdByPhone(memdto.getPhone());
		String session_id = (String)session.getAttribute("MID");
		mv.addObject("MEMOLIST", my_memo_list);
		mv.setViewName("member/memo/list");
		
		return mv;
	}
	public ModelAndView memoWriteFrm(ModelAndView mv) {
		//String id = memSVC.findIdByPhone(memdto.getPhone());
		
		//mv.addObject("ID",id);
		
		mv.setViewName("member/memo/write");
		
		return mv;
	}
	
	@RequestMapping(value = "/getContent", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Map<String,Object> selectContentByMemo_no(int mn) {
		return map;
	}
	*/
}
