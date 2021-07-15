package com.project.myver.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.myver.dto.BlogDTO;
import com.project.myver.dto.CommentDTO;
import com.project.myver.util.PageUtil;

@Repository("blogDAO")
public class BlogDAO extends SqlSessionDaoSupport {
	@Autowired
	SqlSessionTemplate session;

	// 'blog' table ===================================================================================================================
	// 21.05.19 블로그 생성하고 'blog_no' 리턴
	public int insertBlog(BlogDTO blogDTO) {
		session.insert("blog.insertBlog", blogDTO);
		return blogDTO.getBlog_no();
	}

	// 21.05.19 블로그 홈에서 보일 'member_id'에 해당하는 블로그 정보
	public BlogDTO selectBlogHomeDataFromBlog(int member_no) {
		return session.selectOne("blog.selectBlogHomeDataFromBlog", member_no);
	}
	
	// 21.05.19 'member_no'로 모든 블로그 정보 가져오기
	public BlogDTO selectAllFromBlog(String member_id) {
		return session.selectOne("blog.selectAllFromBlog", member_id);
	}

	// 21.05.25 'member_no'로 블로그 이웃 정보 가져오기 (blog_title, blog_id, blog_nick, blog_img_no)
	public BlogDTO selectBlog_titleAndidAndNickAndImg_noFromBlog(int member_no) {
		return session.selectOne("blog.selectBlog_titleAndidAndNickAndImg_noFromBlog", member_no);
	}
	
	// 21.06.22 블로그 정보 수정
	public int blogUpdate(BlogDTO blogDTO) {
		return session.update("blog.blogUpdate", blogDTO);
	}
	
	
	// 'blog_visit'table ============================================================================================================
	// 21.05.19 'blog_no'에 해당하는 블로그 오늘 방문자수 
	public int todayBlogVisitCount(int blog_no) {
		return session.selectOne("blog.todayBlogVisitCount", blog_no);
	}

	// 21.06.09 블로그 방문자 정보 추가
	public void insertBlog_visit(BlogDTO blogDTO) {
		session.insert("blog.insertBlog_visit", blogDTO);
	}
	
	// 21.07.03 'blog_no'에 해당하는 블로그글 오늘 조회수
	public int todayObjectHitFromBlog_visit(int blog_no) {
		return session.selectOne("blog.todayObjectHitFromBlog_visit", blog_no);
	}
	
	// 21.07.04 'blog_no'로 특정날짜(endDay)까지의 15일의 총 조회수 가져오기
	public List<BlogDTO> totalHitOfLast15DaysFromBlog_visit(Map<String,Object> map) {
		return session.selectList("blog.totalHitOfLast15DaysFromBlog_visit", map);
	}
	
	
	
	// 'blog_category'table ==========================================================================================================
	// 21.05.28 블로그 기본 카테고리('전체보기') 생성insertDefaultBlogCategory
	public void insertDefaultBlogCategory(int blog_no) {
		session.insert("blog.insertDefaultBlogCategory", blog_no);
	}
	
	// 21.05.23 카테고리 리스트 가져오기
	public List<BlogDTO> selectAllFromBlog_category(int blog_no) {
		return session.selectList("blog.selectAllFromBlog_category", blog_no);
	}

	// 21.06.02 공개된 카테고리 리스트 가져오기
	public List<BlogDTO> selectPublicFromBlog_category(int blog_no) {
		return session.selectList("blog.selectPublicFromBlog_category", blog_no);
	}
	
