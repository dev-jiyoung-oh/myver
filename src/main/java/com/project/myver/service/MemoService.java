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
		int memoNO = memoDAO.insertMemo(memoDTO);
		return memoNO;
	}

	// 21.05.03 해당 쪽지 번호 레코드의 크기 가져오기 from memo table
	public int selectRecordSizeFromMemo(int memoNo) {
		int recordSize = memoDAO.selectRecordSizeFromMemo(memoNo);
		return recordSize;
	}

	// 21.05.03 쪽지 첨부파일 table에 데이터 삽입하고 쪽지 첨부파일 번호 가져오기
	public int insertMemoFile(int memoNo, int fileSeq, int fileNo) {
		MemoDTO memoDTO = new MemoDTO();
		memoDTO.setMemo_no(memoNo);
		memoDTO.setFile_seq(fileSeq);
		memoDTO.setFile_no(fileNo);
		
		int memoFileNo = memoDAO.insertMemoFile(memoDTO);
		
		return memoFileNo;
	}

	// 21.05.03 해당 쪽지 파일 번호 레코드의 크기 가져오기 from memo_file table
	public int selectRecordSizeFromMemoFile(int memoFileNo) {
		int recordSize = memoDAO.selectRecordSizeFromMemoFile(memoFileNo);
		return recordSize;
	}

	// 21.05.03 쪽지 번호에 해당하는 레코드의 쪽지 크기 수정
	public void updateMemoSize(int memo_no, Double memo_size) {
		MemoDTO memoDTO = new MemoDTO();
		memoDTO.setMemo_no(memo_no);
		memoDTO.setMemo_size(memo_size);
		
		memoDAO.updateMemoSize(memoDTO);
	}

	// 21.05.03 my_memo table에 데이터 추가
	public void insertMyMemo(int member_no, int memo_no, int box) {
		MemoDTO memoDTO = new MemoDTO();
		memoDTO.setMember_no(member_no);
		memoDTO.setMemo_no(memo_no);
		memoDTO.setBox(box);
		
		memoDAO.insertMyMemo(memoDTO);
	}
}
