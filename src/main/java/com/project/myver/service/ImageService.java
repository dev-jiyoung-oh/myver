package com.project.myver.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.project.myver.dao.ImageDAO;
import com.project.myver.dto.ImageDTO;

public class ImageService {

	@Autowired
	private ImageDAO imgDAO;
	
	// 21.05.23 이미지 업로드
	public String[] upload(MultipartFile mf, int area, String id) throws IllegalStateException, IOException {
		StringBuffer path = new StringBuffer();
		StringBuffer sb_name = new StringBuffer();
		String saved_path = "";
		
		path.append("D:\\jy_project\\myver\\workspace\\image\\");
		
		switch(area) {
		case 0:
			path.append("0_memo\\");
			saved_path = "0_memo\\";
			break;
		case 1:
			path.append("1_blog\\");
			saved_path = "1_blog\\";
			break;
		case 2:
			path.append("2_cafe\\");
			saved_path = "2_cafe\\";
			break;
		}

		// 파일저장이름 : 날짜_아이디_파일원래이름
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String dateStr = sdf.format(Calendar.getInstance().getTime());
		sb_name.append(dateStr);
		sb_name.append("_");
		sb_name.append(id);
		sb_name.append("_");
		sb_name.append(mf.getOriginalFilename());
		
		String saved_name = renameTo(path.toString(), sb_name.toString());
		path.append(saved_name);
		
		mf.transferTo(new File(path.toString()));
		
		return new String[]{saved_name, saved_path};
	}
	
	// 21.05.23 동일한 이름의 파일이 이미 존재할 시, 현재 파일 이름 변경 (-> 업어쓰기가 되지 않도록 방지)
	public String renameTo(String path, String name) {
		
		// 만약 같은 이름의 파일이 존재하면 현재 파일이름 다음에 (번호) 를 붙여서 이름을 바꾸도록 한다.
		String	tempName = name;	//	현재 이름을 기억
		int		cnt = 0;			//	옆에 붙일 번호를 기억할 변수
		
		// 현재 저장할 이름을 File 클래스로 바꿔놓고
		File	file = new File(path, tempName);
		
		// 파일이 존재하는지 확인 및 존재시 이름 변경
		while(file.exists()) {
			// 파일의 이름과 확장자를 분리
			int	index = name.lastIndexOf(".");
			String	fileName = name.substring(0, index);		//	sam
			String	extName = name.substring(index + 1);		//	txt
			
			// 붙일 번호를 하나 증가
			cnt++;
			
			// 파일 이름 뒤에 이 번호를 붙인다.
			fileName = fileName + "(" + cnt + ")";				//	sam(1)
			
			//	이 결과에 확장자까지 붙여서 완전한 파일 이름을 다시 만든다.
			tempName = fileName + "." + extName;				//	sam(1).txt
			
			//	이름이 또 있을 수 있으므로 다시검사
			file = new File(path, tempName);
		}
		return tempName;
	}
	
	
	
	// 'image' table =======================================================
	// 21.05.25 'image_no'로 'path', 'saved_name' 가져오기
	public ImageDTO selectPathAndSaved_nameFromImage(int image_no) {
		return imgDAO.selectPathAndSaved_nameFromImage(image_no);
	}
	
	// 21.05.25 데이터 삽입하고 이미지 번호 가져오기
	public int insert(ImageDTO imgDTO) {
		int image_no = imgDAO.insert(imgDTO);
		return image_no;
	}

	
}
