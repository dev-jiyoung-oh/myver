package com.project.myver.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.project.myver.dao.FileDAO;
import com.project.myver.dto.FileDTO;

public class FileService {

	@Autowired
	private FileDAO fileDAO;
	
	// 21.05.03 파일 업로드
	public String[] upload(MultipartFile mf, int area, String id) throws IllegalStateException, IOException {
		StringBuffer path = new StringBuffer();
		StringBuffer sb_name = new StringBuffer();
		String saved_path = "";
		
		path.append("D:\\jy_project\\myver\\workspace\\upload\\");
		
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
	
	// 21.05.06 동일한 이름의 파일이 이미 존재할 시, 현재 파일 이름 변경 (-> 업어쓰기가 되지 않도록 방지)
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
	
	// 21.05.06 url 바로가기 파일 생성 (미완)
	public FileDTO uploadUrlFile(int memo_no, String original_name) throws IOException {
		
		String path = "D:\\jy_project\\myver\\workspace\\upload\\0_memo\\";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String dateStr = sdf.format(Calendar.getInstance().getTime());
		
		String saved_name = renameTo(path, dateStr + "_myver_" + original_name);
		String content = "[InternetShortcut]\r\n\r\n"
				+ "URL=http://localhost:8080/myver/memo/popup/mn="+memo_no; 
		
		FileOutputStream fileOutfutStream = new FileOutputStream(new File(path+saved_name)); 
		
		// FileOutputStream은 값을 사용할 때는 byte로 변환하여 사용
		byte[] buf = content.getBytes(); 
		
		fileOutfutStream.write(buf); // 내용 입력
		
		fileOutfutStream.flush(); // 버퍼의 내용을 비우는 역할 
		fileOutfutStream.close(); // FileOutputStream을 닫아준다.(중요!)
		
		// 파일사이즈, 파일 저장명 리턴
		return new FileDTO(0, original_name, saved_name, path, content.getBytes().length);
	}
	
	
	// 'file' table =======================================================
	// 21.05.03 첨부파일 테이블에 데이터 삽입하고 파일번호 가져오기
	public int insert(FileDTO fileDTO) {
		int file_no = fileDAO.insert(fileDTO);
		return file_no;
	}

	// 21.05.03 첨부파일 번호로 해당 레코드의 크기 가져오기
	public double selectRecordSize(int file_no) {
		double recordSize = fileDAO.selectRecordSize(file_no);
		return recordSize;
	}

	
	// 테이블 조인 ============================================================
	// 21.05.13 'memo_file'테이블과 'file'테이블 조인 - 'memo_file.memo_no'에 해당하는 데이터 가져오기
	public List<FileDTO> selectMemofileAndFile(int memo_no) {
		List<FileDTO> fileList = fileDAO.selectMemofileAndFile(memo_no);
		return fileList;
	}
	
}
