package com.project.myver.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

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
	private ImageService imgSVC;

	// 클래스 내에서 사용하는 함수 =====================================================
	// 21.05.25 'blogDTO.member_no'로 블로그 이웃 정보 가져와서 blogDTO 값 초기화 (blog_id, blog_nick, blog_img_no)
	public void setBlog_idAndNickAndImg_no(BlogDTO blogDTO, String followingOrFollower) {
		BlogDTO temp = null;
		if(followingOrFollower.equals("following")) {
			temp = blogDAO.selectBlog_idAndNickAndImg_noFromBlog(blogDTO.getNeighbor_member_no());
		}else {
			temp = blogDAO.selectBlog_idAndNickAndImg_noFromBlog(blogDTO.getMember_no());
		}
		
		blogDTO.setBlog_id(temp.getBlog_id());
		blogDTO.setBlog_nick(temp.getBlog_nick());
		blogDTO.setBlog_img_no(temp.getBlog_img_no());
	}
		
		
	// 'blog'table ============================================================
	// 21.05.19 블로그 생성하고 'blog_no' 리턴
	public int insertBlog(int member_no, String member_id, String blog_nick) {
		BlogDTO blogDTO = new BlogDTO();
		blogDTO.setMember_no(member_no);
		blogDTO.setBlog_id(member_id);
		blogDTO.setBlog_title(blog_nick+"의 블로그");
		blogDTO.setBlog_nick(blog_nick);
		
		return blogDAO.insertBlog(blogDTO);
	}

	// 21.05.19 블로그 홈에서 보일 'member_no'에 해당하는 블로그 정보
	public BlogDTO selectBlogHomeDataFromBlog(int member_no) {
		BlogDTO blogDTO = blogDAO.selectBlogHomeDataFromBlog(member_no);
		// 이미지 번호로 이미지 path, saved_name 세팅(이미지 번호 없는 경우 제외)
		if(blogDTO.getBlog_img_no() != 0) {
			imgSVC.setPathAndSaved_nameFromImage(blogDTO);
		}
		return blogDTO;
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
			// 'blogDTO.neighber_member_no'로 블로그 이웃 정보 가져와서 blogDTO 값 초기화 (blog_id, blog_nick, blog_img_no)
			setBlog_idAndNickAndImg_no(blogDTO, "following");
			
			// 'blogDTO.blog_img_no'로 'image'테이블에서 path, saved_name 가져와서 blogDTO 값 초기화
			if(blogDTO.getBlog_img_no() != 0) {
				imgSVC.setPathAndSaved_nameFromImage(blogDTO);
			}
		}
		
		return blogList;
	}
	
	// 21.05.24 나를 추가한 이웃 리스트 가져오기
	public List<BlogDTO> selectFollowerListFromBlog_neighbor(int member_no) {
		List<BlogDTO> blogList = blogDAO.selectFollowerListFromBlog_neighbor(member_no);
		
		for(BlogDTO blogDTO : blogList) {
			// 'blogDTO.member_no'로  blog_title, blog_nick, blog_img_no 가져와서 blogDTO 값 초기화
			setBlog_idAndNickAndImg_no(blogDTO, "follwer");
			
			// 'blogDTO.blog_img_no'로 'image'테이블에서 path, saved_name 가져와서 blogDTO 값 초기화
			if(blogDTO.getBlog_img_no() != 0) {
				imgSVC.setPathAndSaved_nameFromImage(blogDTO);
			}
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

	// 21.06.10 'blog_no'와 'blog_object_no'에 일치하는 'blog_object' 가져오기
	public BlogDTO selectBlog_object(int blog_no, int blog_object_no, boolean is_owner) {
		Map<String, Object> map = new HashMap<>();
		map.put("blog_no", blog_no);
		map.put("blog_object_no", blog_object_no);
		map.put("is_owner",is_owner);
		
		BlogDTO blogDTO = blogDAO.selectBlog_object(map);
		
		// 21.06.14 'blog_category_no'에 해당하는 'category_name' 가져와서 set
		blogDTO.setCategory_name(blogDAO.selectCategory_nameByBlog_category_noFromBlog_category(blogDTO.getBlog_category_no()));
		
		return blogDTO;
	}
	
	// 21.06.12 블로그 메인 - 카테고리 리스트, 목록 리스트, 글 리스트 가져오기
	public Map<String, Object> selectCategoryAndListAndObject(BlogDTO blogDTO, boolean is_owner, int blog_category_no, int currentPage) {
		Map<String, Object> map = new HashMap<>();
		String column_name = ""; // "blog_category_no" 혹은 "blog_no"가 들어갈 예정
		int no = -1;			 // blog_category_no 혹은 blog_no가 들어갈 예정
		BlogDTO blog_category = null;
		
		// 21.06.03 방문자가 블로그 주인일 경우
		if(is_owner){
			System.out.println("주인이 방문했습니다.");
			
			// 21.05.23 카테고리 리스트 가져오기
			List<BlogDTO> categoryList = selectAllFromBlog_category(blogDTO.getBlog_no());
			
			
			if(blog_category_no!= -1) { // 21.06.12 카테고리 번호가 주어진 경우
				for(BlogDTO category : categoryList) {
					if(category.getBlog_category_no() == blog_category_no) {
						blog_category = category;
						
						if(category.getAll_category()==1) { //전체 카테고리가 기본 카테고리인 경우
							System.out.println("전체 카테고리가 기본 카테고리인 경우");
							no = blogDTO.getBlog_no();
							column_name = "blog_no";
						}else {
							System.out.println("기본 카테고리가 특정 카테고리인 경우");
							no = category.getBlog_category_no();
							column_name = "blog_category_no";
						}
						break;
					}
				}
			}else { // 카테고리 번호가 존재하지 않는 경우
				for(BlogDTO category : categoryList) {
					// 대표 카테고리인 경우
					if(category.getIs_basic() == 1) {
						blog_category = category;
						
						if(category.getAll_category()==1) { //전체 카테고리가 기본 카테고리인 경우
							System.out.println("전체 카테고리가 기본 카테고리인 경우");
							no = blogDTO.getBlog_no();
							column_name = "blog_no";
						}else {
							System.out.println("기본 카테고리가 특정 카테고리인 경우");
							no = category.getBlog_category_no();
							column_name = "blog_category_no";
						}
						break;
					}
				}				
			}
			map.put("categoryList", categoryList);
		}else { // 21.06.30 방문자가 외부인일 경우
			System.out.println("외부인이 방문했습니다.");
			
			// 21.06.02 공개된 카테고리 리스트 가져오기
			List<BlogDTO> categoryList = selectPublicFromBlog_category(blogDTO.getBlog_no());
			
			// 21.06.12 카테고리 번호가 존재하는 경우 해당하는 카테고리의 정보를 가져오기
			if(blog_category_no!= -1) {
				for(BlogDTO category : categoryList) {
					if(category.getBlog_category_no() == blog_category_no) {
						blog_category = category;
						
						if(category.getAll_category()==1) { //전체 카테고리가 기본 카테고리인 경우
							System.out.println("전체 카테고리가 기본 카테고리인 경우");
							no = blogDTO.getBlog_no();
							column_name = "blog_no";
						}else {
							System.out.println("기본 카테고리가 특정 카테고리인 경우");
							no = category.getBlog_category_no();
							column_name = "blog_category_no";
						}
						break;
					}
				}
			}else { // 카테고리 번호가 존재하지 않는 경우
				for(BlogDTO category : categoryList) {
					// 대표 카테고리인 경우
					if(category.getIs_basic() == 1) {
						blog_category = category;
						
						if(category.getAll_category()==1) { //전체 카테고리가 기본 카테고리인 경우
							System.out.println("전체 카테고리가 기본 카테고리인 경우");
							no = blogDTO.getBlog_no();
							column_name = "blog_no";
						}else {
							System.out.println("기본 카테고리가 특정 카테고리인 경우");
							no = category.getBlog_category_no();
							column_name = "blog_category_no";
						}
						break;
					}
				}
			}
			map.put("categoryList", categoryList);
		}
		
		// 존재하지 않은 카테고리에 들어가려고 하는 경우/ 외부인이 비공개인 카테고리에 들어가려고 하는 경우
		if(blog_category == null) {
			return null;
		}
		
		// 21.05.27 블로그 글 테이블에서 'blog_no' 혹은 'blog_category_no'에 해당하는 개수 가져오기
		int totalCount = selectTotalCountByNoFromBlog_object(no, column_name, is_owner);
		
		// 21.05.27 리스트, 게시글 페이지 정보 생성  PageUtil(nowPage,totalCount,lineCount,no,column_name,is_owner)
		PageUtil listInfo = new PageUtil(currentPage,totalCount,blog_category.getList_line(),no,column_name,is_owner);
		PageUtil pageInfo = new PageUtil(currentPage,totalCount,blog_category.getObjects_per_page(),no,column_name,is_owner);
		
		// 21.05.27 목록 내용 가져오기
		List<BlogDTO> lists = selectListDetailByNoFromBlog_object(listInfo);
				
		// 21.05.27 게시글 내용 가져오기
		List<BlogDTO> objects = selectObjectDetailByNoFromBlog_object(pageInfo);
		
		map.put("blog_category", blog_category);
		map.put("totalCount", totalCount);
		map.put("lists", lists);
		map.put("objects", objects);
		
		return map;
	}


	// 21.06.14 블로그 글 보기 - 카테고리, 목록 가져오기
	public Map<String, Object> selectCategoryAndList(BlogDTO blogDTO, BlogDTO object, boolean is_owner, int blog_category_no) {
		Map<String, Object> map = new HashMap<>();
		String column_name = ""; // "blog_category_no" 혹은 "blog_no"가 들어갈 예정
		int no = -1;			 // blog_category_no 혹은 blog_no가 들어갈 예정
		BlogDTO blog_category = null;
		
		// 방문자가 블로그 주인일 경우
		if(is_owner){
			System.out.println("주인이 방문했습니다.");
			
			// 21.05.23 모든 카테고리 리스트 가져오기
			List<BlogDTO> categoryList = selectAllFromBlog_category(blogDTO.getBlog_no());
			
			// 21.06.12 카테고리 번호가 존재하지 않는 경우, 해당하는 게시글의 카테고리의 정보를 가져오기
			if(blog_category_no== -1) {
				int object_category_no = object.getBlog_category_no();
				
				for(BlogDTO category : categoryList) {
					if(category.getBlog_category_no() == object_category_no) {
						blog_category = category;
						
						if(category.getAll_category()==1) { //전체 카테고리가 기본 카테고리인 경우
							System.out.println("전체 카테고리가 기본 카테고리인 경우");
							no = blogDTO.getBlog_no();
							column_name = "blog_no";
						}else {
							System.out.println("기본 카테고리가 특정 카테고리인 경우");
							no = category.getBlog_category_no();
							column_name = "blog_category_no";
						}
						break;
					}
				}
			}else { // 카테고리 번호가 존재하는 경우
				for(BlogDTO category : categoryList) {
					// 대표 카테고리인 경우
					if(category.getBlog_category_no() == blog_category_no) {
						blog_category = category;
						
						if(category.getAll_category()==1) { //전체 카테고리가 기본 카테고리인 경우
							System.out.println("전체 카테고리가 기본 카테고리인 경우");
							no = blogDTO.getBlog_no();
							column_name = "blog_no";
						}else {
							System.out.println("기본 카테고리가 특정 카테고리인 경우");
							no = category.getBlog_category_no();
							column_name = "blog_category_no";
						}
						break;
					}
				}				
			}
			map.put("categoryList", categoryList);
		}else { // 21.05.30 방문자가 외부인일 경우
			System.out.println("외부인이 방문했습니다.");
			
			// 21.06.02 공개된 카테고리 리스트 가져오기
			List<BlogDTO> categoryList = selectPublicFromBlog_category(blogDTO.getBlog_no());
			
			// 21.06.12 카테고리 번호가 존재하지 않는 경우, 해당하는 게시글의 카테고리의 정보를 가져오기
			if(blog_category_no == -1) {
				int object_category_no = object.getBlog_category_no();
				
				for(BlogDTO category : categoryList) {
					if(category.getBlog_category_no() == object_category_no) {
						blog_category = category;
						
						if(category.getAll_category()==1) { //전체 카테고리가 기본 카테고리인 경우
							System.out.println("전체 카테고리가 기본 카테고리인 경우");
							no = blogDTO.getBlog_no();
							column_name = "blog_no";
						}else {
							System.out.println("기본 카테고리가 특정 카테고리인 경우");
							no = category.getBlog_category_no();
							column_name = "blog_category_no";
						}
						break;
					}
				}
			}else { // 카테고리 번호가 존재하지 않는 경우
				for(BlogDTO category : categoryList) {
					// 대표 카테고리인 경우
					if(category.getIs_basic() == 1) {
						blog_category = category;
						
						if(category.getAll_category()==1) { //전체 카테고리가 기본 카테고리인 경우
							System.out.println("전체 카테고리가 기본 카테고리인 경우");
							no = blogDTO.getBlog_no();
							column_name = "blog_no";
						}else {
							System.out.println("기본 카테고리가 특정 카테고리인 경우");
							no = category.getBlog_category_no();
							column_name = "blog_category_no";
						}
						break;
					}
				}
			}
			map.put("categoryList", categoryList);
		}
		
		// 존재하지 않은 카테고리에 들어가려고 하는 경우/ 외부인이 비공개인 카테고리에 들어가려고 하는 경우
		if(blog_category == null) {
			return null;
		}
		
		// 21.05.27 블로그 글 테이블에서 'blog_no' 혹은 'blog_category_no'에 해당하는 개수 가져오기
		int totalCount = selectTotalCountByNoFromBlog_object(no, column_name, is_owner);
		
		// 21.05.27 리스트, 게시글 페이지 정보 생성  PageUtil(totalCount,lineCount,no,column_name,is_owner,nowNo)
		PageUtil listInfo = new PageUtil(totalCount,blog_category.getList_line(),no,column_name,is_owner,object.getBlog_object_no());
		
		// 21.05.27 목록 내용 가져오기
		List<BlogDTO> lists = selectListDetailByNoFromBlog_object(listInfo);
		
		map.put("blog_category", blog_category);
		map.put("totalCount", totalCount);
		map.put("lists", lists);
		
		return map;
	}
	
}
