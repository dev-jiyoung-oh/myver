package com.project.myver.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.myver.dto.MemoDTO;

public class MemoDAO extends SqlSessionDaoSupport {
	@Autowired
	SqlSessionTemplate session;

	// 21.05.03 memo table에 데이터 삽입하고 쪽지 번호 가져오기 
	public int insertMemo(MemoDTO memoDTO) {
		int memoNo = session.insert("memo.insertMemo", memoDTO);
		return memoNo;
	}

	// 21.05.03 해당 쪽지 번호 레코드의 크기 가져오기 from memo table(미완)
	public int selectRecordSizeFromMemo(int memoNo) {
		int recordSize = session.selectOne("memo.selectRecordSizeFromMemo", memoNo);
		return recordSize;
	}

	// 21.05.03 쪽지 첨부파일 table에 데이터 삽입하고 쪽지 첨부파일 번호 가져오기
	public int insertMemoFile(MemoDTO memoDTO) {
		int memoFileNo = session.insert("memo.insertMemoFile", memoDTO);
		return memoFileNo;
	}

	// 21.05.03 해당 쪽지 파일 번호 레코드의 크기 가져오기 from memo_file table(미완)
	public int selectRecordSizeFromMemoFile(int memoFileNo) {
		int recordSize = session.selectOne("memo.selectRecordSizeFromMemoFile", memoFileNo);
		return recordSize;
	}

	// 21.05.03 쪽지 번호에 해당하는 레코드의 쪽지 크기 수정
	public void updateMemoSize(MemoDTO memoDTO) {
		int ok = session.update("memo.updateMemoSize", memoDTO);
		
		if(ok==0) {
			System.out.println("MemoDAO-updateMemoSize 실패");
		}else if(ok==1) {
			System.out.println("MemoDAO-updateMemoSize 성공");
		}else {
			System.out.println("MemoDAO-updateMemoSize 예상치 못한 오류");
		}
	}

	// 21.05.03 my_memo table에 데이터 추가
	public void insertMyMemo(MemoDTO memoDTO) {
		int ok = session.insert("memo.insertMyMemo", memoDTO);
		
		if(ok==0) {
			System.out.println("MemoDAO-insertMyMemo 실패");
		}else if(ok==1) {
			System.out.println("MemoDAO-insertMyMemo 성공");
		}else {
			System.out.println("MemoDAO-insertMyMemo 예상치 못한 오류");
		}
	}
}
