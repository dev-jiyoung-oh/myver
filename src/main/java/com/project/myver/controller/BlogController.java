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
import com.project.myver.service.ImageService;
import com.project.myver.service.MemberService;
import com.project.myver.util.PageUtil;
import com.project.myver.service.BlogService;

@Controller
@RequestMapping("/blog")
public class BlogController {
	
	@Autowired
	private BlogService blogSVC;
	@Autowired
	private MemberService memSVC;
	@Autowired
	private ImageService imgSVC;
	
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
	@RequestMapping(value = "/{id}")
	public ModelAndView blog(HttpSession session, ModelAndView mv, @PathVariable("id")String id) {
		// "myver/blog/"로 들어온 경우 블로그 홈으로 이동
		if(id.length() == 0) { 
			mv.setViewName("blog/home");
			return mv;
		}
		// 방문자 회원 번호
		int visitor_no = memSVC.selectMember_noById((String)session.getAttribute("MID"));
		
		
		/* 1. str(id)->member_no로 블로그 정보 가져오기
		 * 2. 이미지 번호로 이미지 path, saved_name 가져오기
		 * 3. 카테고리 리스트 가져오기
		 * 4. 이웃 리스트 가져오기
		 * 5. 블로그 글 리스트 가져오기
		 */
		int member_no = memSVC.selectMember_noById(id);
		
		if(member_no == 0) { // member id가 존재하지 않는 경우 (혹은 member id가 관리자인 경우)
			System.out.println("member id==0");
			mv.setViewName("blog/home");
			return mv;
		}
		
		// 21.05.19 member_no로 블로그 정보 가져오기
		BlogDTO blogDTO = blogSVC.selectAllFromBlog(member_no);
		
		// 21.05.28 이미지 번호로 이미지 path, saved_name 세팅(이미지 번호 없는 경우에는 건너뛰기)
		if(blogDTO.getBlog_img_no() != 0) {
			imgSVC.selectPathAndSaved_nameFromImage(blogDTO);
		}

		// 방문자가 블로그 주인일 경우
		if(blogDTO.getMember_no()==visitor_no){
		// 전체 카테고리 등 가져오기
		}else { // 방문자가 외부인일 경우
		// visitor 테이블에 데이터 삽입 + 비밀이 아닌 경우의 카테고리,글,댓글 등 가져오기 + 방문 카운트++ + 어떤글을 봤는지 어떻게 기록할래?
		}

		// 21.05.23 카테고리 리스트 가져오기
		List<BlogDTO> categoryList = blogSVC.selectAllFromBlog_category(blogDTO.getBlog_no());
		
		// 블로그 글 리스트 가져오기 ---두 가지 경우 생각해야 함(블로그 주인/그 외)
		/* 1. 대표 카테고리로 설정된 카테고리의 글 상세히 가져오기 
		 *    - 카테고리 설정에서 '페이지당 글 개수' 설정한 개수만큼... PageUtil 객체 생성 및 그거에 맞는 값 가져오기
		 *    - 해당 카테고리의 모든 글 개수에서 '페이지당 글 개수'로 나눈만큼 1,2,3,4... 버튼 생성
		 * 2. 대표 카테고리의 글 목록 가져오기...글제목, 댓글수, 조회수, 날짜 
		 *    - 카테고리 설정에서 '목록 개수' 설정한 만큼...
		 *    - 해당 카테고리의 모든 글 개수에서 '목록개수'로 나눈만큼 1,2,3,4,5... 버튼 생성
		 */
		// 블로그 주인인 경우~~
		for(BlogDTO category : categoryList) {
			// 대표 카테고리인 경우
			if(category.getIs_basic() == 1) {
				int blog_category_no = category.getBlog_category_no();
				// 21.05.27 블로그 글 테이블에서 'blog_category_no'에 해당하는 개수 가져오기
				int totalCount = blogSVC.selectTotalCountFromBlog_object(blog_category_no);
				
				// 21.05.27 리스트, 게시글 페이지 정보 생성  PageUtil(nowPage,totalCount,lineCount,blog_cateogry_no)
				PageUtil listInfo = new PageUtil(1,totalCount,category.getList_line(),blog_category_no);
				PageUtil pageInfo = new PageUtil(1,totalCount,category.getObjects_per_page(),blog_category_no);
				
				// 21.05.27 목록 내용 가져오기
				List<BlogDTO> lists = blogSVC.selectListDetailFromBlog_object(listInfo);
						
				// 21.05.27 게시글 내용 가져오기
				List<BlogDTO> objects = blogSVC.selectObjectDetailFromBlog_object(pageInfo);
				
				mv.addObject("CATEGORY_NO", category.getBlog_category_no());
				mv.addObject("CATEGORY_NAME", category.getCategory_name());
				mv.addObject("LIST", lists);
				mv.addObject("OBJECT", objects);
				
				break;
			}
		}
		
		
		// 21.05.24 이웃 리스트 가져오기 (내가 추가한 이웃 following / 나를 추가한 이웃 follower)
		List<BlogDTO> followingList = blogSVC.selectFollowingListFromBlog_neighbor(member_no);
		List<BlogDTO> followerList = blogSVC.selectFollowerListFromBlog_neighbor(member_no);
		
		
		mv.addObject("BLOG", blogDTO);
		mv.addObject("CATEGORY", categoryList);
		mv.addObject("FOLLOWING", followingList);
		mv.addObject("FOLLOWER", followerList);
		
		mv.setViewName("blog/main");
		
		return mv;
	}

        // 21.05.30 블로그 글 보기	
        @RequestMapping(value = "/{id}/{object_no}")	
        public ModelAndView blogObject(HttpSession session, ModelAndView mv, @PathVariable("id") String id, PathVariable("object_no") int object_no) {	
		if(id.length() == 0) { 
			mv.setViewName("blog/home");
			return mv;
		}
		if(object_no == 0) { 
			mv.setViewName("blog/home");
			return mv;
		}
		// mv.addObject("FOLLOWER", followerList);
		
		// mv.setViewName("blog/object");
		
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
