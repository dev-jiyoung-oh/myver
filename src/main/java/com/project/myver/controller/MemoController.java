package com.project.myver.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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
	
	// 21.04.26 쪽지 리스트
	@RequestMapping(value = "/list")
	public ModelAndView memoList(
				@AuthenticationPrincipal MemberDTO user,
				ModelAndView mv, 
				RedirectView rv,
				HttpServletRequest request) {
		System.out.println("memoList - user - "+user.toString());
		
		if(user.getUsername() == null) {
			System.out.println("memoList - 로그인을 안했어유~");
			System.out.println("MemoController - memoList() - loginRedirect: "+request.getRequestURI());
    		mv.addObject("loginRedirect", request.getRequestURI());
			rv.setUrl("/login");
			mv.setView(rv);
			return mv;
		}
		
		ArrayList<MemoDTO> my_received_memo_list = memoSVC.selectReceivedFromMemoAndMy_memo(user.getMember_no());
		
		mv.addObject("RECEIVED_LIST", my_received_memo_list);
		mv.setViewName("memo/list");
		
		return mv;
	}
	
	// 21.04.29 쪽지 작성 폼
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public ModelAndView memoWriteFrm(
			ModelAndView mv) {
		//String id = memSVC.findIdByPhone(memdto.getPhone());
		
		//mv.addObject("ID",id);
		
		mv.setViewName("memo/write");
		
		return mv;
	}
	
	// 21.05.01 쪽지 작성
	@RequestMapping(value = "/write", method = RequestMethod.POST) //, produces = "application/text; charset=utf8")
	@ResponseBody
	public String memoWrite(
				@AuthenticationPrincipal MemberDTO user,
				MemoDTO memoDTO,
				MultipartFile[] file_array,
				ModelAndView mv,
				RedirectView rv,
				HttpServletRequest request) {
		System.out.println(memoDTO.memoToString());

		// 작성자 id가 null인 경우
		if(user.getUsername() == null || memoDTO.getWriter_id() == null) {
    		return "no_login";
		}
		
		int memo_no =-1; 					// 쪽지 번호
		double memo_size = 0D;				// 쪽지 크기
		boolean has_file = false; 			// 파일 유무
		int file_index = 1; 				// 파일 첨부순서
		boolean memoWrite_success = true; 	// 쪽지 작성 성공 여부
		
		try {
			// 21.05.02 1. 'memo' table에 데이터 삽입하고 쪽지 번호 가져오기 
			memo_no = memoSVC.insertMemo(memoDTO);
			memo_size += memoSVC.selectRecordSizeFromMemo(memo_no); // 해당 레코드의 크기 가져와 memo_size에 추가
			
			if(file_array == null) { 
				System.out.println("file_array == null");
			}else {
				for(MultipartFile file : file_array) {
					System.out.println("첨부파일: "+file.getOriginalFilename()+" "+file.getSize());
					String oriName = file.getOriginalFilename();
					
					if(oriName==null || oriName.length()==0) {
						System.out.println("파일이 없어요~");
						continue;
					}
					
					memo_size += file.getSize(); // memo_size에 파일 크기 더하기
					
					// 21.05.02 2. 첨부파일 upload 폴더에 저장 (return값의 [0]:저장명, [1]:저장경로)
					String[] saved_nameAndPath = fileSVC.upload(file, 0, memoDTO.getWriter_id()); // 2번째 파라미터 - 0: 쪽지 영역
					
					// 21.05.02 3. 'file' table에 데이터 삽입하고 파일번호 가져오기 (종류 0:쪽지) 
					int file_no = fileSVC.insert(new FileDTO(0, file.getOriginalFilename(), saved_nameAndPath[0], saved_nameAndPath[1], file.getSize()));
					memo_size += fileSVC.selectRecordSize(file_no); // 해당 레코드의 크기 가져와 memo_size에 추가
					
					// 21.05.02 4. 'memo_file' table에 데이터 삽입 (첨부순서:file_seq) 후 쪽지 첨부파일 번호 가져오기
					int memo_file_no = memoSVC.insertMemo_file(memo_no,file_index,file_no);
					memo_size += memoSVC.selectRecordSizeFromMemo_file(memo_file_no); // 해당 레코드의 크기 가져와 memo_size에 추가
					
					if(!has_file) { has_file = true; }
					
					file_index++;
				}
			}

			// 21.07.16 첨부 파일이 있는 경우, 쪽지의 'has_file'을 1로 변경
			if(has_file) {
				memoSVC.updateHas_fileFromMemo(memo_no, 1);
			}
			
			if(memo_no != -1) {
				// 21.05.03 memo_no에 해당하는 레코드의 쪽지 크기 수정
				memoSVC.updateMemo_size(memo_no, memo_size);
				
				// 21.05.03 발신자의 'my_memo' table에 추가 (발신자 - 읽음:1 / 보관함:2)
				memoSVC.insertMy_memo(memSVC.selectMember_noById(memoDTO.getWriter_id()), memo_no, 1, 2); // insertMyMemo(member_no,memo_no,is_read,box);
				
				
				// 21.05.06 수신자id가 회원 중에 존재하지 않는 경우
				if(memSVC.getIdCnt(memoDTO.getReceiver_id()) == 0) {
					System.out.println("수신자가 존재하지 않습니다.");
					try {
						// 작성자에게 [발송실패] 쪽지 보내기
						int memo_noWhenNoReceiver = memoSVC.insertMemoWhenNoReceiver(memoDTO,memoDTO.getWriter_id());
			
						// url 바로가기 파일 생성
						// URL = http://localhost:8080/myver/memo/popup/mn=쪽지번호(실패한 쪽지의 번호)
						// 미완★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
						FileDTO fileDTOWhenNoReceiver = fileSVC.uploadUrlFile(memo_no, memoDTO.getTitle());
						
						// 'file' table에 데이터 삽입하고 파일번호 가져오기
						int file_noWhenNoReceiver = fileSVC.insert(fileDTOWhenNoReceiver);
						double memo_sizeWhenNoReceiver = fileSVC.selectRecordSize(file_noWhenNoReceiver); // 해당 레코드의 크기 가져와 memo_sizeWhenNoReceiver에 추가
						
						// 'memo_file' table에 데이터 삽입 (첨부순서:file_seq) 후 쪽지 첨부파일 번호 가져오기
						int memo_file_noWhenNoReceiver = memoSVC.insertMemo_file(memo_noWhenNoReceiver,0,file_noWhenNoReceiver);
						memo_sizeWhenNoReceiver += memoSVC.selectRecordSizeFromMemo_file(memo_file_noWhenNoReceiver); // 해당 레코드의 크기 가져와 memo_sizeWhenNoReceiver에 추가
					
						// 'memo_no'에 해당하는 레코드의 'memo_size' 수정
						memoSVC.updateMemo_size(memo_noWhenNoReceiver, memo_sizeWhenNoReceiver);
						
						// 'my_memo' table에 추가 (member_no, memo_no, is_read, box) (수신자 - 읽음:0 / 보관함:0)
						memoSVC.insertMy_memo(memSVC.selectMember_noById(memoDTO.getWriter_id()), memo_noWhenNoReceiver, 0, 0);
						
					} catch(IllegalStateException e) {
						e.printStackTrace();
		            } catch(IOException e) {
		                e.printStackTrace();
		            }
					
				}else { // 수신자가 존재하는 경우
					// 'my_memo' table에 추가 (수신자 - 읽음:0 / 보관함:0)
					memoSVC.insertMy_memo(memSVC.selectMember_noById(memoDTO.getReceiver_id()), memo_no, 0, 0);
				}
			}else {
				System.out.println("memo_no == -1, 쪽지 발송 실패");
				memoWrite_success = false;
			}
		} catch(Exception e) {
			System.out.println("MemoController-memoWrite() 실패");
			e.printStackTrace();
			memoWrite_success = false;
		}

		
		//mv.addObject("ID",id);
		
		// 오류 발생시 실패 페이지 / 오류 미발생시 성공 페이지
		if(memoWrite_success) {
			//mv.setViewName("member/memo/write_success");
			return "success";
		}else {
			//mv.setViewName("member/memo/write_fail");
			return "fail";
		}
		
		//return mv;
	}
	
	// 21.05.05 쪽지 팝업 폼(아이디랑 비밀번호 입력하는 폼)
	@RequestMapping(value = "/popup", method = RequestMethod.GET)
	public ModelAndView memoPopupFrm(ModelAndView mv, int mn) {
		return mv;
	}
	
	// 21.05.05 쪽지 팝업
	@RequestMapping(value = "/popup", method = RequestMethod.POST)
	public ModelAndView memoPopup(ModelAndView mv, int mn, MemberDTO memDTO) {
		return mv;
	}
	
	// 21.05.11 쪽지 상세 (mn: memo_no)
	@RequestMapping(value = "/read")
	public ModelAndView memoRead(ModelAndView mv, int mn) {
		mv.setViewName("memo/read");
		return mv;
	}
	
	// 21.05.12 'memo_no'에 해당하는 memo.content 데이터 가져오기 (mn: memo_no)
	@RequestMapping(value = "/getContent", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Map<String,Object> selectContentByMemo_no(int mn) {
		System.out.println("selectContentByMemo_no-meno_no: "+mn);
		
		String content = memoSVC.selectContentByMemo_no(mn);
		List<FileDTO> fileList = fileSVC.selectMemofileAndFile(mn);
		Map<String, Object> map = new HashMap<>();
		map.put("content", content);
		map.put("fileList", fileList);
		
		System.out.println("content: "+content);
		if(fileList!=null && fileList.size()>0) {
			for(FileDTO file : fileList) {
				System.out.println(file);
			}
		}else {
			System.out.println("fileList가 비었어요");
		}
		return map;
	}
}
