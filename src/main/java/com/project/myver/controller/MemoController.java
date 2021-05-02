package com.project.myver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.project.myver.dto.MemberDTO;
import com.project.myver.dto.MemoDTO;
import com.project.myver.service.MemberService;
import com.project.myver.service.MemoService;

@Controller
@RequestMapping("/memo")
public class MemoController {
	
	@Autowired
	private MemoService memoSVC;
	@Autowired
	private MemberService memSVC;
	
	// 21.04.26 쪽지24 리스트
	@RequestMapping(value = "/list")
	public ModelAndView memoList(MemberDTO memdto, ModelAndView mv) {
		//String id = memSVC.findIdByPhone(memdto.getPhone());
		
		//mv.addObject("ID",id);
		
		mv.setViewName("/member/memo/list");
		
		return mv;
	}
	
	// 21.04.29 쪽지 작성 폼
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public ModelAndView memoWriteFrm(ModelAndView mv) {
		//String id = memSVC.findIdByPhone(memdto.getPhone());
		
		//mv.addObject("ID",id);
		
		mv.setViewName("member/memo/write");
		
		return mv;
	}
	
	// 21.05.01 쪽지 작성
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public ModelAndView memoWrite(
				ModelAndView mv,
				MultipartHttpServletRequest mtfRequest,
				MemoDTO memoDTO) {
		
		List<MultipartFile> fileList = mtfRequest.getFiles("file");
		int memoSize = 0;
		int memo_no =-1;
		
		if(fileList != null) { // 파일 첨부한 경우
			memoDTO.setHas_file(1); // 1 : 첨부파일 있음
			
			String path = "D:\\jy_project\\myver\\workspace\\upload\\";
			int fileSeq = 1; // 첨부순서
			
			// 1. memo table에 데이터 삽입 / 쪽지 번호 가져오기 
			memo_no = memoSVC.insertMemo(memoDTO);	
			
			memoSize += memoSVC.selectSize(memo_no); // 해당 레코드의 크기 가져오기 및 memoSize에 추가
			
			for (MultipartFile mf : fileList) {
				// 2. 첨부파일 upload 폴더에 저장 / 파일 크기 가져오기
				String originFileName = mf.getOriginalFilename(); // 원본 파일명
				long fileSize = mf.getSize(); // 파일 크기
				memoSize += fileSize; // memoSize에 파일 크기 더하기
				//System.out.println("originFileName : " + originFileName);
				//System.out.println("fileSize : " + fileSize);
				// 파일 저장이름 "날짜_작성자아이디_원본파일명"
				// new file()
				
				// 3. 첨부파일 테이블에 데이터 삽입 -> 저장한 파일번호 가져오기 (종류:쪽지) / 해당 레코드의 크기 가져오기 및 memoSize에 추가
				
				// 4. 쪽지첨부파일 table에 데이터 삽입 (첨부순서:fileSeq) / 해당 레코드의 크기 가져오기 및 memoSize에 추가
				
				fileSeq++;
			}
			
		}else {
			memoDTO.setHas_file(0); // 0 : 첨부파일 없음
			
			// memo table에 데이터 삽입 / 쪽지 번호 가져오기 
			memo_no = memoSVC.insertMemo(memoDTO);
			
			// 해당 레코드의 크기 가져오기 및 memoSize에 추가
			memoSize += memoSVC.selectSize(memo_no); 
		}
		
		// 쪽지 번호에 해당하는 레코드 파일 크기 수정
		memoSVC.updateMemoSize(memo_no);
		
		
		// 발신자의 my_memo table에 추가 (보관함:2)
		
		// 수신자id가 회원 중에 존재하지 않는 경우
		if(memSVC.getIDCnt(memoDTO.getReceiver_id()) == 0) {
			// 본문 내용을 링크로! 그 링크를 첨부파일로... (rm:memo_popup, jsp:memo_popup)
			
			// 작성자에게 메일 보내기
			
		}else { // 수신자가 존재하는 경우
			// my_memo table에 추가 (수신자 - 보관함:0)
			
		}
		

		// 오류 발생시 실패 페이지 / 오류 없을시 성공 페이지
		
		//mv.addObject("ID",id);
		
		mv.setViewName("member/memo/write");
		
		return mv;
	}
}
