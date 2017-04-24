package com.nantian.att.main.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.nantian.att.main.web.entity.CaseCollection;
import com.nantian.att.main.web.mapper.CaseCollectionMapper;
import com.nantian.att.main.web.mapper.CaseMapper;

@Service
@Transactional(isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class CaseCollectionService {
	private Logger logger = LoggerFactory.getLogger(CaseCollectionService.class);
	@Autowired
	private CaseCollectionMapper ccMapper = null;
	@Autowired
	private CaseMapper cMapper = null;
	
	
	public List<CaseCollection> findCaseCollsByBusId(String busId){
		List<CaseCollection> caseColl = ccMapper.selectCaseGroupsByBusId(busId);
		if(caseColl.size() == 0){
			List<CaseCollection> temp = new ArrayList<CaseCollection>();
			temp.add(new CaseCollection("null", "请添加", null, null));
			return temp;
		}
		return caseColl;
	}
	
	public int addCaseColl(String  formData){
		CaseCollection caseColl = JSONObject.parseObject(formData, CaseCollection.class);
		caseColl.setId(UUID.randomUUID().toString().replace("-", ""));
		return ccMapper.insertCaseGroup(caseColl);
	}
	
	public String removeCaseColl(String caseCollId){
		int caseRes = cMapper.deleteCasesByCaseCollId(caseCollId);
		int caseCollRes = 0;
		if(caseRes >= 0){
			caseCollRes = ccMapper.deleteCaseGroup(caseCollId);
		}
		if(caseCollRes > 0 ){
			return "true";
		}
		logger.info("删除案例集失败");
		return "false";
	}
	
	public int modifyCaseColl(String formData){
		CaseCollection caseColl = JSONObject.parseObject(formData, CaseCollection.class);
		return ccMapper.updateCaseGroup(caseColl);
	}
}
