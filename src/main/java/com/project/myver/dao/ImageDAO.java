package com.project.myver.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.myver.dto.ImageDTO;

public class ImageDAO extends SqlSessionDaoSupport {
	@Autowired
	SqlSessionTemplate session;

	// 21.05.25 'image_no'로 'path', 'saved_name' 가져오기
	public ImageDTO selectPathAndSaved_nameFromImage(int image_no) {
		return session.selectOne("img.selectPathAndSaved_nameFromImage", image_no);
	}
	
	
	// 21.05.25 데이터 삽입하고 'file_no' 가져오기
	public int insert(ImageDTO imgDTO) {
		session.insert("img.insertImage", imgDTO);
		int image_no = imgDTO.getImage_no();
		return image_no;
	}


}
