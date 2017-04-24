package com.nantian.att.main.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nantian.att.main.web.mapper.UserMapper;

@Service
@Transactional(isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class IndexService {
	@Autowired
	private  UserMapper userMapper = null;
	
	@SuppressWarnings("unused")
	public void test(){
		 List<Map<String,Object>> result = userMapper.findByName("t1");
	}
	
}
