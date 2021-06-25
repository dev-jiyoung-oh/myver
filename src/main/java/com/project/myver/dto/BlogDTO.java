package com.project.myver.dto;

import java.sql.Date;

public class BlogDTO {
	
	// Constructor
	// 기본 생성자
	public BlogDTO(){}
	
	// 기타
	private CommentDTO commentDTO;	//블로그 댓글
	
	// 'blog' table
	private int blog_no;			//블로그 번호
	private int member_no;			//회원번호
	private String blog_id;			//회원 아이디
	private String blog_title;		//블로그명
	private String blog_nick;		//별명
	private String blog_info;		//블로그 소개글
	private String blog_topic;		//블로그 주제
	private int blog_img_no;		//블로그 소개 이미지 번호
	private int objects_per_page; 	//페이지당 글 개수(1,3,5,10)
	
	// 'blog_category' table
	private int blog_category_no;	//블로그 카테고리 번호
	private int parent_category_no;	//부모 카테고리 번호
	private int category_index;		//카테고리 순서
	private String category_name;	//카테고리명
	private int is_public;			//공개여부(공개:0, 비공개:1)
	private String topic;			//카테고리 주제
	private int type;				//유형(블로그형:0, 앨범형:1)
	private int show_list;			//목록열기여부(열기:0, 닫기:1)
	private int list_line;		//목록의 글 개수(닫은 경우:0, 연 경우:5/10/15/20/30)
	private int is_basic;			//대표 여부(아님:0, 대표:1)
	private int is_upper;			//블로그 상단 표시 여부(아님:0, 표시:1)
	private int all_category;		//카테고리 전체보기 여부(아님:0, 맞음:1)
	
	// 'blog_visit' table
	private int blog_visit_no;		//블로그 방문 번호
	private int visitor_no;			//방문자 번호
	private String query;		//검색 단어(검색 유입 경로)
	private Date date;				//방문일
	//private int blog_object_no;	//유입된 블로그 글 번호
	
	// 'blog_object' table
	private int blog_object_no;		//글 번호
	private String title;			//제목
	private String content;			//내용
	//private Date date;			//작성일
	//private int is_public;		//공개 여부(0:비공개, 1:공개)
	private int hits;				//조회수
	private int likes;				//좋아요수
	
	// 'blog_object_like' table
	private int blog_object_like_no;//블로그 글 좋아요 번호
	private String id;				//좋아요 누른 id
	//private Date date;			//날짜
	
	// 'blog_neighbor' table
	private int blog_neighbor_no;	//블로그 이웃 번호
	//private int member_no;		//회원 번호
	private int neighbor_member_no;	//이웃 회원 번호
	
	// 블로그 이미지 관련 정보
	private String path;			//저장경로
	private String saved_name;		//저장명
	
	
	
