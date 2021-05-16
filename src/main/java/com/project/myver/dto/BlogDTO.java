package com.project.myver.dto;

import java.sql.Date;

public class BlogDTO {
	
	// 'blog' table
	private int blog_no;			//블로그 번호
	private int member_no;			//회원번호
	private String blog_title;		//블로그명
	private String nick;			//별명
	private String info;			//블로그 소개글
	private String blog_topic;		//블로그 주제
	private int blog_img_no;		//블로그 소개 이미지 번호
	
	// 'blog_category' table
	private int blog_category_no;	//블로그 카테고리 번호
	private int parent_category_no;	//부모 카테고리 번호
	private int category_index;		//카테고리 순서
	private String category_name;	//카테고리명
	private int is_public;			//공개여부(공개:0, 비공개:1)
	private String topic;			//카테고리 주제
	private int type;				//유형(블로그형:0, 앨범형:1)
	private int show_list;			//목록열기여부(열기:0, 닫기:1)
	private int show_list_line;		//목록의 글 개수(5/10/15/20/30)
	private int is_basic;			//대표 여부(아님:0, 대표:1)
	private int is_upper;			//블로그 상단 표시 여부(아님:0, 표시:1)
	
	// 'blog_visit' table
	private int blog_visit_no;		//블로그 방문 번호
	private int visitor_no;			//방문자 번호
	private String search_word;		//검색 단어(검색 유입 경로)
	private Date date;				//방문일
}
