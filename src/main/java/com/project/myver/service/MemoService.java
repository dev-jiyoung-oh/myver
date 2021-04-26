package com.project.myver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.myver.dao.MemoDAO;

@Service
public class MemoService {
	@Autowired
	private MemoDAO memoDAO;
}
