package com.project.myver.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.myver.dto.MemoDTO;

public class MemoDAO extends SqlSessionDaoSupport {
	@Autowired
	SqlSessionTemplate session;

	// 'memo' table =================================================
	// 21.05.03 데이터 삽입하고 'memo_no' 가져오기 
	public int insertMemo(MemoDTO memoDTO) {
		int memo_no = session.insert("memo.insertMemo", memoDTO);
		return memo_no;
	}

	// 21.05.03 'memo_no'에 해당하는 레코드의 크기 가져오기 
	public int selectRecordSizeFromMemo(int memo_no) {
		int recordSize = session.selectOne("memo.selectRecordSizeFromMemo", memo_no);
		return recordSize;
	}

	// 21.05.03 'memo_no'에 해당하는 레코드의 'memo_size' 수정
	public void updateMemo_size(MemoDTO memoDTO) {
		int ok = session.update("memo.updateMemo_size", memoDTO);
		
		if(ok==0) {
			System.out.println("MemoDAO-updateMemo_size 실패");
		}else if(ok==1) {
			System.out.println("MemoDAO-updateMemo_size 성공");
		}else {
			System.out.println("MemoDAO-updateMemo_size 예상치 못한 오류");
		}
	}
	
	
	// 'memo_file' table =================================================
	// 21.05.03 데이터 삽입하고 'memo_file_no' 가져오기
	public int insertMemo_file(MemoDTO memoDTO) {
		int memo_file_no = session.insert("memo.insertMemo_file", memoDTO);
		return memo_file_no;
	}

	// 21.05.03 'memo_file_no'에 해당하는 레코드의 크기 가져오기
	public int selectRecordSizeFromMemo_file(int memo_file_no) {
		int recordSize = session.selectOne("memo.selectRecordSizeFromMemo_file", memo_file_no);
		return recordSize;
	}
	

	// 'my_memo' table =================================================
	// 21.05.03 데이터 추가
	public void insertMy_memo(MemoDTO memoDTO) {
		int ok = session.insert("memo.insertMy_memo", memoDTO);
		
		if(ok==0) {
			System.out.println("MemoDAO-insertMy_memo 실패");
		}else if(ok==1) {
			System.out.println("MemoDAO-insertMy_memo 성공");
		}else {
			System.out.println("MemoDAO-insertMy_memo 예상치 못한 오류");
		}
	}
}
