package com.project.myver.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
import com.project.myver.dto.CommentDTO;
import com.project.myver.dto.ImageDTO;
import com.project.myver.util.PageUtil;

@Service
public class BlogService {
	@Autowired
	private BlogDAO blogDAO;
	@Autowired
	private ImageService imgSVC;

	// 기타 함수 =====================================================
	// 21.05.25 'blogDTO.member_no'로 블로그 이웃 정보 가져와서 blogDTO 값 초기화 (blog_title, blog_id, blog_nick, blog_img_no)
	public void setBlog_titleAndidAndNickAndImg_no(BlogDTO blogDTO, String followingOrFollower) {
		BlogDTO temp = null;
		if(followingOrFollower.equals("following")) {
			temp = blogDAO.selectBlog_titleAndidAndNickAndImg_noFromBlog(blogDTO.getNeighbor_member_no());
		}else {
			temp = blogDAO.selectBlog_titleAndidAndNickAndImg_noFromBlog(blogDTO.getMember_no());
		}
		
		blogDTO.setBlog_title(temp.getBlog_title());
		blogDTO.setBlog_id(temp.getBlog_id());
		blogDTO.setBlog_nick(temp.getBlog_nick());
		blogDTO.setBlog_img_no(temp.getBlog_img_no());
	}
	
