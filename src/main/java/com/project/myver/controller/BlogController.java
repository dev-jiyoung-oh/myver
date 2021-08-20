package com.project.myver.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.myver.dto.BlogDTO;
import com.project.myver.dto.CommentDTO;
import com.project.myver.dto.FileDTO;
import com.project.myver.dto.MemberDTO;
import com.project.myver.dto.MemoDTO;
import com.project.myver.service.ImageService;
import com.project.myver.service.MemberService;
import com.project.myver.util.PageUtil;
import com.project.myver.service.BlogService;

@Controller
public class BlogController {
	
	@Autowired
	private BlogService blogSVC;
	@Autowired
	private MemberService memSVC;
	@Autowired
	private ImageService imgSVC;
	
	// 21.05.17 블로그 홈_메인 페이지
	@RequestMapping(value = {"/blog/home", "/blog/", "/blog"})
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
			
			// 4. 내 소식 리스트
			// 5. 내가 남긴 글 리스트
			// 6. 이웃 목록 리스트
			// 7. 이웃 새글 리스트
			// 8. 인기글 리스트 가져오기
			mv.addObject("BLOG", blogDTO);
			mv.addObject("TODAYVISIT", today_visit_cnt);
		}
		
		mv.setViewName("blog/home");
		
		return mv;
	}
	
	// 21.05.19 블로그 메인
	// ★★★★★ 댓글, 좋아요 기능 추가해야함! ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
	@RequestMapping(value = "/blog/{blog_id}")
	public ModelAndView blogMain(
					ModelAndView mv, 
					HttpSession session,
					@AuthenticationPrincipal MemberDTO user,
				    @PathVariable("blog_id")String blog_id, 
				    @RequestParam(value="query", defaultValue="") String query, 
				    @RequestParam(value="currentPage", defaultValue="1") int currentPage,
				    @RequestParam(value="blog_category_no", defaultValue="-1") int blog_category_no) {
		
		// 블로그 주인 id count 가져오기
		int idCnt = memSVC.getIdCnt(blog_id);
		
		if(idCnt == 0) { // member id(blog_id)가 존재하지 않는 경우
			System.out.println("blog id가 존재하지 않음");
			mv.setViewName("blog/error/no_blog_error");
			return mv;
		}
		
		// 21.05.19 blog_id로 블로그 정보 가져오기
		BlogDTO blogDTO = blogSVC.selectAllFromBlog(blog_id);
		System.out.println(blogDTO.blogToString());
		
		// 21.05.24 블로그의 이웃 리스트 가져오기 (내가 추가한 이웃 following / 나를 추가한 이웃 follower)
		List<BlogDTO> followingList = blogSVC.selectFollowingListFromBlog_neighbor(blogDTO.getMember_no());
		List<BlogDTO> followerList = blogSVC.selectFollowerListFromBlog_neighbor(blogDTO.getMember_no());
		
		// 방문자 정보
		int visitor_no = (user == null)? -1 : user.getMember_no();
		String visitor_type = blogSVC.sortOutVisitor_type(blogDTO.getMember_no(), visitor_no, followingList);
		
		// 21.08.05 블로그 followerList(나를 추가한 이웃)중에 방문자가 포함되는지 확인
		boolean is_neighbor = blogSVC.checkMemberIsPartOfFollowerList(visitor_no, followerList);
		
		// 21.06.12  블로그 메인 - 카테고리 리스트, 목록 리스트, 글 리스트 가져오기
		Map<String,Object> map  = blogSVC.selectCategoryAndListAndObject(blogDTO, visitor_type, blog_category_no, currentPage);
		
		// 21.06.12 가져올 카테고리가 없는 경우, 해당하는 블로그 메인으로 이동
		if(map == null) {
			System.out.println("해당 카테고리를 방문하는데 오류가 발생했습니다.");
			mv.setViewName("blog/error/error_to_main");
			mv.addObject("BLOG", blogDTO);
			return mv;
		}
		
		mv.addObject("BLOG", blogDTO);
		mv.addObject("IS_NEIGHBOR", is_neighbor);
		mv.addObject("FOLLOWING", followingList);
		mv.addObject("FOLLOWER", followerList);
		mv.addObject("CATEGORY_LIST", (List)map.get("categoryList"));
		mv.addObject("CATEGORY", map.get("blog_category"));
		mv.addObject("CATEGORY_TOTAL", map.get("totalCount"));
		mv.addObject("LIST", (List)map.get("lists"));
		mv.addObject("OBJECTS", (List)map.get("objects"));

		mv.setViewName("blog/main");
		
		return mv;
	}
	
	// ★★★★★ 댓글, 좋아요 기능 추가해야함! ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
    // 21.05.30 블로그 글 보기	
    @RequestMapping(value = "/blog/{blog_id}/{blog_object_no}")	
    public ModelAndView blogObject(
    				HttpSession session, 
    				ModelAndView mv, 
    				@AuthenticationPrincipal MemberDTO user,
    				@PathVariable("blog_id") String blog_id, 
    				@PathVariable("blog_object_no") int blog_object_no, 
    				@RequestParam(value="query", defaultValue="") String query,
    				@RequestParam(value="blog_category_no", defaultValue="-1") int blog_category_no) {	
		
    	if(blog_id.length() == 0) {
			mv.setViewName("blog/home");
			return mv;
		}
		
    	// 블로그 주인 id count 가져오기
		int idCnt = memSVC.getIdCnt(blog_id);
		
		if(idCnt == 0) { // member id(blogId)가 존재하지 않는 경우
			System.out.println("blog id가 존재하지 않음");
			mv.setViewName("blog/error/no_blog_error");
			return mv;
		}
		
		// blog_id로 블로그 정보 가져오기
		BlogDTO blogDTO = blogSVC.selectAllFromBlog(blog_id);
		int blog_member_no = blogDTO.getMember_no();
		
		// 방문자 정보
		int visitor_no = (user == null)? -1 : user.getMember_no();
		
		// 21.06.04 이웃 리스트 가져오기 (내가 추가한 이웃 following / 나를 추가한 이웃 follower)
		List<BlogDTO> followingList = blogSVC.selectFollowingListFromBlog_neighbor(blog_member_no);
		List<BlogDTO> followerList = blogSVC.selectFollowerListFromBlog_neighbor(blog_member_no);
		
		// 방문자 종류(주인:"owner", 이웃:"neighbor", 비이웃:"other")
		String visitor_type = blogSVC.sortOutVisitor_type(blog_member_no, visitor_no, followingList);
		
		// 21.06.10 'blog_no'와 'blog_object_no'에 일치하는 'blog_object' 가져오기
		BlogDTO object = blogSVC.selectBlog_object(blogDTO.getBlog_no(), blog_object_no, visitor_type);
		
		// 해당 블로그에 해당 게시글이 존재하지 않는 경우
		if(object == null) {
			System.out.println("해당 블로그에 존재하지 않는 게시글입니다.");
			mv.setViewName("blog/error/no_blog_object_error");
			mv.addObject("BLOG", blogDTO);
			return mv;
		}
		
		// 21.06.14 블로그 카테고리, 목록, 글 가져오기
		Map<String,Object> map  = blogSVC.selectCategoryAndList(blogDTO, object, visitor_type, blog_category_no);
		
		// 가져올 카테고리가 없는 경우, 해당하는 블로그 메인으로 이동
		if(map == null) {
			System.out.println("해당 카테고리를 방문하는데 오류가 발생했습니다.");
			mv.setViewName("blog/error/error_to_main");
			mv.addObject("BLOG", blogDTO);
			return mv;
		}
		
		// 21.08.05 블로그 followerList(나를 추가한 이웃)중에 방문자가 포함되는지 확인
		boolean is_neighbor = blogSVC.checkMemberIsPartOfFollowerList(visitor_no, followerList);

		mv.addObject("BLOG", blogDTO);
		mv.addObject("FOLLOWING", followingList);
		mv.addObject("FOLLOWER", followerList);
		mv.addObject("IS_NEIGHBOR", is_neighbor);
		mv.addObject("CATEGORY_LIST", (List)map.get("categoryList"));
		mv.addObject("CATEGORY", map.get("blog_category"));
		mv.addObject("CATEGORY_TOTAL", map.get("totalCount"));
		mv.addObject("LIST", (List)map.get("lists"));
		mv.addObject("OBJECT", object);
		
		mv.setViewName("blog/object");
		
		return mv;
    }

    // 21.07.28 블로그 글 작성 폼
    @RequestMapping(value = "/blog/{blog_id}/write", method = RequestMethod.GET)	
    public ModelAndView blogWriteObjectForm(
    			ModelAndView mv,
    			@AuthenticationPrincipal MemberDTO user,
    			@PathVariable("blog_id") String blog_id) {
    	System.out.println("BlogController - blogWriteObjectForm()");
    	
    	if(user == null || !user.getUsername().equals(blog_id)) {
    		System.out.println("로그인 아이디 없음. 혹은 블로그 아이디와 현재 로그인된 아이디가 일치하지 않음.");
    		mv.setViewName("blog/error/no_blog_error");
			return mv;
    	}
    	
    	// blog_id로 블로그 정보 가져오기
		BlogDTO blogDTO = blogSVC.selectAllFromBlog(blog_id);
		
		// 블로그의 (모든) 카테고리 정보 리스트 가져오기
		List<BlogDTO> categoryList = blogSVC.selectAllFromBlog_category(blogDTO.getBlog_no());
		
		mv.addObject("BLOG_ID", blog_id);
		mv.addObject("CATEGORY_LIST", categoryList);
    	mv.setViewName("blog/write");
    	return mv;
    }
    
    // 21.07.30 블로그 글 작성
    @RequestMapping(value = "/blog/{blog_id}/write", method = RequestMethod.POST)	
    public ModelAndView blogWriteObject(
    			ModelAndView mv,
    			HttpServletRequest request,
    			@PathVariable("blog_id") String blog_id,
    			BlogDTO blog_object) {
    	System.out.println("BlogController - blogWriteObject() - " + blog_object.blog_objectToString());
    	
    	int blog_object_no = blogSVC.insertBlog_object(blog_object);
    	
    	if(blog_object_no == -1) {
    		mv.setViewName("blog/error/write_object_error");
    	}else {
    		RedirectView rv = new RedirectView(request.getContextPath()+"/blog/"+blog_id+"/"+blog_object_no);
    		mv.setView(rv);
    	}
    	
    	return mv;
    }
    
    // 21.08.05 이웃 설정(추가/삭제) 폼
    @RequestMapping(value = "/blog/neighborChange", method = RequestMethod.GET)	
    public ModelAndView blogNeighborChangeFrm(
				ModelAndView mv,
				HttpServletRequest request,
				@AuthenticationPrincipal MemberDTO user,
				@RequestParam(value="blog_id", required = true) String blog_id) {
    	System.out.println("blogNeighborChangeFrm - blog_id: "+blog_id);
    	if(user == null) {
    		System.out.println("blogNeighborChangeFrm - 로그인 정보 없음");
    		mv.setViewName("blog/error/?????????");
			return mv;
    	}
    	
    	// 21.08.06 blog_id로 회원번호, 닉네임 가져오기
    	BlogDTO blogDTO = blogSVC.selectMember_noAndBlog_nickByBlog_idFromBlog(blog_id);
    	
    	if(blogDTO == null) {
    		System.out.println("blogNeighborChangeFrm - 존재하지 않는 blog_id");
    		mv.setViewName("blog/error/no_blog_error.onlyBody");
			return mv;
    	}
    	
    	blogDTO.setBlog_id(blog_id);
    	
    	// 21.08.06 user가 해당 회원을 이웃으로 추가했는지 여부 가져오기
    	boolean is_neighbor = 
    			(blogSVC.selectCntByMember_noAndNeighborMember_noFromBlog_neighbor(user.getMember_no(), blogDTO.getMember_no()) == 0)?
    					false : true;
    	System.out.println("이웃 여부: "+is_neighbor);
    	mv.addObject("BLOG", blogDTO);
    	mv.addObject("IS_NEIGHBOR", is_neighbor);
    	mv.setViewName("blog/blog_neighbor_change.onlyBody");
    	return mv;
    }
    
    // 21.08.06 이웃 설정(추가/삭제)
    @RequestMapping(value = "/blog/neighborChange", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView blogNeighborChange(
    		ModelAndView mv,
    		@AuthenticationPrincipal MemberDTO user,
    		@RequestParam(value="add", required = true) boolean add,
    		@RequestParam(value="blog_id", required = true) String blog_id) {
    	System.out.println("blogNeighborChange - add: "+add+", blog_id: "+blog_id);
    	
    	// 블로그 주인 id count 가져오기
		int idCnt = memSVC.getIdCnt(blog_id);
		
		if(idCnt == 0) { // member id(blogId)가 존재하지 않는 경우
			System.out.println("blogNeighborChange - blog id가 존재하지 않음");
			mv.setViewName("blog/error/no_blog_error.onlyBody");
			return mv;
		}
    	
    	BlogDTO blogDTO = blogSVC.selectMember_noAndBlog_nickByBlog_idFromBlog(blog_id);
		int neighbor_member_no = memSVC.selectMember_noById(blog_id);
		int cnt = 0;
    	
		// 이웃 추가
    	if(add) {
    		cnt = blogSVC.insertBlog_neighbor(user.getMember_no(), neighbor_member_no);
		// 이웃 삭제
    	}else {
    		cnt = blogSVC.deleteBlog_neighbor(user.getMember_no(), neighbor_member_no);
    	}
    	
    	System.out.println("cnt : "+cnt);

    	mv.addObject("BLOG", blogDTO);
    	mv.addObject("ADD", add);
    	mv.setViewName("blog/blog_neighbor_change_success.onlyBody");
    	return mv;
    }
    
    // 21.08.16 이웃 삭제
    @RequestMapping(value = "/blog.admin/neighborDelete", method = RequestMethod.POST)
    @ResponseBody
    public List<Integer> blogNeighborDelete(
    		@AuthenticationPrincipal MemberDTO user,
    		@RequestParam(value="followings") int[] followings) {
    	System.out.println("blogNeighborDelete - followings: " + Arrays.toString(followings));
    	
    	List<Integer> successed_member_no_list = new ArrayList<>();

    	if(user == null) {
    		System.out.println("미로그인");
    		
    		successed_member_no_list.add(-1);
    		return successed_member_no_list;
    	}
    	
    	for(int neighbor_member_no : followings) {
    		int temp_cnt = blogSVC.deleteBlog_neighbor(user.getMember_no(), neighbor_member_no);
    		
    		if(temp_cnt == 1) {
    			successed_member_no_list.add(neighbor_member_no);
    		}
    	}
    	
    	return successed_member_no_list;
    }
    
    // 21.08.20 내 블로그 관리 페이지 - 기본설정(config) > 블로그 정보
    @RequestMapping(value = "/blog.admin/blogInfo.myver", method = RequestMethod.POST, produces = "application/json; charset=utf8")	
    @ResponseBody
    public String blogConfigBlogInfo(
				@AuthenticationPrincipal MemberDTO user,
				String blog_id) {
    	if(blog_id == null) System.out.println("blog_id == null");
    	if(user == null || blog_id == null || !user.getUsername().equals(blog_id)) {
    		System.out.println("blogConfigBlogInfo - 로그인 정보가 없거나 일치하지 않음.");
    		return "no_login";
    	}
    	
    	// 1) 블로그 정보 : 'blog_id'로 블로그 정보 가져오기
    	BlogDTO blogDTO = blogSVC.selectAllFromBlog(blog_id);
		
		try {
			return new ObjectMapper().writeValueAsString(blogDTO);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "error";
		}
    	
    }
    

    // 21.06.21 내 블로그 관리 페이지 - 기본설정(config) > 블로그 정보 수정
    @RequestMapping(value = "/blog.admin/blogInfoUpdate.myver", method = RequestMethod.POST)
	@ResponseBody
	public String updateBlog(BlogDTO blogDTO) {
		System.out.println("updateBlog - blogDTO: "+blogDTO.blogToString());
		String result = "";
		
		/* ★★★ 업로드한 이미지가 있는 경우 이미지 업로드...하고 번호 가져와서 blogDTO에 set ★★★ */
		//boolean imgCange = false;
		
		int cnt = blogSVC.blogUpdate(blogDTO);
		
		if(cnt==1) {
			System.out.println("updateBlog - 업데이트 성공");
			result = "success";
		}else {
			System.out.println("updateBlog - cnt: "+cnt);
			result = "fail";
		}
		
		return result;
	}
    
    // 21.06.15 내 블로그 관리 페이지 - 기본설정(config)
    @RequestMapping(value = {"/blog.admin/{blog_id}/config", "/blog.admin/{blog_id}/", "/blog.admin/{blog_id}"})	
    public ModelAndView blogConfig(
    			HttpServletRequest request,
				ModelAndView mv,
				@AuthenticationPrincipal MemberDTO user,
				@PathVariable("blog_id") String blog_id) {
    	
    	if(user == null || !user.getUsername().equals(blog_id)) {
    		System.out.println("blogConfig - loginRedirect: "+request.getRequestURI());
    		mv.addObject("loginRedirect", request.getRequestURI());
    		RedirectView rv = new RedirectView(request.getContextPath()+"/login");
    		mv.setView(rv);
    		return mv;
    	}
    	
    	// 방문자 id
    	String visitor_id = user.getUsername();
    	
    	/* config(기본 설정)
    	   1) 블로그 정보 : blog_id로 'blog' 정보 가져오기
    	   2) 내가 추가한 이웃 : 내가 추가한 이웃 리스트 가져오기
    	   3) 나를 추가한 이웃 : 나를 추가한 이웃 리스트 가져오기
    	   4) 블로그 초기화
    	 */
    	
    	// 1) 블로그 정보 : 'blog_id'로 블로그 정보 가져오기
    	BlogDTO blogDTO = blogSVC.selectAllFromBlog(blog_id);
    	
		int blog_member_no = blogDTO.getMember_no();
    	
		// 2) 내가 추가한 이웃 : 내가 추가한 이웃 리스트 가져오기
		List<BlogDTO> followingList = blogSVC.selectFollowingListFromBlog_neighbor(blog_member_no);
		
		// 3) 나를 추가한 이웃 : 나를 추가한 이웃 리스트 가져오기
		List<BlogDTO> followerList = blogSVC.selectFollowerListFromBlog_neighbor(blog_member_no);
		
		// 나를 추가한 이웃/내가 추가한 이웃 - 일치하는 이웃 확인
		for(BlogDTO following : followingList) {
			for(BlogDTO follower : followerList) {
				if(following.getNeighbor_member_no() == follower.getMember_no()) {
					System.out.println("서로 이웃 - "+following.getNeighbor_member_no());
					following.setIsBothNeighbor(true);
					follower.setIsBothNeighbor(true);
				}
			}
		}
		
		/* ★★★★★★★★★★★★★★★★★★★★★★★★★★★
		try {
			new ObjectMapper().writeValueAsString(blogDTO);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		mv.addObject("BLOG", blogDTO);
		mv.addObject("FOLLOWINGS", followingList);
		mv.addObject("FOLLOWERS", followerList);
		mv.setViewName("blog/admin/config");
    	
    	return mv;
    }
    
    // 21.06.19 내 블로그 관리 페이지 - 메뉴,글,동영상 관리(content)
    @RequestMapping(value = "/blog.admin/{blog_id}/content/{menu}")
    public ModelAndView blogContent(HttpSession session,  
			   	   HttpServletRequest request,
				   ModelAndView mv, 
				   @PathVariable("blog_id") String blog_id,
				   @PathVariable("menu") String menu) {
    	String visitor_id = (String)session.getAttribute("MID");
    	
    	if(visitor_id==null || !blog_id.equals(visitor_id)) {
    		System.out.println("blogConfig - loginRedirect: "+request.getRequestURI());
    		mv.addObject("loginRedirect", request.getRequestURI());
    		RedirectView rv = new RedirectView();
			rv.setUrl(request.getContextPath()+"/login");
			mv.setView(rv);
			return mv;
    	}
    	
    	/* content(메뉴,글 관리)
	 	   1) 상단 메뉴 설정 (/topmenu)
	 	   2) 카테고리 설정 (/category)
	 	   3) 게시글 관리 (/object)
	 	   4) 댓글 관리 (/comment)
    	 */
    	
    	// 블로그 정보 : 'blog_id'로 블로그 정보 가져오기
    	BlogDTO blogDTO = blogSVC.selectAllFromBlog(blog_id);
		int blog_no = blogDTO.getBlog_no();
 
		// 1) 상단 메뉴 설정 (topmenu)
    	if(menu.equals("topmenu")) {
    		// 21.06.29 'blog_no'에 해당하는 카테고리의 'blog_category_no','category_name','is_public','parent_category_no','is_upper' 가져오기
    		List<BlogDTO> categoryList = blogSVC.selectBlog_category_noAndCategory_nameAndIs_publicAndParent_category_noAndIs_upper(blog_no);
    		
    		System.out.println(categoryList);
    		
    		mv.addObject("CATEGORYS_FOR_UPPER", categoryList);
    		
		// 2) 카테고리 설정 (category)
    	}else if(menu.equals("category")) {
    		// (모든)카테고리 리스트 가져오기
    		List<BlogDTO> categoryList = blogSVC.selectAllFromBlog_category(blog_no);
     	    
    		mv.addObject("CATEGORYS", categoryList);

            for(BlogDTO category : categoryList) {
            	System.out.println(category.blog_categoryToString());
            }
            
        // 3) 게시글 관리 (object)
    	}else if(menu.equals("object")) {
    		// 모든 카테고리 이름과 카테고리 번호 가져오기
    		List<BlogDTO> categoryList = blogSVC.selectBlog_category_noAndCategory_name(blog_no);
    		
    		// (모든)글 목록 가져오기
    		// ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★ 
    		//  1 - 페이지에 따라 비동기로 가져오는거 처리해야함
    		//  2 - 아이디로 검색하는것도 비동기 처리해야함
    		int totalCount = blogSVC.selectTotalCountByNoFromBlog_object(blog_no, "blog_no", "owner");
    		PageUtil pageInfo = new PageUtil(1, totalCount, 20, blog_no, "blog_no", "owner");
    		List<BlogDTO> objectList = blogSVC.selectObjectDetailByNoFromBlog_object(pageInfo);
    		
    		mv.addObject("CATEGORYS_FOR_OBJECT", categoryList);
    		mv.addObject("OBJECTS", objectList);
    		
    		for(BlogDTO object : objectList) {
    			System.out.println(object.blog_objectToString());
    		}
    		
		// 4) 댓글 관리 (comment)
    	}else if(menu.equals("comment")) {
    		// 모든 	댓글 가져오기
    		List<CommentDTO> commentList = blogSVC.selectCommentByBlog_noFromBlog_comment(blog_no);
    		
    		mv.addObject("COMMENTS", commentList);
    		
    		for(CommentDTO category : commentList) {
    			System.out.println(category.toString());
    		}
    	}
    	
    	
    	mv.addObject("BLOG", blogDTO);
    	mv.setViewName("blog/admin/content");
    	
    	return mv;
    }
    
 // 21.06.15 내 블로그 통계 페이지
    @RequestMapping(value = "/blog.admin/{blog_id}/stat/{menu}")	
    public ModelAndView blogStat(HttpSession session, 
			       HttpServletRequest request, 
				   ModelAndView mv, 
				   RedirectView rv,
				   @PathVariable("blog_id") String blog_id,
				   @PathVariable("menu") String menu) {
    	String visitor_id = (String)session.getAttribute("MID");
    	
    	if(visitor_id==null || !blog_id.equals(visitor_id)) {
    		System.out.println("BlogController - blogStat() - loginRedirect: "+request.getRequestURI());
    		mv.addObject("loginRedirect", request.getRequestURI());
			rv.setUrl(request.getContextPath()+"/login");
			mv.setView(rv);
			return mv;
    	}
    	
    	BlogDTO blogDTO = blogSVC.selectAllFromBlog(blog_id);
		int blog_member_no = blogDTO.getMember_no();
		int blog_no = blogDTO.getBlog_no();
    	
		// 1) 오늘 (/today)
		// - 날짜 받아오기(없는 경우 오늘 날짜)
    	if(menu.equals("today")) {
    		// 21.07.03 1. 오늘의 블로그글 조회수
    		int todayHit = blogSVC.todayObjectHitFromBlog_visit(blog_no);
    		
    		// 21.07.03 2. 오늘의 블로그 댓글수
    		int todayCommentCount = blogSVC.todayCommentCount(blog_no);
    		
    		// 3. 총 좋아요수★★★★★★★★★★★★★★★★★★★
    		// 4. 총 이웃증감수★★★★★★★★★★★★★★★★★★★★★★★
    		
    		// 21.07.04 5. 오늘날짜+14일 전의 "조회수" 가져오기
    		List<BlogDTO> totalHitOfLast15Days = blogSVC.totalHitOfLast15DaysFromBlog_visit("", blog_no);
    		if(totalHitOfLast15Days == null) {
    			System.out.println("totalHitOfLast15Days == null ~~나중에 처리하기~~");
    		}
    		mv.addObject("HITS_OF_15DAYS", totalHitOfLast15Days);
    		
    		// 21.07.07 6. 게시물 조회수 순위(top5는 front-end 에서 처리)
    		List<BlogDTO> todayHitRank = blogSVC.hitRankDuringFromBlog_visitAndBlog_object("", "", blog_no, "daily"); //hitRankDuringFromBlog_visitAndBlog_object(String startDate_str, String endDate_str, int blog_no, String period)
    		if(todayHitRank == null) {
    			System.out.println("todayHitRank == null ~~나중에 처리하기~~");
    		}
    		mv.addObject("HIT_RANK", todayHitRank);
    		
    		// 7. 게시물 댓글수 순위
    		// - 오늘 달린 댓글수 많은 순으로 순위,제목,조회수,글번호 가져오기. (최대 5개)
   		 
   		 	// 8. 검색어 유입 경로
    		// - 오늘의 검색어 일치하는 거 있으면 count해서 높은 순으로 검색어,count 가져오기.(최대 10개) 
    		
		// 2) 조회수 (/visit_pv) ~~ 글 조회수
  	    // - 일간(데이터 없는 경우, 일간-오늘) / 주간 / 월간
  	    // - 전체/이웃/기타
    	}else if(menu.equals("/hit")){
    	
    	// 3) 방문 횟수 (/visit) ~~ 블로그 방문 횟수
  	    //  - 일간(데이터 없는 경우, 일간-오늘) / 주간 / 월간
  	    //  - 전체/이웃/기타
    	}else if(menu.equals("/visit")) {
    	
      	//   4) 검색어 분석(/referer)
  	    //  - 일간(데이터 없는 경우, 일간-오늘) / 주간 / 월간
		}else if(menu.equals("/referer")) {
			
    	//   5) 시간대 분석 (/hour_cv)--할지말지 고민중~~~
    	//}else if(menu.equals("/hour_")) {
		//   6) 성별·연령별 분포 (/demo) --이것두 고민중~~~
    	//}else if(menu.equals("/")) {
        	
      	//   7) 조회수 순위 (/rank_hits)
  	    //  - 일간(데이터 없는 경우, 일간-오늘) / 주간 / 월간
    	}else if(menu.equals("/rank_hit")) {
        	
      	//   8) 좋아요수 순위 (/rank_likes)
  	    //  - 일간(데이터 없는 경우, 일간-오늘) / 주간 / 월간
    	}else if(menu.equals("/rank_like")) {
        	
      	//   9) 댓글수 순위 (/rank_comment)
  	    //  - 일간(데이터 없는 경우, 일간-오늘) / 주간 / 월간
    	}else if(menu.equals("/rank_comment")) {
    		
    	}
    	
    	mv.addObject("BLOG", blogDTO);
    	mv.setViewName("blog/admin/stat");
    	
    	return mv;
    }
}