	// Getters and Setters
	public int getBlog_no() {
		return blog_no;
	}
	public void setBlog_no(int blog_no) {
		this.blog_no = blog_no;
	}
	public int getMember_no() {
		return member_no;
	}
	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}
	public String getBlog_id() {
		return blog_id;
	}
	public void setBlog_id(String blog_id) {
		this.blog_id = blog_id;
	}
	public String getBlog_title() {
		return blog_title;
	}
	public void setBlog_title(String blog_title) {
		this.blog_title = blog_title;
	}
	public String getBlog_nick() {
		return blog_nick;
	}
	public void setBlog_nick(String blog_nick) {
		this.blog_nick = blog_nick;
	}
	public String getBlog_info() {
		return blog_info;
	}
	public void setBlog_info(String blog_info) {
		this.blog_info = blog_info;
	}
	public String getBlog_topic() {
		return blog_topic;
	}
	public void setBlog_topic(String blog_topic) {
		this.blog_topic = blog_topic;
	}
	public int getBlog_img_no() {
		return blog_img_no;
	}
	public void setBlog_img_no(int blog_img_no) {
		this.blog_img_no = blog_img_no;
	}
	public int getBlog_category_no() {
		return blog_category_no;
	}
	public void setBlog_category_no(int blog_category_no) {
		this.blog_category_no = blog_category_no;
	}
	public int getParent_category_no() {
		return parent_category_no;
	}
	public void setParent_category_no(int parent_category_no) {
		this.parent_category_no = parent_category_no;
	}
	public int getCategory_index() {
		return category_index;
	}
	public void setCategory_index(int category_index) {
		this.category_index = category_index;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public int getIs_public() {
		return is_public;
	}
	public void setIs_public(int is_public) {
		this.is_public = is_public;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getShow_list() {
		return show_list;
	}
	public void setShow_list(int show_list) {
		this.show_list = show_list;
	}
	public int getList_line() {
		return list_line;
	}
	public void setList_line(int list_line) {
		this.list_line = list_line;
	}
	public int getObjects_per_page() {
		return objects_per_page;
	}
	public void setObjects_per_page(int objects_per_page) {
		this.objects_per_page = objects_per_page;
	}
	public int getIs_basic() {
		return is_basic;
	}
	public void setIs_basic(int is_basic) {
		this.is_basic = is_basic;
	}
	public int getIs_upper() {
		return is_upper;
	}
	public void setIs_upper(int is_upper) {
		this.is_upper = is_upper;
	}
	public int getAll_category() {
		return all_category;
	}
	public void setAll_category(int all_category) {
		this.all_category = all_category;
	}
	public int getBlog_visit_no() {
		return blog_visit_no;
	}
	public void setBlog_visit_no(int blog_visit_no) {
		this.blog_visit_no = blog_visit_no;
	}
	public int getVisitor_no() {
		return visitor_no;
	}
	public void setVisitor_no(int visitor_no) {
		this.visitor_no = visitor_no;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getBlog_object_no() {
		return blog_object_no;
	}
	public void setBlog_object_no(int blog_object_no) {
		this.blog_object_no = blog_object_no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public int getBlog_object_like_no() {
		return blog_object_like_no;
	}
	public void setBlog_object_like_no(int blog_object_like_no) {
		this.blog_object_like_no = blog_object_like_no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getBlog_neighbor_no() {
		return blog_neighbor_no;
	}
	public void setBlog_neighbor_no(int blog_neighbor_no) {
		this.blog_neighbor_no = blog_neighbor_no;
	}
	public int getNeighbor_member_no() {
		return neighbor_member_no;
	}
	public void setNeighbor_member_no(int neighbor_member_no) {
		this.neighbor_member_no = neighbor_member_no;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getSaved_name() {
		return saved_name;
	}
	public void setSaved_name(String saved_name) {
		this.saved_name = saved_name;
	}
	public CommentDTO getCommentDTO() {
		return commentDTO;
	}
	public void setCommentDTO(CommentDTO commentDTO) {
		this.commentDTO = commentDTO;
	}
	
	
	@Override
	public String toString() {
		return "BlogDTO [blog_no=" + blog_no + ", member_no=" + member_no + ", blog_id=" + blog_id + ", blog_title="
				+ blog_title + ", blog_nick=" + blog_nick + ", blog_info=" + blog_info + ", blog_topic=" + blog_topic
				+ ", blog_img_no=" + blog_img_no + ", blog_category_no=" + blog_category_no + ", parent_category_no="
				+ parent_category_no + ", category_index=" + category_index + ", category_name=" + category_name
				+ ", is_public=" + is_public + ", topic=" + topic + ", type=" + type + ", show_list=" + show_list
				+ ", list_line=" + list_line + ", objects_per_page=" + objects_per_page + ", is_basic=" + is_basic
				+ ", is_upper=" + is_upper + ", all_category=" + all_category + ", blog_visit_no=" + blog_visit_no
				+ ", visitor_no=" + visitor_no + ", query=" + query + ", date=" + date + ", blog_object_no="
				+ blog_object_no + ", title=" + title + ", content=" + content + ", hits=" + hits + ", likes=" + likes
				+ ", blog_object_like_no=" + blog_object_like_no + ", id=" + id + ", blog_neighbor_no="
				+ blog_neighbor_no + ", neighbor_member_no=" + neighbor_member_no + ", path=" + path + ", saved_name="
				+ saved_name + "]";
	}	
	
	public String blogToString() {
		return "BlogDTO - blog [blog_no=" + blog_no + ", member_no=" + member_no + ", blog_id=" + blog_id + ", blog_title=" + blog_title + ", blog_nick="
				+ blog_nick + ", blog_info=" + blog_info + ", blog_topic=" + blog_topic + ", blog_img_no=" + blog_img_no 
				+ ", objects_per_page=" + objects_per_page + ", path=" + path + ", saved_name=" + saved_name + "]";
	}
	
	public String blog_categoryToString() {
		return "BlogDTO - blog_category [blog_category_no=" + blog_category_no + ", blog_no=" + blog_no
				+ ", category_index=" + category_index + ", parent_category_no=" + parent_category_no 
				+ ", category_name=" + category_name + ", is_public=" + is_public + ", topic=" + topic 
				+ ", type=" + type + ", show_list=" + show_list + ", list_line=" + list_line 
				+ ", is_basic=" + is_basic + ", is_upper=" + is_upper + ", all_category=" + all_category + "]";
	}
	
	public String blog_visitToString() {
		return "BlogDTO - blog_visit [blog_visit_no=" + visitor_no +", blog_no=" + blog_no
				+ ", visitor_no=" + visitor_no + ", query=" + query + ", date=" + date
				+ ", blog_object_no=" + blog_object_no + "]";
	}
	
	public String blog_objectToString() {
		return "BlogDTO - blog_object [blog_object_no=" + blog_object_no+ ", blog_category_no=" + blog_category_no 
				+ ",category_name= " + category_name + ", title=" + title + ", content=" + content + ", date=" + date
				+ ", is_public=" + is_public + ", hits=" + hits + ", likes=" + likes + "]";
	}
	
	public String blog_object_likeToString() {
		return "BlogDTO - blog_object_like [blog_object_like_no=" + blog_object_like_no
				+ ", blog_object_no=" + blog_object_no + ", id=" + id + ", date=" + date + "]";
	}
	
	public String blog_neighborToString() {
		return "BlogDTO - blog_neighbor [blog_neighbor_no=" + blog_neighbor_no 
				+ "member_no=" + member_no + ", neighbor_member_no=" + neighbor_member_no + "]";
	}
}
