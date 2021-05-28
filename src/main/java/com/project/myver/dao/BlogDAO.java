package com.project.myver.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.myver.dto.BlogDTO;
import com.project.myver.util.PageUtil;

public class BlogDAO extends SqlSessionDaoSupport {
	@Autowired
	SqlSessionTemplate session;

	// 'blog' table =================================================
	// 21.05.19 블로그 생성하고 'blog_no' 리턴
	public int insertBlog(BlogDTO blogDTO) {
		session.insert("blog.insertBlog", blogDTO);
		return blogDTO.getBlog_no();
	}

	// 21.05.19 블로그 홈에서 보일 'member_no'에 해당하는 블로그 정보
	public BlogDTO selectBlogHomeDataFromBlog(int member_no) {
		return session.selectOne("blog.selectBlogHomeDataFromBlog", member_no);
	}
	
	// 21.05.19 'member_no'로 모든 블로그 정보 가져오기
	public BlogDTO selectAllFromBlog(int member_no) {
		return session.selectOne("blog.selectAllFromBlog", member_no);
	}

	// 21.05.25 'member_no'로 블로그 이웃 정보 가져오기 (blog_title, blog_nick, blog_img_no)
	public BlogDTO selectBlog_titleAndNickAndImg_noFromBlog(int member_no) {
		return session.selectOne("blog.selectBlog_titleAndNickAndImg_noFromBlog", member_no);
	}
	
	// 'blog_visit'table =========================================
	// 21.05.19 'blog_no'에 해당하는 블로그 오늘 방문자수 
	public int todayBlogVisitCount(int blog_no) {
		return session.selectOne("blog.todayBlogVisitCount", blog_no);
	}

	
	// 'blog_category'table ========================================
	// 21.05.28 블로그 기본 카테고리('전체보기') 생성insertDefaultBlogCategory
	public void insertDefaultBlogCategory(int blog_no) {
		session.insert("blog.insertDefaultBlogCategory", blog_no);
	}
	
	// 21.05.23 카테고리 리스트 가져오기
	public List<BlogDTO> selectAllFromBlog_category(int blog_no) {
		return session.selectList("blog.selectAllFromBlog_category", blog_no);
	}

	
	// 'blog_neighbor'table =========================================
	// 21.05.24 내가 추가한 이웃 리스트 가져오기(member_no 리스트)
	public List<BlogDTO> selectFollowingListFromBlog_neighbor(int member_no) {
		return session.selectList("blog.selectFollowingListFromBlog_neighbor", member_no);
	}

	// 21.05.24 나를 추가한 이웃 리스트 가져오기(member_no 리스트)
	public List<BlogDTO> selectFollowerListFromBlog_neighbor(int member_no) {
		return session.selectList("blog.selectFollowerListFromBlog_neighbor", member_no);
	}

	
	// 'blog_object'table =================================================
	// 21.05.27 블로그 글 테이블에서 'blog_category_no'에 해당하는 개수 가져오기 selectTotalCountFromBlog_object
	public int selectTotalCountFromBlog_object(int blog_category_no) {
		return session.selectOne("blog.selectTotalCountFromBlog_object", blog_category_no);
	}

	// 21.05.27 목록 내용 가져오기
	public List<BlogDTO> selectListDetailFromBlog_object(PageUtil listInfo) {
		System.out.println(listInfo.toString());
		return session.selectList("blog.selectListDetailFromBlog_object", listInfo);
	}

	// 21.05.27 게시글 내용 가져오기
	public List<BlogDTO> selectObjectDetailFromBlog_object(PageUtil pageInfo) {
		return session.selectList("blog.selectObjectDetailFromBlog_object", pageInfo);
	}
	

	
}
