package com.project.myver.dto;

import java.sql.Date;
import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

public class MemoDTO {
	
	// 기타
	//private MultipartFile[] file_array;	// 파일 배열
	
	// 'memo' table
	private int memo_no = -1;		  // 쪽지 번호
	private String writer_id;	  // 작성자 아이디
	private String writer_name;	  // 작성자 이름
	private String receiver_id;	  // 수신자 아이디
	private String receiver_name; // 수신자 이름
	private String title;		  // 제목
	private String content;		  // 내용
	private Date date;			  // 작성일자
	private int has_file;		  // 첨부파일 유무 (0:없음 / 1:있음)
	private double memo_size;	  // 쪽지 크기
	
	// 'my_memo' table
	private int my_memo_mo;		  // 나의 쪽지 번호
	private int member_no;		  // 회원 번호
	private int is_important;	  // 중요 여부 (0:중요X / 1:중요)
	private int is_read;		  // 읽음 여부 (0:안읽음 / 1:읽음)
	private int box;			  // 보관함 (개인0/카페1//보낸2/내게3/보관4/스팸5/휴지통6)
	
	// 'memo_file' table
	private int memo_file_no;	  // 쪽지 첨부파일 번호
	private int file_index;		  // 첨부 순서
	private int file_no;		  // 파일 번호
	
	// 'memo_set' table
	private int memo_set_no;	  // 쪽지 설정 번호
	
	
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
	public int getHas_file() {
		return has_file;
	}
	public void setHas_file(int has_file) {
		this.has_file = has_file;
	}
	public double getMemo_size() {
		return memo_size;
	}
	public void setMemo_size(double memo_size) {
		this.memo_size = memo_size;
	}
	public int getMy_memo_mo() {
		return my_memo_mo;
	}
	public void setMy_memo_mo(int my_memo_mo) {
		this.my_memo_mo = my_memo_mo;
	}
	public int getMember_no() {
		return member_no;
	}
	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}
	public int getIs_important() {
		return is_important;
	}
	public void setIs_important(int is_important) {
		this.is_important = is_important;
	}
	public int getIs_read() {
		return is_read;
	}
	public void setIs_read(int is_read) {
		this.is_read = is_read;
	}
	public int getBox() {
		return box;
	}
	public void setBox(int box) {
		this.box = box;
	}
	public int getMemo_file_no() {
		return memo_file_no;
	}
	public void setMemo_file_no(int memo_file_no) {
		this.memo_file_no = memo_file_no;
	}
	public int getFile_index() {
		return file_index;
	}
	public void setFile_index(int file_index) {
		this.file_index = file_index;
	}
	public int getFile_no() {
		return file_no;
	}
	public void setFile_no(int file_no) {
		this.file_no = file_no;
	}
	public int getMemo_set_no() {
		return memo_set_no;
	}
	public void setMemo_set_no(int memo_set_no) {
		this.memo_set_no = memo_set_no;
	}
	/*public MultipartFile[] getFile_array() {
		return file_array;
	}
	public void setFile_array(MultipartFile[] file_array) {
		this.file_array = file_array;
	}*/
	
	
	
	// toString()
	public String memoToString() { // 'memo' table toString
		return "MemoDTO - memo [memo_no=" + memo_no + ", writer_id=" + writer_id + ", writer_name=" + writer_name
				+ ", receiver_id=" + receiver_id + ", receiver_name=" + receiver_name + ", title=" + title
				+ ", content=" + content + ", date=" + date + ", has_file=" + has_file
				+ ", memo_size=" + memo_size + ", file_array=" + "]";
	}
	
	public String my_memoToString() { // 'my_memo" table toString
		return "MemoDTO - my_memo [my_memo_mo=" + my_memo_mo + ", member_no=" + member_no + ", memo_no=" + memo_no
				+ ", is_important=" + is_important + ", is_read=" + is_read + ", box=" + box + "]";
	}
	
	public String memo_fileToString() { // 'memo_file' table toString
		return "MemoDTO - memo_file [memo_file_no=" + memo_file_no + ", memo_no=" + memo_no
				+ ", file_index" + file_index + ", file_no" + file_no + "]";
	}
	
	public String memo_setToString() { // 'memo_set' table toString
		return "MemoDTO - memo [memo_set_no=" + memo_set_no + ", member_no=" + member_no 
				+ ", writer_name=" + writer_name + "]";
	}
	
	@Override
	public String toString() {
		return "MemoDTO [memo_no=" + memo_no + ", writer_id=" + writer_id + ", writer_name=" + writer_name
				+ ", receiver_id=" + receiver_id + ", receiver_name=" + receiver_name + ", title=" + title
				+ ", content=" + content + ", date=" + date + ", has_file=" + has_file + ", memo_size=" + memo_size
				+ ", my_memo_mo=" + my_memo_mo + ", member_no=" + member_no + ", is_important=" + is_important
				+ ", is_read=" + is_read + ", box=" + box + ", memo_file_no=" + memo_file_no + ", file_index=" + file_index
				+ ", file_no=" + file_no + ", memo_set_no=" + memo_set_no + "]";
	}
}
