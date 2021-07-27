package com.project.myver.service;

import java.util.ArrayList;
import java.util.HashMap;

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
	public double selectRecordSizeFromMemo(int memo_no) {
		double recordSize = memoDAO.selectRecordSizeFromMemo(memo_no);
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
	public int insertMemoWhenNoReceiver(MemoDTO failMemoDTO, String writer_id) {
		MemoDTO memoDTO = new MemoDTO();
		memoDTO.setWriter_id("myver_memo");
		memoDTO.setWriter_name("myver쪽지");
		memoDTO.setReceiver_id(writer_id);
		memoDTO.setTitle("[발송실패 안내] " + failMemoDTO.getReceiver_id() + "으로 쪽지가 전송되지 못했습니다.");
		
		int memo_no = memoDAO.insertMemo(memoDTO);
		return memo_no;
	}
	
	// 21.05.12 'memo_no'에 해당하는 content 데이터 가져오기
	public String selectContentByMemo_no(int memo_no) {
		String content = memoDAO.selectContentByMemo_no(memo_no);
		return content;
	}
		
	// 'memo_file' table =================================================
	// 21.05.03 데이터 삽입하고 'memo_file_no' 가져오기
	public int insertMemo_file(int memo_no, int file_index, int file_no) {
		MemoDTO memoDTO = new MemoDTO();
		memoDTO.setMemo_no(memo_no);
		memoDTO.setFile_index(file_index);
		memoDTO.setFile_no(file_no);
		
		int memo_file_no = memoDAO.insertMemo_file(memoDTO);
		
		return memo_file_no;
	}

	// 21.05.03 'memo_file_no'에 해당하는 레코드의 크기 가져오기
	public double selectRecordSizeFromMemo_file(int memo_file_no) {
		double recordSize = memoDAO.selectRecordSizeFromMemo_file(memo_file_no);
		return recordSize;
	}

	// 21.07.16 'memo_no'에 해당하는 레코드의 첨부파일 유무 변경
	public void updateHas_fileFromMemo(int memo_no, int has_file) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("memo_no", memo_no);
		map.put("has_file", has_file);
		
		memoDAO.updateHas_fileFromMemo(map);
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
	
	
	
	// 테이블 조인 ============================================================
	/* 21.05.10 'memo'테이블과 'my_memo'테이블 조인 - 'member_no'에 해당하는 모든 데이터 가져오기(my_memo.box별로)
	public ArrayList<ArrayList<MemoDTO>> selectAllFromMemoAndMy_memo(int member_no){
		ArrayList<ArrayList<MemoDTO>> list = new ArrayList<>();
		
		for(int i=0; i<6; i++) {
			HashMap<String,Integer> map = new HashMap<>();
			map.put("member_no", member_no);
			map.put("box", i);
			
			ArrayList<MemoDTO> my_memo_list = memoDAO.selectMemoAndMy_memo(map);
			list.add(my_memo_list);
		}
		
		return list;
	}
	*/
	// 'memo'table & 'my_memo'table
	// 21.05.10 'member_no'에 해당하는 '받은 쪽지' 데이터 가져오기
	public ArrayList<MemoDTO> selectReceivedFromMemoAndMy_memo(int member_no){
		
		ArrayList<MemoDTO> my_memo_list = memoDAO.selectReceivedFromMemoAndMy_memo(member_no);
		
		return my_memo_list;
	}


	
}
