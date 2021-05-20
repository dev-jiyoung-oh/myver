package com.project.myver.dto;

import java.sql.Date;

public class ImageDTO {
	
	private int image_no;			//이미지 번호
	private int area;				//영역(쪽지:1, 블로그:2, 카페:3)
	private String path;			//파일 저장 위치
	private String saved_name;		//파일 저장 이름
	private String original_name;	//파일 본래 이름
	private int represent;			//대표 여부(아님0, 맞음:1)
	private double image_size;		//그림 크기
	private Date date;				//등록일
	private int can_delete;			//삭제 가능 여부(불가능0, 가능1)
	
	
	// Getters and Setters
	public int getImage_no() {
		return image_no;
	}
	public void setImage_no(int image_no) {
		this.image_no = image_no;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
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
	public String getOriginal_name() {
		return original_name;
	}
	public void setOriginal_name(String original_name) {
		this.original_name = original_name;
	}
	public int getRepresent() {
		return represent;
	}
	public void setRepresent(int represent) {
		this.represent = represent;
	}
	public double getImage_size() {
		return image_size;
	}
	public void setImage_size(double image_size) {
		this.image_size = image_size;
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
	
	
	//toString()
	@Override
	public String toString() {
		return "ImageDTO [image_no=" + image_no + ", area=" + area + ", path=" + path + ", saved_name=" + saved_name
				+ ", original_name=" + original_name + ", represent=" + represent + ", image_size=" + image_size
				+ ", date=" + date + ", can_delete=" + can_delete + "]";
	}
	
}
