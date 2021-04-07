package com.project.myver;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestMySQL {
	public static void main(String[] args) {
		try {
		    Class.forName("com.mysql.jdbc.Driver");
		} 
		catch (ClassNotFoundException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		
		Connection conn;
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/myver?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","jiyoung","1234");
			System.out.println("데이터베이스 연결 성공");
			conn.close();
		}catch(Exception e) {
			System.out.println("오류:"+e);
		}
	}
}
