package com.nantian.att.main.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.nantian.att.main.web.entity.Dictionary;
import com.nantian.att.main.web.mapper.DictionaryMapper;
import com.nantian.att.main.web.util.GetUUID;

@Service
@Transactional(isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class DictionaryService {

	@Autowired
	private DictionaryMapper dmMapper = null;
	
	public List<Dictionary> findItemsByParentName(String parentName){
		return dmMapper.selectItems(parentName);
	}
	
	public List<Dictionary> findParentItems(){
		return dmMapper.selectParentItems();
	}
	
	public List<Dictionary> findItemsByParentId(String parentId){
		return dmMapper.selectItemsByPid(parentId);
	}
	
	public int addDictionary(String formData){
		Dictionary dictionary = JSONObject.parseObject(formData, Dictionary.class);
		dictionary.setId(GetUUID.randomId());
		return dmMapper.insertDictionary(dictionary);
	}
	
	public int modifyDictionary(String formData){
		Dictionary dictionary = JSONObject.parseObject(formData, Dictionary.class);
		return dmMapper.updateDictionary(dictionary);
	}
	
	public int removeDictionary(String dicId){
		return dmMapper.deleteDicById(dicId);
	}
	
	public List<Dictionary> findItems2Select(){
		return dmMapper.selectParentItems2Select();
	}
}
