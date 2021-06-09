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
import org.springframework.web.bind.annotation.RequestParam;
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
	public ModelAndView blogHome(HttpSession session, ModelAndView mv) {
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
	
	@RequestMapping(value = "/")
	public ModelAndView blogHome2(HttpSession session, ModelAndView mv) {
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
	
	// 21.05.19 블로그 메인
	// 21.06.04 블로그table에는 blogId, 방문자는 logNo, 방문자는 세션에 저장해서~~~, 
	@RequestMapping(value = "/{blogId}")
	public ModelAndView blogMain(HttpSession session, 
				     ModelAndView mv, 
				     @PathVariable("blogId")String blogId, 
				     @RequestParam(value="query", defaultValue="") String query, 
				     @RequestParam(value="currentPage", defaultValue="1") int currentPage) {
		// "myver/blog/"로 들어온 경우 블로그 홈으로 이동
		if(blogId.length() == 0) { 
			mv.setViewName("blog/home");
			return mv;
		}
		
		// 블로그 주인 회원 번호
		int member_no = memSVC.selectMember_noById(blogId);
		
		if(member_no == 0) { // member id가 존재하지 않는 경우 (혹은 member id가 관리자인 경우)
			System.out.println("member id==0");
			mv.setViewName("blog/home");
			return mv;
		}
		
		// 21.05.19 member_no로 블로그 정보 가져오기
		BlogDTO blogDTO = blogSVC.selectAllFromBlog(member_no);
		
		// 21.05.28 이미지 번호로 이미지 path, saved_name 세팅(이미지 번호 없는 경우에는 blank.png)
		if(blogDTO.getBlog_img_no() != 0) {
			imgSVC.selectPathAndSaved_nameFromImage(blogDTO);
		}else{
			//21.06.04 이미지 번호 없는 경우 처리~~
		}

		//방문자
		String visitor_id = (String)session.getAttribute("MID");
		int visitor_no = 0;
		
		// 방문자 회원 번호
		if(visitor_id != null) {
			visitor_no = memSVC.selectMember_noById(visitor_id);
		}
		
		// 21.06.03 방문자가 블로그 주인일 경우
		if(visitor_no != 0 && blogDTO.getMember_no()==visitor_no){
			System.out.println("주인이 방문했습니다.");
			
			// 21.05.23 카테고리 리스트 가져오기
			List<BlogDTO> categoryList = blogSVC.selectAllFromBlog_category(blogDTO.getBlog_no());
			
			for(BlogDTO category : categoryList) {
				// 대표 카테고리인 경우
				if(category.getIs_basic() == 1) {
					if(category.getAll_category()==1) { //전체 카테고리가 기본 카테고리인 경우
						System.out.println("전체 카테고리가 기본 카테고리임!");
						// 21.06.02 블로그 글 테이블에서 'blog_no'에 해당하는 개수 가져오기
						int blog_no = blogDTO.getBlog_no();
						int totalCount = blogSVC.selectTotalCountByNoFromBlog_object(blog_no, "blog_no", false);
						
						// 21.06.02 리스트, 게시글 페이지 정보 생성  PageUtil(nowPage,totalCount,lineCount,no,column_name,only_public)
						PageUtil listInfo = new PageUtil(currentPage,totalCount,category.getList_line(),blog_no,"blog_no",false);
						PageUtil pageInfo = new PageUtil(currentPage,totalCount,category.getObjects_per_page(),blog_no,"blog_no",false);
						
						// 21.06.02 목록 내용 가져오기
						List<BlogDTO> lists = blogSVC.selectListDetailByNoFromBlog_object(listInfo);
								
						// 21.06.02 게시글 내용 가져오기
						List<BlogDTO> objects = blogSVC.selectObjectDetailByNoFromBlog_object(pageInfo);
						
						mv.addObject("CATEGORY_NO", category.getBlog_category_no());
						mv.addObject("CATEGORY_NAME", category.getCategory_name());
						mv.addObject("CATEGORY_TOTAL", totalCount);
						mv.addObject("LIST", lists);
						mv.addObject("OBJECT", objects);
						
					}else {
						int blog_category_no = category.getBlog_category_no();
						// 21.05.27 블로그 글 테이블에서 'blog_category_no'에 해당하는 개수 가져오기
						int totalCount = blogSVC.selectTotalCountByNoFromBlog_object(blog_category_no, "blog_category_no", false);
						
						// 21.05.27 리스트, 게시글 페이지 정보 생성  PageUtil(nowPage,totalCount,lineCount,no,column_name,only_public)
						PageUtil listInfo = new PageUtil(currentPage,totalCount,category.getList_line(),blog_category_no,"blog_category_no",false);
						PageUtil pageInfo = new PageUtil(currentPage,totalCount,category.getObjects_per_page(),blog_category_no,"blog_category_no",false);
						
						// 21.05.27 목록 내용 가져오기
						List<BlogDTO> lists = blogSVC.selectListDetailByNoFromBlog_object(listInfo);
								
						// 21.05.27 게시글 내용 가져오기
						List<BlogDTO> objects = blogSVC.selectObjectDetailByNoFromBlog_object(pageInfo);
						
						mv.addObject("CATEGORY_NO", category.getBlog_category_no());
						mv.addObject("CATEGORY_NAME", category.getCategory_name());
						mv.addObject("CATEGORY_TOTAL", totalCount);
						mv.addObject("LIST", lists);
						mv.addObject("OBJECT", objects);
					}
					
					break;
				}
			}
			
			mv.addObject("CATEGORY", categoryList);
		}else { // 21.06.030 방문자가 외부인일 경우
			System.out.println("외부인이 방문했습니다.");
			
			// 21.06.09 게시글 조회수 업데이트(증가)
			blogSVC.updateBlogObjectHits(blogDTO.getBlog_object_no(), session);
			// 21.06.09 블로그 방문자 정보 추가
			blogSVC.insertBlog_visit(blogDTO.getBlog_no(), blogDTO.getBlog_object_no(), visitor_no, query, session);
			
			if(visitor_no != 0) { // 외부 방문자가 로그인한 경우
				System.out.println("외부인이 로그인했습니다.");
				
			}else { // 외부 방문자가 로그인하지 않은 경우
				System.out.println("외부인이 로그인하지 않았습니다.");
			}
			
			// 21.06.02 공개된 카테고리 리스트 가져오기
			List<BlogDTO> categoryList = blogSVC.selectPublicFromBlog_category(blogDTO.getBlog_no());
			
			for(BlogDTO category : categoryList) {
				// 대표 카테고리인 경우
				if(category.getIs_basic() == 1) {
					if(category.getAll_category()==1) { //전체 카테고리가 기본 카테고리인 경우
						System.out.println("전체 카테고리가 기본 카테고리임!");
						// 21.06.02 블로그 글 테이블에서 'blog_no'에 해당하는 공개된 게시글 개수 가져오기
						int blog_no = blogDTO.getBlog_no();
						int totalCount = blogSVC.selectTotalCountByNoFromBlog_object(blog_no, "blog_no", true);
						
						// 21.06.02 리스트, 게시글 페이지 정보 생성  PageUtil(nowPage,totalCount,lineCount,no,column_name,only_public)
						PageUtil listInfo = new PageUtil(currentPage,totalCount,category.getList_line(),blog_no,"blog_no",true);
						PageUtil pageInfo = new PageUtil(currentPage,totalCount,category.getObjects_per_page(),blog_no,"blog_no",true);
						
						// 21.06.02 공개된 목록 내용 가져오기
						List<BlogDTO> lists = blogSVC.selectListDetailByNoFromBlog_object(listInfo);
								
						// 21.06.02 공개된 게시글 내용 가져오기
						List<BlogDTO> objects = blogSVC.selectObjectDetailByNoFromBlog_object(pageInfo);
						
						mv.addObject("CATEGORY_NO", category.getBlog_category_no());
						mv.addObject("CATEGORY_NAME", category.getCategory_name());
						mv.addObject("CATEGORY_TOTAL", totalCount);
						mv.addObject("LIST", lists);
						mv.addObject("OBJECT", objects);
						
					}else {
						int blog_category_no = category.getBlog_category_no();
						// 21.05.27 블로그 글 테이블에서 'blog_category_no'에 해당하는 개수 가져오기
						int totalCount = blogSVC.selectTotalCountByNoFromBlog_object(blog_category_no, "blog_category_no", true);
						
						// 21.05.27 리스트, 게시글 페이지 정보 생성  PageUtil(nowPage,totalCount,lineCount,no,column_name,only_public)
						PageUtil listInfo = new PageUtil(currentPage,totalCount,category.getList_line(),blog_category_no,"blog_category_no",true);
						PageUtil pageInfo = new PageUtil(currentPage,totalCount,category.getObjects_per_page(),blog_category_no,"blog_category_no",true);
						
						// 21.05.27 목록 내용 가져오기
						List<BlogDTO> lists = blogSVC.selectListDetailByNoFromBlog_object(listInfo);
								
						// 21.05.27 게시글 내용 가져오기
						List<BlogDTO> objects = blogSVC.selectObjectDetailByNoFromBlog_object(pageInfo);
						
						mv.addObject("CATEGORY_NO", category.getBlog_category_no());
						mv.addObject("CATEGORY_NAME", category.getCategory_name());
						mv.addObject("CATEGORY_TOTAL", totalCount);
						mv.addObject("LIST", lists);
						mv.addObject("OBJECT", objects);
					}
					
					break;
				}
			}
			
			mv.addObject("CATEGORY", categoryList);
		}

		
		
		
		// 21.05.24 이웃 리스트 가져오기 (내가 추가한 이웃 following / 나를 추가한 이웃 follower)
		List<BlogDTO> followingList = blogSVC.selectFollowingListFromBlog_neighbor(member_no);
		List<BlogDTO> followerList = blogSVC.selectFollowerListFromBlog_neighbor(member_no);
		
		mv.addObject("BLOG", blogDTO);
		mv.addObject("FOLLOWING", followingList);
		mv.addObject("FOLLOWER", followerList);
		
		mv.setViewName("blog/main");
		
		return mv;
	}
    // 21.06.05 글 번호에 해당하는 내용 가져오기. 글에 해당하는 카테고리 정보 가져와서 목록/글 페이지 정보 만들고 리스트 가져오기 && 방문자 처리
    // 21.05.30 블로그 글 보기	
    @RequestMapping(value = "/{blogId}/{object_no}")	
    public ModelAndView blogObject(HttpSession session, 
				   ModelAndView mv, 
				   @PathVariable("blogId") String blogId, 
				   @PathVariable("object_no") int object_no, 
				   @RequestParam(value="query", defaultValue="") String query) {	
	if(blogId.length() == 0) {
		mv.setViewName("blog/home");
		return mv;
	}
	if(object_no == 0) { 
		mv.setViewName("blog/home");
		return mv;
	}
	
	// 블로그 주인 회원 번호
	int member_no = memSVC.selectMember_noById(blogId);
	// member_no로 블로그 정보 가져오기
	BlogDTO blogDTO = blogSVC.selectAllFromBlog(member_no);
	
	// 21.06.04 이웃 리스트 가져오기 (내가 추가한 이웃 following / 나를 추가한 이웃 follower)
	List<BlogDTO> followingList = blogSVC.selectFollowingListFromBlog_neighbor(member_no);
	List<BlogDTO> followerList = blogSVC.selectFollowerListFromBlog_neighbor(member_no);

	mv.addObject("BLOG", blogDTO);
	mv.addObject("FOLLOWING", followingList);
	mv.addObject("FOLLOWER", followerList);
	mv.setViewName("blog/object");
	
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
