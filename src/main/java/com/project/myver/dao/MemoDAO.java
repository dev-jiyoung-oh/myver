package com.project.myver.dao;

import java.util.ArrayList;
import java.util.Map;

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
		session.insert("memo.insertMemo", memoDTO);
		int memo_no = memoDTO.getMemo_no();
		System.out.println("MemoDAO.insertMemo - memo_no : "+memo_no);
		return memo_no;
	}

	// 21.05.03 'memo_no'에 해당하는 레코드의 크기 가져오기 
	public double selectRecordSizeFromMemo(int memo_no) {
		double recordSize = session.selectOne("memo.selectRecordSizeFromMemo", memo_no);
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
	
	// 21.05.12 'memo_no'에 해당하는 content 데이터 가져오기
	public String selectContentByMemo_no(int memo_no) {
		String content = session.selectOne("memo.selectContentByMemo_no", memo_no);
		return content;
	}
	
	// 'memo_file' table =================================================
	// 21.05.03 데이터 삽입하고 'memo_file_no' 가져오기
	public int insertMemo_file(MemoDTO memoDTO) {
		session.insert("memo.insertMemo_file", memoDTO);
		int memo_file_no = memoDTO.getMemo_file_no();
		System.out.println("MemoDAO.insertMemo - memo_no : "+memo_file_no);
		return memo_file_no;
	}

	// 21.05.03 'memo_file_no'에 해당하는 레코드의 크기 가져오기
	public double selectRecordSizeFromMemo_file(int memo_file_no) {
		double recordSize = session.selectOne("memo.selectRecordSizeFromMemo_file", memo_file_no);
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
	
	// 테이블 조인 ============================================================
	/* 21.05.10 'memo'테이블과 'my_memo'테이블 조인 - member_no랑 box에 해당하는 데이터 가져오기
	public ArrayList<MemoDTO> selectMemoAndMy_memo(Map<String,Integer> member_noAndBox) {
		ArrayList<MemoDTO> my_memo_list = (ArrayList)session.selectList("memo.selectMemoAndMy_memo", member_noAndBox);
		return my_memo_list;
	}
	*/
	// 21.05.10 'memo'테이블과 'my_memo'테이블 조인 - member_no랑 box에 해당하는 데이터 가져오기
	public ArrayList<MemoDTO> selectMemoAndMy_memo(int member_no) {
		ArrayList<MemoDTO> my_memo_list = (ArrayList)session.selectList("memo.selectMemoAndMy_memo", member_no);
		return my_memo_list;
	}

	
}
