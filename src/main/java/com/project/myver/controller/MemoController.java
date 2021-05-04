package com.project.myver.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.project.myver.dto.FileDTO;
import com.project.myver.dto.MemberDTO;
import com.project.myver.dto.MemoDTO;
import com.project.myver.service.FileService;
import com.project.myver.service.MemberService;
import com.project.myver.service.MemoService;

@Controller
@RequestMapping("/memo")
public class MemoController {
	
	@Autowired
	private MemoService memoSVC;
	@Autowired
	private MemberService memSVC;
	@Autowired
	private FileService fileSVC;
	
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
		Double memo_size = 0D;
		int memo_no =-1;
		
		if(fileList != null) { // 파일 첨부한 경우
			memoDTO.setHas_file(1); // 1 : 첨부파일 있음
			
			int file_seq = 1; // 첨부순서
			
			// 21.05.02 1. 'memo' table에 데이터 삽입하고 쪽지 번호 가져오기 
			memo_no = memoSVC.insertMemo(memoDTO);	
			
			memo_size += memoSVC.selectRecordSizeFromMemo(memo_no); // 해당 레코드의 크기 가져와 memo_size에 추가
			
			for (MultipartFile mf : fileList) {
				String saved_name = "";
				
				// 21.05.02 2. 첨부파일 upload 폴더에 저장 
				try {
					memo_size += mf.getSize(); // memo_size에 파일 크기 더하기
					
					saved_name = fileSVC.upload(mf,0,memoDTO.getWriter_id()); // 0: 쪽지 영역
					
					// 21.05.02 3. 'file' table에 데이터 삽입하고 파일번호 가져오기 (종류 0:쪽지) 
					int file_no = fileSVC.insert(new FileDTO(0,mf.getOriginalFilename(),saved_name,mf.getSize()));
					memo_size += fileSVC.selectRecordSize(file_no); // 해당 레코드의 크기 가져와 memo_size에 추가
					
					// 21.05.02 4. 'memo_file' table에 데이터 삽입 (첨부순서:file_seq) 후 쪽지 첨부파일 번호 가져오기
					int memo_file_no = memoSVC.insertMemo_file(memo_no,file_seq,file_no);
					memo_size += memoSVC.selectRecordSizeFromMemo_file(memo_file_no); // 해당 레코드의 크기 가져와 memo_size에 추가
					
					file_seq++;
				} catch(IllegalStateException e) {
					e.printStackTrace();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
			}
			
		}else {
			memoDTO.setHas_file(0); // 0 : 첨부파일 없음
			
			// 'memo' table에 데이터 삽입 / 쪽지 번호 가져오기 
			memo_no = memoSVC.insertMemo(memoDTO);
			
			// 'memo' table에서 해당 레코드의 크기 가져오기 및 memo_size에 추가
			memo_size += memoSVC.selectRecordSizeFromMemo(memo_no); 
		}
		
		// 21.05.03 memo_no에 해당하는 레코드의 쪽지 크기 수정
		memoSVC.updateMemo_size(memo_no, memo_size);
		
		
		// 21.05.03 발신자의 'my_memo' table에 추가 (발신자 - 읽음:1 / 보관함:2)
		memoSVC.insertMy_memo(memSVC.selectMember_noById(memoDTO.getWriter_id()), memo_no, 1, 2); // insertMyMemo(member_no,memo_no,is_read,box);
		
		// 수신자id가 회원 중에 존재하지 않는 경우
		if(memSVC.getIdCnt(memoDTO.getReceiver_id()) == 0) {
			// 본문 내용을 링크로! 그 링크를 첨부파일로... (rm:memo_popup, jsp:memo_popup)
			
			// 작성자에게 메일 보내기
			
		}else { // 수신자가 존재하는 경우
			// 'my_memo' table에 추가 (수신자 - 읽음:0 / 보관함:0)
			memoSVC.insertMy_memo(memSVC.selectMember_noById(memoDTO.getReceiver_id()), memo_no, 0, 0);
		}
		

		// 오류 발생시 실패 페이지 / 오류 없을시 성공 페이지
		
		//mv.addObject("ID",id);
		
		mv.setViewName("member/memo/write");
		
		return mv;
	}
}
