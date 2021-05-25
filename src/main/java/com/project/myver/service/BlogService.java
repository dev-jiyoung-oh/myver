package com.project.myver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.myver.dao.BlogDAO;
import com.project.myver.dao.ImageDAO;
import com.project.myver.dto.BlogDTO;
import com.project.myver.dto.ImageDTO;

@Service
public class BlogService {
	@Autowired
	private BlogDAO blogDAO;
	@Autowired
	private ImageDAO imgDAO;

	// 'blog'table ===============================================
	// 21.05.19 블로그 생성
	public void insertBlog(int member_no, String blog_nick) {
		BlogDTO blogDTO = new BlogDTO();
		blogDTO.setMember_no(member_no);
		blogDTO.setBlog_title(blog_nick+"의 블로그");
		blogDTO.setBlog_nick(blog_nick);
		
		blogDAO.insertBlog(blogDTO);
	}

	// 21.05.19 블로그 홈에서 보일 'member_no'에 해당하는 블로그 정보
	public BlogDTO selectBlogHomeDataFromBlog(int member_no) {
		return blogDAO.selectBlogHomeDataFromBlog(member_no);
	}
	
	// 21.05.19 member_no로 블로그 정보 가져오기
	public BlogDTO selectAllFromBlog(int member_no) {
		return blogDAO.selectAllFromBlog(member_no);
	}

	
	// 'blog_visit'table =========================================
	// 21.05.19 'blog_no'에 해당하는 블로그 오늘 방문자수 
	public int todayBlogVisitCount(int blog_no) {
		return blogDAO.todayBlogVisitCount(blog_no);
	}	
	
	
	// 'blog_category'table ========================================
	// 21.05.23 카테고리 리스트 가져오기SELECT * FROM blog_category WHERE blog_no=#{blog_no} order by category_index
	public List<BlogDTO> selectAllFromBlog_category(int blog_no) {
		return blogDAO.selectAllFromBlog_category(blog_no);
	}

	
	// 'blog_neighbor'table =========================================
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
}
