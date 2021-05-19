package com.project.myver.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.myver.dto.BlogDTO;

public class BlogDAO extends SqlSessionDaoSupport {
	@Autowired
	SqlSessionTemplate session;

	// 'blog' table =================================================
	// 21.05.19 블로그 생성
	public void insertBlog(BlogDTO blogDTO) {
		session.insert("blog.insertBlog", blogDTO);
	}

	// 21.05.19 블로그 홈에서 보일 'member_no'에 해당하는 블로그 정보
	public BlogDTO selectBlogHomeDataFromBlog(int member_no) {
		return session.selectOne("blog.selectBlogHomeDataFromBlog", member_no);
	}
	
	// 21.05.19 member_no로 블로그 정보 가져오기
	public BlogDTO selectAllFromBlog(int member_no) {
		return session.selectOne("blog.selectAllFromBlog", member_no);
	}

	
	// 'blog_visit'table =========================================
	// 21.05.19 'blog_no'에 해당하는 블로그 오늘 방문자수 
	public int todayBlogVisitCount(int blog_no) {
		return session.selectOne("blog.todayBlogVisitCount", blog_no);
	}

	
}
