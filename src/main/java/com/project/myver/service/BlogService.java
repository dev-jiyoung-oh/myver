package com.project.myver.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.myver.dao.BlogDAO;
import com.project.myver.dao.ImageDAO;
import com.project.myver.dto.BlogDTO;
import com.project.myver.dto.ImageDTO;
import com.project.myver.util.PageUtil;

@Service
public class BlogService {
	@Autowired
	private BlogDAO blogDAO;
	@Autowired
	private ImageDAO imgDAO;

	// 클래스 내에서 사용하는 함수 =====================================================
	// 21.05.25 'blogDTO.member_no'로 블로그 이웃 정보 가져와서 blogDTO 값 초기화 (blog_title, blog_nick, blog_img_no)
	public void setBlog_titleAndNickAndImg_no(BlogDTO blogDTO) {
		BlogDTO temp = blogDAO.selectBlog_titleAndNickAndImg_noFromBlog(blogDTO.getMember_no());
		
		blogDTO.setBlog_title(temp.getBlog_title());
		blogDTO.setBlog_nick(temp.getBlog_nick());
		blogDTO.setBlog_img_no(temp.getBlog_img_no());
	}
	
	// 21.05.25 'blogDTO.blog_img_no'로 'image'테이블에서 'path', 'saved_name' 가져와서 blogDTO 값 초기화
	public void setPathAndSavedName(BlogDTO blogDTO) {
		ImageDTO imgDTO = imgDAO.selectPathAndSaved_nameFromImage(blogDTO.getBlog_img_no());
		
		blogDTO.setPath(imgDTO.getPath());
		blogDTO.setSaved_name(imgDTO.getSaved_name());
	}
		
		
	// 'blog'table ============================================================
	// 21.05.19 블로그 생성하고 'blog_no' 리턴
	public int insertBlog(int member_no, String blog_nick) {
		BlogDTO blogDTO = new BlogDTO();
		blogDTO.setMember_no(member_no);
		blogDTO.setBlog_title(blog_nick+"의 블로그");
		blogDTO.setBlog_nick(blog_nick);
		
		return blogDAO.insertBlog(blogDTO);
	}

	// 21.05.19 블로그 홈에서 보일 'member_no'에 해당하는 블로그 정보
	public BlogDTO selectBlogHomeDataFromBlog(int member_no) {
		return blogDAO.selectBlogHomeDataFromBlog(member_no);
	}
	
	// 21.05.19 member_no로 블로그 정보 가져오기
	public BlogDTO selectAllFromBlog(int member_no) {
		return blogDAO.selectAllFromBlog(member_no);
	}

	
	// 'blog_visit'table ===================================================
	// 21.05.19 'blog_no'에 해당하는 블로그 오늘 방문자수 
	public int todayBlogVisitCount(int blog_no) {
		return blogDAO.todayBlogVisitCount(blog_no);
	}	
	
	
	// 'blog_category'table ==================================================
	// 21.05.28 블로그 기본 카테고리('전체보기') 생성
	public void insertDefaultBlogCategory(int blog_no) {
		blogDAO.insertDefaultBlogCategory(blog_no);
	}
	
	// 21.05.23 카테고리 리스트 가져오기
	public List<BlogDTO> selectAllFromBlog_category(int blog_no) {
		return blogDAO.selectAllFromBlog_category(blog_no);
	}
	
	// 21.06.02 공개된 카테고리 리스트 가져오기
	public List<BlogDTO> selectPublicFromBlog_category(int blog_no) {
		return blogDAO.selectPublicFromBlog_category(blog_no);
	}

	
	// 'blog_neighbor'table ==================================================
	// 21.05.24 내가 추가한 이웃 리스트 가져오기
	public List<BlogDTO> selectFollowingListFromBlog_neighbor(int member_no) {
		List<BlogDTO> blogList = blogDAO.selectFollowingListFromBlog_neighbor(member_no);
		
		for(BlogDTO blogDTO : blogList) {
			// 'blogDTO.member_no'로 블로그 이웃 정보 가져와서 blogDTO 값 초기화 (blog_title, blog_nick, blog_img_no)
			setBlog_titleAndNickAndImg_no(blogDTO);
			
			// 'blogDTO.blog_img_no'로 'image'테이블에서 path, saved_name 가져와서 blogDTO 값 초기화
			setPathAndSavedName(blogDTO);
		}
		
		return blogList;
	}
	
