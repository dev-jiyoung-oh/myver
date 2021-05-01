package com.project.myver.dto;

import java.sql.Date;

public class MemoDTO {
	
	private int memo_no;		  // 쪽지 번호
	private String writer_id;	  // 작성자 아이디
	private String writer_name;	  // 작성자 이름
	private String receiver_id;	  // 수신자 아이디
	private String receiver_name; // 수신자 이름
	private String title;		  // 제목
	private String content;		  // 내용
	private Date date;			  // 작성일자
	private int is_read;		  // 읽음 여부 (0:안읽음 / 1:읽음)
	private int has_file;		  // 첨부파일 유무 (0:없음 / 1:있음)
	private Double memo_size;		  // 쪽지 크기
	
	
	public int getMemo_no() {
		return memo_no;
	}
	public void setMemo_no(int memo_no) {
		this.memo_no = memo_no;
	}
	public String getWriter_id() {
		return writer_id;
	}
	public void setWriter_id(String writer_id) {
		this.writer_id = writer_id;
	}
	public String getWriter_name() {
		return writer_name;
	}
	public void setWriter_name(String writer_name) {
		this.writer_name = writer_name;
	}
	public String getReceiver_id() {
		return receiver_id;
	}
	public void setReceiver_id(String receiver_id) {
		this.receiver_id = receiver_id;
	}
	public String getReceiver_name() {
		return receiver_name;
	}
	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getIs_read() {
		return is_read;
	}
	public void setIs_read(int is_read) {
		this.is_read = is_read;
	}
	public int getHas_file() {
		return has_file;
	}
	public void setHas_file(int has_file) {
		this.has_file = has_file;
	}
	public Double getMemo_size() {
		return memo_size;
	}
	public void setMemo_size(Double memo_size) {
		this.memo_size = memo_size;
	}
	
	
	@Override
	public String toString() {
		return "MemoDTO [memo_no=" + memo_no + ", writer_id=" + writer_id + ", writer_name=" + writer_name
				+ ", receiver_id=" + receiver_id + ", receiver_name=" + receiver_name + ", title=" + title
				+ ", content=" + content + ", date=" + date + ", is_read=" + is_read + ", has_file=" + has_file
				+ ", memo_size=" + memo_size + "]";
	}
}
