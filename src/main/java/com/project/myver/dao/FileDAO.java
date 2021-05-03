package com.project.myver.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.myver.dto.FileDTO;

public class FileDAO extends SqlSessionDaoSupport {
	@Autowired
	SqlSessionTemplate session;

	// 21.05.03 첨부파일 테이블에 데이터 삽입하고 파일번호 가져오기
	public int insert(FileDTO fileDTO) {
		int fileNo = session.insert("file.insertFile", fileDTO);
		return fileNo;
	}

	// 21.05.03 첨부파일 번호로 해당 레코드의 크기 가져오기(미완)
	public int selectRecordSize(int fileNo) {
		int recordSize = session.selectOne("file.selectRecordSize", fileNo);
		return recordSize;
	}
}
