package com.project.myver.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.myver.dto.FileDTO;

public class FileDAO extends SqlSessionDaoSupport {
	@Autowired
	SqlSessionTemplate session;

	// 21.05.03 데이터 삽입하고 'file_no' 가져오기
	public int insert(FileDTO fileDTO) {
		int file_no = session.insert("file.insertFile", fileDTO);
		return file_no;
	}

	// 21.05.03 'file_no'로 해당 레코드의 크기 가져오기
	public double selectRecordSize(int file_no) {
		double recordSize = session.selectOne("file.selectRecordSize", file_no);
		return recordSize;
	}
}