	// 21.06.12 블로그 메인 - 카테고리 리스트, 목록 리스트, 글 리스트 가져오기
	public Map<String, Object> selectCategoryAndListAndObject(BlogDTO blogDTO, String visitor_type, int blog_category_no, int currentPage) {
		Map<String, Object> map = new HashMap<>();
		String column_name = ""; // "blog_category_no" 혹은 "blog_no"가 들어갈 예정
		int no = -1;			 // blog_category_no 혹은 blog_no가 들어갈 예정
		BlogDTO blog_category = null;
		
		// 21.06.03 방문자가 블로그 주인일 경우
		if(visitor_type.equals("owner")){
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
		int totalCount = selectTotalCountByNoFromBlog_object(no, column_name, visitor_type);
		
		// 21.05.27 리스트, 게시글 페이지 정보 생성  PageUtil(nowPage, totalCount, lineCount, no, column_name, visitor_type)
		PageUtil listInfo = new PageUtil(currentPage, totalCount, blog_category.getList_line(), no, column_name, visitor_type);
		PageUtil pageInfo = new PageUtil(currentPage, totalCount, blogDTO.getObjects_per_page(), no, column_name, visitor_type);
		
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
	public Map<String, Object> selectCategoryAndList(BlogDTO blogDTO, BlogDTO object, String visitor_type, int blog_category_no) {
		Map<String, Object> map = new HashMap<>();
		String column_name = ""; // "blog_category_no" 혹은 "blog_no"가 들어갈 예정
		int no = -1;			 // blog_category_no 혹은 blog_no가 들어갈 예정
		BlogDTO blog_category = null;
		
		// 방문자가 블로그 주인일 경우
		if(visitor_type.equals("owner")){
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
		int totalCount = selectTotalCountByNoFromBlog_object(no, column_name, visitor_type);
		int rowNum = selectRowNumByNoFromBlog_object(no, column_name, visitor_type, blogDTO.getBlog_object_no());
		// 21.05.27 리스트, 게시글 페이지 정보 생성  PageUtil(totalCount, lineCount, no, column_name, visitor_type, nowNo)
		PageUtil listInfo = new PageUtil(totalCount, blog_category.getList_line(), no, column_name, visitor_type, rowNum);
		
		System.out.println(listInfo.toString());
		
		// 21.05.27 목록 내용 가져오기
		List<BlogDTO> lists = selectListDetailByNoFromBlog_object(listInfo);
		
		map.put("blog_category", blog_category);
		map.put("totalCount", totalCount);
		map.put("lists", lists);
		
		return map;
	}
	
	// 21.07.30 방문자 종류 가려내기
	public String sortOutVisitor_type(int blog_member_no, int visitor_member_no, List<BlogDTO> followingList) {
		
		if(blog_member_no == visitor_member_no) {
			return "owner";
		}else {
			for(BlogDTO following : followingList) {
				if(following.getNeighbor_member_no() == visitor_member_no) {
					return "neighbor";
				}
			}
		}
		return "other";
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
	
	// 21.05.19 member_id로 블로그 정보 가져오기
	public BlogDTO selectAllFromBlog(String member_id) {
		return blogDAO.selectAllFromBlog(member_id);
	}

	// 21.06.22 블로그 정보 수정
	public int blogUpdate(BlogDTO blogDTO) {
		return blogDAO.blogUpdate(blogDTO);
	}
		
	
	
	// 'blog_visit'table ===================================================
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
	
	// 21.05.19 'blog_no'에 해당하는 블로그 오늘 방문자수 
	public int todayBlogVisitCount(int blog_no) {
		return blogDAO.todayBlogVisitCount(blog_no);
	}	
	
	// 21.07.03 'blog_no'에 해당하는 블로그 글 오늘 조회수
	public int todayObjectHitFromBlog_visit(int blog_no) {
		return blogDAO.todayObjectHitFromBlog_visit(blog_no);
	}
	
	// 21.07.04 'blog_no'로 특정날짜(endDay)까지의 각 15일의 총 조회수 가져오기
	public List<BlogDTO> totalHitOfLast15DaysFromBlog_visit(String endDate_str, int blog_no){
		String[] endDate_str_arr = endDate_str.split(".");
		LocalDate endDate = LocalDate.now().plusDays(1);
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
		
		// 들어온 날짜가 있는 경우
		if(endDate_str_arr.length > 1) {
			endDate = LocalDate.of(Integer.parseInt(endDate_str_arr[0]), Integer.parseInt(endDate_str_arr[1]), Integer.parseInt(endDate_str_arr[2])).plusDays(1);
		}else {
			endDate_str = endDate.format(dateTimeFormatter);
		}
		
		LocalDate startDate = endDate.minusDays(15);
		String startDate_str = startDate.format(dateTimeFormatter);
		
		Map<String, Object> map = new HashMap<>();
		map.put("startDate", startDate_str);
		map.put("endDate", endDate_str);
		map.put("blog_no", blog_no);
		
		System.out.println("startDate: "+startDate_str+", endDate: "+endDate_str);
		
		ArrayList<BlogDTO> timeList = new ArrayList<>(); // 날짜 리스트
		Map<String,Integer> timeMap = new HashMap<>(); // (key: 날짜, value: list의 인덱스)
		int index = 0; // list의 인덱스(map의 값으로 넣을 것)
		while(!startDate.isEqual(endDate)){
			BlogDTO temp = new BlogDTO();
			temp.setStr_date(startDate_str);
			timeList.add(temp);
			timeMap.put(startDate_str, index++);
			startDate = startDate.plusDays(1);
			startDate_str = startDate.format(dateTimeFormatter);
		}
		
		
		List<BlogDTO> resultList = blogDAO.totalHitOfLast15DaysFromBlog_visit(map);
		
		for(BlogDTO result : resultList) {
			timeList.set(timeMap.get(result.getStr_date()), result);
		}
		
		for(BlogDTO t : timeList) {
			System.out.println(t.getStr_date()+" "+t.getHits()+" ");
		}
		
		return timeList;
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

	// 21.06.29 'blog_no' 해당하는 카테고리의 'blog_category_no','category_name' 가져오기
	public List<BlogDTO> selectBlog_category_noAndCategory_name(int blog_no) {
		return blogDAO.selectBlog_category_noAndCategory_name(blog_no);
	}

	// 21.06.29 'blog_no'에 해당하는 카테고리의 'blog_category_no','category_name','is_public','parent_category_no','is_upper' 가져오기
	public List<BlogDTO> selectBlog_category_noAndCategory_nameAndIs_publicAndParent_category_noAndIs_upper(int blog_no) {
		return blogDAO.selectBlog_category_noAndCategory_nameAndIs_publicAndParent_category_noAndIs_upper(blog_no);
	}
	
	
	
	// 'blog_neighbor'table ==================================================
	// 21.05.24 내가 추가한 이웃 리스트 가져오기
	public List<BlogDTO> selectFollowingListFromBlog_neighbor(int member_no) {
		List<BlogDTO> blogList = blogDAO.selectFollowingListFromBlog_neighbor(member_no);
		
		for(BlogDTO blogDTO : blogList) {
			// 'blogDTO.neighber_member_no'로 블로그 이웃 정보 가져와서 blogDTO 값 초기화 (blog_title, blog_id, blog_nick, blog_img_no)
			setBlog_titleAndidAndNickAndImg_no(blogDTO, "following");
			
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
			setBlog_titleAndidAndNickAndImg_no(blogDTO, "follwer");
			
			// 'blogDTO.blog_img_no'로 'image'테이블에서 path, saved_name 가져와서 blogDTO 값 초기화
			if(blogDTO.getBlog_img_no() != 0) {
				imgSVC.setPathAndSaved_nameFromImage(blogDTO);
			}
			System.out.println(blogDTO.toString());
		}
		
		return blogList;
	}

	
	
	// 'blog_object'table =================================================
	// 21.07.30 블로그 글 작성
	public int insertBlog_object(BlogDTO blog_object) {
		return blogDAO.insertBlog_object(blog_object);
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
	
	// 21.05.27 'blog_category_no' 혹은 'blog_no'에 해당하는 개수 가져오기
	public int selectTotalCountByNoFromBlog_object(int no, String column_name, String visitor_type) {
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		map.put("column_name", column_name);
		map.put("visitor_type", visitor_type);
		
		return blogDAO.selectTotalCountByNoFromBlog_object(map);
	}
	
	// 21.07.30 'blog_category_no' 혹은 'blog_no'에 해당하는 것들 중에 특정 글의 순번 가져오기
	public int selectRowNumByNoFromBlog_object(int no, String column_name, String visitor_type, int blog_object_no) {
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		map.put("column_name", column_name);
		map.put("visitor_type", visitor_type);
		map.put("blog_object_no", blog_object_no);
		
		return blogDAO.selectRowNumByNoFromBlog_object(map);
	}
	
	// 21.05.27 'blog_category_no' 혹은 'blog_no'에 해당하는 목록 내용 가져오기 (전체/공개)
	public List<BlogDTO> selectListDetailByNoFromBlog_object(PageUtil listInfo) {
		return blogDAO.selectListDetailByNoFromBlog_object(listInfo);
	}
	
	// 21.05.27 'blog_category_no' 혹은 'blog_no'에 해당하는 게시글 내용 가져오기 (전체/공개)
	public List<BlogDTO> selectObjectDetailByNoFromBlog_object(PageUtil pageInfo) {
		return blogDAO.selectObjectDetailByNoFromBlog_object(pageInfo);
	}

	// 21.06.10 'blog_no'와 'blog_object_no'에 일치하는 'blog_object' 가져오기
	public BlogDTO selectBlog_object(int blog_no, int blog_object_no, String visitor_type) {
		Map<String, Object> map = new HashMap<>();
		map.put("blog_no", blog_no);
		map.put("blog_object_no", blog_object_no);
		map.put("visitor_type", visitor_type);
		
		BlogDTO blogDTO = blogDAO.selectBlog_object(map);
		
		// 21.06.14 'blog_category_no'에 해당하는 'category_name' 가져와서 set
		blogDTO.setCategory_name(blogDAO.selectCategory_nameByBlog_category_noFromBlog_category(blogDTO.getBlog_category_no()));
		
		return blogDTO;
	}

	
	
	// 'blog_comment' table ===================================================
	// 21.07.03 'blog_no'에 해당하는 오늘의 댓글수 가져오기
	public int todayCommentCount(int blog_no) {
		return blogDAO.todayCommentCount(blog_no);
	}
	
	
	
	// 테이블 join ====================================================================================
	// 'blog_comment' & 'blog_object' table ==============================================
	// 'blog_no'에 해당하는 블로그 댓글과 해당하는 댓글의 글 번호의 글제목 가져오기
	public List<CommentDTO> selectCommentByBlog_noFromBlog_comment(int blog_no) {
		return blogDAO.selectCommentByBlog_noFromBlog_comment(blog_no);
	}

	
	
	// 'blog_visit' & 'blog_object' table ============================================
	// 21.07.13 특정기간(원하는 일자 ~ 포함되지 않는 일자: 일간/주간/월간)에 해당하는 조회수 순위(+ 제목, 글번호, 조회수, 작성일)
	public List<BlogDTO> hitRankDuringFromBlog_visitAndBlog_object(String startDate_str, String endDate_str, int blog_no, String period){
		if(startDate_str.length() == 0 || startDate_str.equals("") || endDate_str.length() == 0 || endDate_str.equals("")) {
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
			
			if(period.equals("daily")) {
				LocalDate startDate = LocalDate.now();
				startDate_str = startDate.format(dateTimeFormatter); // 오늘
				endDate_str = startDate.plusDays(1).format(dateTimeFormatter); // 내일
			}else if(period.equals("weekly")) {
				LocalDate endDate = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)); // 가장 최근의 지난 일요일
				endDate_str = endDate.format(dateTimeFormatter);
				startDate_str = endDate.minusDays(6).format(dateTimeFormatter); // 가장 최근의 지난 일요일의 가장 최근의 지난 월요일
			}else { // period.equals("monthly")
				LocalDate lastMonth = LocalDate.now().minusMonths(1); // 지난달
				startDate_str = lastMonth.with(TemporalAdjusters.firstDayOfMonth()).format(dateTimeFormatter); // 지난달 첫째 날 
				endDate_str = lastMonth.with(TemporalAdjusters.lastDayOfMonth()).format(dateTimeFormatter); // 지난달 마지막 날
			}
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("startDate", startDate_str);
		map.put("endDate", endDate_str);
		map.put("blog_no", blog_no);
		
		List<BlogDTO> list = blogDAO.hitRankDuringFromBlog_visitAndBlog_object(map);
		
		if(list == null || list.size() == 0) return null;
		
		// 조회수 순으로 내림차순
		Collections.sort(list, new Comparator<BlogDTO>() {
			@Override
			public int compare(BlogDTO o1, BlogDTO o2) {
				return o2.getHits() - o1.getHits();
			}
		});
		
		// 조회수 순위 매기기(조회수가 같은 것들은 같은 순위)
		int rank = 1;
		int nextRank = 2;
		list.get(0).setRank(rank);
		
		for(int i=1; i<list.size(); i++) {
			if(list.get(i).getHits() < list.get(i-1).getHits()) {
				rank = nextRank++;
			}else { // 이전 것과 조회수가 같은 경우
				nextRank++;
			}
			list.get(i).setRank(rank);
		}
		
		return list;
	}
	
	
}
