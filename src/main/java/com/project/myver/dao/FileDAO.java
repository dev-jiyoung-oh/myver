package com.project.myver.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.myver.dto.FileDTO;

public class FileDAO extends SqlSessionDaoSupport {
	@Autowired
	SqlSessionTemplate session;

	// 21.05.03 데이터 삽입하고 'file_no' 가져오기
	public int insert(FileDTO fileDTO) {
		session.insert("file.insertFile", fileDTO);
		int file_no = fileDTO.getFile_no();
		return file_no;
	}

	// 21.05.03 'file_no'로 해당 레코드의 크기 가져오기
	public double selectRecordSize(int file_no) {
		double recordSize = session.selectOne("file.selectRecordSize", file_no);
		return recordSize;
	}

	
	// 테이블 조인 ============================================================
	// 21.05.13 'memo_file'테이블과 'file'테이블 조인 - 'memo_file.memo_no'에 해당하는 데이터 가져오기
	public List<FileDTO> selectMemofileAndFile(int memo_no) {
		List<FileDTO> fileList = session.selectList("file.selectMemofileAndFile", memo_no);
		return fileList;
	}
}