	// 21.06.12 'blog_category_no'에 해당하는 정보 가져오기 ~~~아직 사용안하는...~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public BlogDTO selectByBlog_category_noFromBlog_category(Map map) {
		return session.selectOne("blog.selectByBlog_category_noFromBlog_category", map);
	}
	
	// 21.06.14 'blog_category_no'에 해당하는 'category_name' 가져오기
	public String selectCategory_nameByBlog_category_noFromBlog_category(int blog_category_no) {
		return session.selectOne("blog.selectCategory_nameByBlog_category_noFromBlog_category", blog_category_no);
	}
	
	// 21.06.29 'blog_no' 해당하는 카테고리의 'blog_category_no','category_name' 가져오기
	public List<BlogDTO> selectBlog_category_noAndCategory_name(int blog_no) {
		return session.selectList("blog.selectBlog_category_noAndCategory_name", blog_no);
	}
	
	// 21.06.29 'blog_no'에 해당하는 카테고리의 'blog_category_no','category_name','is_public','parent_category_no','is_upper' 가져오기
	public List<BlogDTO> selectBlog_category_noAndCategory_nameAndIs_publicAndParent_category_noAndIs_upper(int blog_no) {
		return session.selectList("blog.selectBlog_category_noAndCategory_nameAndIs_publicAndParent_category_noAndIs_upper", blog_no);
	}
	
	
	// 'blog_neighbor'table ==========================================================================================================
	// 21.05.24 내가 추가한 이웃 리스트 가져오기(member_no 리스트)
	public List<BlogDTO> selectFollowingListFromBlog_neighbor(int member_no) {
		return session.selectList("blog.selectFollowingListFromBlog_neighbor", member_no);
	}

	// 21.05.24 나를 추가한 이웃 리스트 가져오기(member_no 리스트)
	public List<BlogDTO> selectFollowerListFromBlog_neighbor(int member_no) {
		return session.selectList("blog.selectFollowerListFromBlog_neighbor", member_no);
	}

	
	
	// 'blog_object'table ===============================================================================================================
	// 21.05.27 'blog_category_no' 혹은 'blog_no'에 해당하는 개수 가져오기
	public int selectTotalCountByNoFromBlog_object(Map map) {
		return session.selectOne("blog.selectTotalCountByNoFromBlog_object", map);
	}

	// 21.05.27 목록 내용 가져오기 (전체/공개)
	public List<BlogDTO> selectListDetailByNoFromBlog_object(PageUtil listInfo) {
		return session.selectList("blog.selectListDetailByNoFromBlog_object", listInfo);
	}

	// 21.05.27 게시글 내용 가져오기 (전체/공개)
	public List<BlogDTO> selectObjectDetailByNoFromBlog_object(PageUtil pageInfo) {
		return session.selectList("blog.selectObjectDetailByNoFromBlog_object", pageInfo);
	}

	// 21.06.09 게시물 조회수 업데이트(증가)
	public void updateBlogObjectHits(int blog_object_no) {
		session.update("blog.updateBlogObjectHits", blog_object_no);
	}

	// 21.06.10 'blog_no' 혹은 'blog_object_no'에 일치하는 'blog_object' 가져오기
	public BlogDTO selectBlog_object(Map<String, Object> map) {
		return session.selectOne("blog.selectBlog_object", map);
	}

	
	
	// 'blog_comment' table =============================================================================================================
	// 21.07.03 'blog_no'에 해당하는 오늘의 댓글수 가져오기
	public int todayCommentCount(int blog_no) {
		return session.selectOne("blog.todayCommentCount", blog_no);
	}
	
	
	
	// 'blog_comment' & 'blog_object' table ===================================================================================================
	// 'blog_no'에 해당하는 블로그 댓글과 해당하는 댓글의 글 번호의 글제목 가져오기
	public List<CommentDTO> selectCommentByBlog_noFromBlog_comment(int blog_no) {
		return session.selectList("blog.selectCommentByBlog_noFromBlog_comment", blog_no);
	}

	
	
	// 'blog_visit' & 'blog_object' table ===================================================================================================
	// 21.07.13 특정기간(원하는 일자 ~ 해당하지 않는 일자)에 해당하는 조회수 순위(+ 제목, 글번호, 조회수, 작성일)
	public List<BlogDTO> hitRankDuringFromBlog_visitAndBlog_object(Map<String, Object> map) {
		return session.selectList("blog.hitRankDuringFromBlog_visitAndBlog_object", map);
	}

	

	

	
	
	
}
