package com.project.myver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.myver.dao.MemoDAO;
import com.project.myver.dto.MemoDTO;

@Service
public class MemoService {
	@Autowired
	private MemoDAO memoDAO;
	
	// 21.05.03 memo table에 데이터 삽입하고 쪽지 번호 가져오기 
	public int insertMemo(MemoDTO memoDTO) {
		int memo_nO = memoDAO.insertMemo(memoDTO);
		return memo_nO;
	}

	// 21.05.03 해당 쪽지 번호 레코드의 크기 가져오기 from memo table
	public int selectRecordSizeFromMemo(int memo_no) {
		int recordSize = memoDAO.selectRecordSizeFromMemo(memo_no);
		return recordSize;
	}

	// 21.05.03 쪽지 첨부파일 table에 데이터 삽입하고 쪽지 첨부파일 번호 가져오기
	public int insertMemo_file(int memo_no, int file_seq, int file_no) {
		MemoDTO memoDTO = new MemoDTO();
		memoDTO.setMemo_no(memo_no);
		memoDTO.setFile_seq(file_seq);
		memoDTO.setFile_no(file_no);
		
		int memo_file_no = memoDAO.insertMemo_file(memoDTO);
		
		return memo_file_no;
	}

	// 21.05.03 해당 쪽지 파일 번호 레코드의 크기 가져오기 from memo_file table
	public int selectRecordSizeFromMemo_file(int memo_file_no) {
		int recordSize = memoDAO.selectRecordSizeFromMemo_file(memo_file_no);
		return recordSize;
	}

	// 21.05.03 쪽지 번호에 해당하는 레코드의 쪽지 크기 수정
	public void updateMemo_size(int memo_no, Double memo_size) {
		MemoDTO memoDTO = new MemoDTO();
		memoDTO.setMemo_no(memo_no);
		memoDTO.setMemo_size(memo_size);
		
		memoDAO.updateMemo_size(memoDTO);
	}

	// 21.05.03 my_memo table에 데이터 추가
	public void insertMy_memo(int member_no, int memo_no, int is_read, int box) {
		MemoDTO memoDTO = new MemoDTO();
		memoDTO.setMember_no(member_no);
		memoDTO.setMemo_no(memo_no);
		memoDTO.setIs_read(is_read);
		memoDTO.setBox(box);
		
		memoDAO.insertMy_memo(memoDTO);
	}
}
