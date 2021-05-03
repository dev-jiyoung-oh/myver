package com.project.myver.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.project.myver.dao.FileDAO;
import com.project.myver.dto.FileDTO;

public class FileService {

	@Autowired
	private FileDAO fileDAO;
	
	// 21.05.03 파일 업로드
	public String upload(MultipartFile mf, int area, String id) throws IllegalStateException, IOException {
		StringBuffer path = new StringBuffer();
		StringBuffer saveName = new StringBuffer();
		
		path.append("D:\\jy_project\\myver\\workspace\\upload\\");
		
		switch(area) {
		case 0:
			path.append("0_memo\\");
			break;
		case 1:
			path.append("1_blog\\");
			break;
		case 2:
			path.append("2_cafe\\");
			break;
		}

		// 파일저장이름 : 날짜_아이디_파일원래이름
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String dateStr = sdf.format(Calendar.getInstance().getTime());
		saveName.append(dateStr);
		saveName.append("_");
		saveName.append(id);
		saveName.append("_");
		saveName.append(mf.getOriginalFilename());
		path.append(saveName);
		
		mf.transferTo(new File(path.toString()));
		
		return saveName.toString();
	}

	// 21.05.03 첨부파일 테이블에 데이터 삽입하고 파일번호 가져오기
	public int insert(FileDTO fileDTO) {
		int fileNo = fileDAO.insert(fileDTO);
		return fileNo;
	}

	// 21.05.03 첨부파일 번호로 해당 레코드의 크기 가져오기
	public int selectRecordSize(int fileNo) {
		int recordSize = fileDAO.selectRecordSize(fileNo);
		return recordSize;
	}
	
}
