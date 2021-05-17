package com.project.myver.dto;

import java.sql.Date;

public class CommentDTO {

	// 'blog_comment' table
	private int blog_comment_no;		//블로그 댓글 번호
	private int blog_object_no;			//블로그 글 번호
	
	// 'blog_comment' table & 'cafe_comment'table
	private int parent_comment_no;		//부모 댓글 번호
	private int comment_index;			//댓글 순서
	private String id;					//작성자 아이디
	private String nick;				//작성자 닉네임
	private String comment;				//댓글 내용
	private Date date;					//작성일
	private int likes;					//댓글 좋아요수
	
	// 'blog_comment_like' table
	private int blog_comment_like_no;	//블로그 댓글 좋아요 번호

	
	
	// Getters and Setters
	public int getBlog_comment_no() {
		return blog_comment_no;
	}

	public void setBlog_comment_no(int blog_comment_no) {
		this.blog_comment_no = blog_comment_no;
	}

	public int getBlog_object_no() {
		return blog_object_no;
	}

	public void setBlog_object_no(int blog_object_no) {
		this.blog_object_no = blog_object_no;
	}

	public int getParent_comment_no() {
		return parent_comment_no;
	}

	public void setParent_comment_no(int parent_comment_no) {
		this.parent_comment_no = parent_comment_no;
	}

	public int getComment_index() {
		return comment_index;
	}

	public void setComment_index(int comment_index) {
		this.comment_index = comment_index;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getBlog_comment_like_no() {
		return blog_comment_like_no;
	}

	public void setBlog_comment_like_no(int blog_comment_like_no) {
		this.blog_comment_like_no = blog_comment_like_no;
	}

	@Override
	public String toString() {
		return "CommentDTO - blog [blog_comment_no=" + blog_comment_no + ", blog_object_no=" + blog_object_no
				+ ", parent_comment_no=" + parent_comment_no + ", comment_index=" + comment_index + ", id=" + id
				+ ", nick=" + nick + ", comment=" + comment + ", date=" + date + ", likes=" + likes
				+ ", blog_comment_like_no=" + blog_comment_like_no + "]";
	}
	
}
