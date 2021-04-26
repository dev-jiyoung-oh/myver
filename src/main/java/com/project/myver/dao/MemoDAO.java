package com.project.myver.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

public class MemoDAO extends SqlSessionDaoSupport {
	@Autowired
	SqlSessionTemplate session;
}