	// 21.05.24 나를 추가한 이웃 리스트 가져오기
	public List<BlogDTO> selectFollowerListFromBlog_neighbor(int member_no) {
		List<BlogDTO> blogList = blogDAO.selectFollowerListFromBlog_neighbor(member_no);
		
		for(BlogDTO blogDTO : blogList) {
			// 'blogDTO.member_no'로  blog_title, blog_nick, blog_img_no 가져와서 blogDTO 값 초기화
			setBlog_titleAndNickAndImg_no(blogDTO);
			
			// 'blogDTO.blog_img_no'로 'image'테이블에서 path, saved_name 가져와서 blogDTO 값 초기화
			setPathAndSavedName(blogDTO);
			
		}
		
		return blogList;
	}

	
	// 'blog_object'table =================================================
	// 21.05.27 'blog_category_no' 혹은 'blog_no'에 해당하는 개수 가져오기
	public int selectTotalCountByNoFromBlog_object(int no, String column_name, boolean only_public) {
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		map.put("column_name", column_name);
		map.put("only_public", only_public);
		
		return blogDAO.selectTotalCountByNoFromBlog_object(map);
	}

	// 21.05.27 'blog_category_no' 혹은 'blog_no'에 해당하는 목록 내용 가져오기 (전체/공개)
	public List<BlogDTO> selectListDetailByNoFromBlog_object(PageUtil listInfo) {
		return blogDAO.selectListDetailByNoFromBlog_object(listInfo);
	}
	
	// 21.05.27 'blog_category_no' 혹은 'blog_no'에 해당하는 게시글 내용 가져오기 (전체/공개)
	public List<BlogDTO> selectObjectDetailByNoFromBlog_object(PageUtil pageInfo) {
		return blogDAO.selectObjectDetailByNoFromBlog_object(pageInfo);
	}
	
	// 21.06.09 게시물 조회수 업데이트(증가)
	public boolean updateBlogObjectHits(int blog_object_no, HttpSession session) {
		ArrayList<Integer> list = (ArrayList) session.getAttribute("BLOGOBJECTHITCHECK");
		boolean is_first;
		
		System.out.println("session id: "+session.getId()+", list: " + list);
		
		// 기록이 없을 경우(블로그 게시글 자체를 처음 방문하는 경우)
		if (list == null) {
			list = new ArrayList<>();
			list.add(blog_object_no);
			session.setAttribute("BLOGOBJECTHITCHECK", list);
			is_first = true;
		}else if (list.contains(blog_object_no)) { // 기록이 있을 경우 -> 해당 게시글을 이미 조회한 경우
			is_first = false;
		}else {// 기록이 있는 경우 -> 해당 게시글을 처음 방문하는 경우
			list.add(blog_object_no);
			session.setAttribute("BLOGOBJECTHITCHECK", list);
			is_first = true;
		}
		
		if(is_first) {
			blogDAO.updateBlogObjectHits(blog_object_no);
		}
		
		return is_first;
	}

	// 21.06.09 블로그 방문자 정보 추가
	public boolean insertBlog_visit(int blog_no, int blog_object_no, int visitor_no, String query, HttpSession session) {
		ArrayList<Integer> list = (ArrayList) session.getAttribute("BLOGVISITCHECK");
		boolean is_first;
		
		System.out.println("session id: "+session.getId()+", list: " + list);
		
		// 기록이 없을 경우(블로그 자체를 처음 방문하는 경우)
		if (list == null) {
			list = new ArrayList<>();
			list.add(blog_no);
			session.setAttribute("BLOGVISITCHECK", list);
			is_first = true;
		}else if (list.contains(blog_no)) { // 기록이 있을 경우 -> 해당 블로그를 방문한 경우
			is_first = false;
		}else {// 기록이 있는 경우 -> 해당 블로그를 처음 방문하는 경우
			list.add(blog_no);
			session.setAttribute("BLOGVISITCHECK", list);
			is_first = true;
		}
		
		if(is_first) {
			BlogDTO blogDTO = new BlogDTO();
			blogDTO.setBlog_no(blog_no);
			blogDTO.setBlog_object_no(blog_object_no);
			blogDTO.setVisitor_no(visitor_no);
			blogDTO.setQuery(query);
			
			blogDAO.insertBlog_visit(blogDTO);
		}
		
		return is_first;
	}
	
}
