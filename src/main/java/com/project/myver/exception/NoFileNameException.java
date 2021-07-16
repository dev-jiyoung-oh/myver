package com.project.myver.exception;

// 21.07.16 파일 업로드 시 파일의 제목이 없는 경우(즉, 파일이 빈 경우) 발생시키는 예외 클래스

public class NoFileNameException extends Exception {
	public NoFileNameException() {
		super();
		System.out.println("NoFileNameException 발생");
	}
}
