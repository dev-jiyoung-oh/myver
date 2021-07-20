package com.project.myver.dto;

import java.sql.Date;
import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

public class FileDTO {
	
	// constructor(생성자)
	public FileDTO() {}
	
	public FileDTO(int area, String original_name, String saved_name, String path, double file_size) {
		this.area = area;
		this.original_name = original_name;
		this.saved_name = saved_name;
		this.path = path;
		this.file_size = file_size;
	}



	// 'file' table
	private int file_no;			// 파일 번호
	private int  area;				// 영역 (쪽지1/블로그2/카페3)
	private String original_name;	// 파일 본래이름
	private String saved_name;		// 파일 저장명
	private String path;			// 파일 저장 위치
	private double file_size;		// 파일 크기
	private Date date;				// 등록일
	private int can_delete;			// 삭제 가능 여부 (불가능0/가능1)
	
	
	
	// get&set method
	public int getFile_no() {
		return file_no;
	}
	public void setFile_no(int file_no) {
		this.file_no = file_no;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public String getSaved_name() {
		return saved_name;
	}
	public void setSaved_name(String saved_name) {
		this.saved_name = saved_name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getOriginal_name() {
		return original_name;
	}
	public void setOriginal_name(String original_name) {
		this.original_name = original_name;
	}
	public double getFile_size() {
		return file_size;
	}
	public void setFile_size(double file_size) {
		this.file_size = file_size;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getCan_delete() {
		return can_delete;
	}
	public void setCan_delete(int can_delete) {
		this.can_delete = can_delete;
	}
	

	
	// toString()
	@Override
	public String toString() {
		return "FileDTO [file_no=" + file_no + ", area=" + area
				+ ", original_name=" + original_name + ", saved_name=" + saved_name + ", path=" + path + ", file_size="
				+ file_size + ", date=" + date + ", can_delete=" + can_delete + "]";
	}
	
}
