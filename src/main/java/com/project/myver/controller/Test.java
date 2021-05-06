package com.project.myver.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException { 
		String text = "[InternetShortcut]\r\n\r\nURL=http://www.naver.com"; //내보낼 경로와 이름을 지정한다. 
		System.out.println(text.getBytes().length);
		FileOutputStream fileOutfutStream = new FileOutputStream(new File("D:\\jy_project\\myver\\workspace\\upload\\naver.url")); 
		// FileOutputStream은 값을 사용할 때는 byte로 변환하여 사용해야 합니다. 
		byte[] buf = text.getBytes(); 
		fileOutfutStream.write(buf); // 내용을 입력한다. 
		fileOutfutStream.flush(); //버퍼의 내용을 비우는 역할을 한다. 
		fileOutfutStream.close(); //FileOutputStream을 닫아준다. (중요!)



	}

}
