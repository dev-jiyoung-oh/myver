package com.project.myver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.myver.dao.MemoDAO;
import com.project.myver.dto.MemoDTO;

@Service
public class MemoService {
	@Autowired
	private MemoDAO memoDAO;
	
	// 'memo' table =================================================
	// 21.05.03 데이터 삽입하고 'memo_no' 가져오기 
	public int insertMemo(MemoDTO memoDTO) {
		int memo_no = memoDAO.insertMemo(memoDTO);
		return memo_no;
	}

	// 21.05.03 해당 'memo_no' 레코드의 크기 가져오기
	public int selectRecordSizeFromMemo(int memo_no) {
		int recordSize = memoDAO.selectRecordSizeFromMemo(memo_no);
		return recordSize;
	}
	
	// 21.05.03 'memo_no'에 해당하는 레코드의 'memo_size' 수정
	public void updateMemo_size(int memo_no, Double memo_size) {
		MemoDTO memoDTO = new MemoDTO();
		memoDTO.setMemo_no(memo_no);
		memoDTO.setMemo_size(memo_size);
		
		memoDAO.updateMemo_size(memoDTO);
	}
	
	// 21.05.06 수신자 없어서 쪽지 발송 실패했을 때 작성자에게 쪽지 발송
	public int insertMemoWhenNoReceiver(MemoDTO failMemoDTO, int writer_no) {
		MemoDTO memoDTO = new MemoDTO();
		memoDTO.setWriter_id("myver쪽지");
		memoDTO.setTitle("[발송실패 안내] " + failMemoDTO.getReceiver_id() + "으로 쪽지가 전송되지 못했습니다.");
		
		int memo_no = memoDAO.insertMemo(memoDTO);
		return memo_no;
	}
	
	// 'memo_file' table =================================================
	// 21.05.03 데이터 삽입하고 'memo_file_no' 가져오기
	public int insertMemo_file(int memo_no, int file_seq, int file_no) {
		MemoDTO memoDTO = new MemoDTO();
		memoDTO.setMemo_no(memo_no);
		memoDTO.setFile_seq(file_seq);
		memoDTO.setFile_no(file_no);
		
		int memo_file_no = memoDAO.insertMemo_file(memoDTO);
		
		return memo_file_no;
	}

	// 21.05.03 'memo_file_no'에 해당하는 레코드의 크기 가져오기
	public int selectRecordSizeFromMemo_file(int memo_file_no) {
		int recordSize = memoDAO.selectRecordSizeFromMemo_file(memo_file_no);
		return recordSize;
	}

	
	// 'my_memo' table =================================================
	// 21.05.03 데이터 추가
	public void insertMy_memo(int member_no, int memo_no, int is_read, int box) {
		MemoDTO memoDTO = new MemoDTO();
		memoDTO.setMember_no(member_no);
		memoDTO.setMemo_no(memo_no);
		memoDTO.setIs_read(is_read);
		memoDTO.setBox(box);
		
		memoDAO.insertMy_memo(memoDTO);
	}
}
