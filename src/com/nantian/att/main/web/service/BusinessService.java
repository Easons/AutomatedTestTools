package com.nantian.att.main.web.service;

import java.util.ArrayList;
import java.util.Date;
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
import com.nantian.att.main.web.entity.Business;
import com.nantian.att.main.web.entity.CaseCollection;
import com.nantian.att.main.web.mapper.BusinessMapper;
import com.nantian.att.main.web.mapper.CaseCollectionMapper;
import com.nantian.att.main.web.mapper.CaseMapper;
import com.nantian.att.main.web.mapper.DataForSelection;

@Service
@Transactional(isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class BusinessService {
	private static Logger logger = LoggerFactory.getLogger(BusinessService.class);
	@Autowired
	private BusinessMapper bMapper = null;
	@Autowired
	private CaseCollectionMapper ccMapper = null;
	@Autowired
	private CaseMapper cMapper = null;
	
	public List<DataForSelection> findBusinessesbByBusCollId(String busCollId){
		 List<Business> bus = bMapper.selectBusinessesbByBusCollId(busCollId);
		 List<DataForSelection> temp = new ArrayList<DataForSelection>();
		 for (Business business : bus) {
			temp.add(new DataForSelection(business.getId(), business.getName()));
		}
		 return temp;
	}
	
	public String removeBusniessByBusId(String busId){
		List<CaseCollection> caseColls = ccMapper.selectCaseGroupsByBusId(busId);
		int res = bMapper.deleteBusByBusId(busId);
		if(res > 0){
			ccMapper.deletecaseCollByBusId(busId);
			for (CaseCollection caseCollection : caseColls) {
				cMapper.deleteCasesByCaseCollId(caseCollection.getId());
			}
			return "true";
		}
		logger.info("删除业务失败");
		return "false";
	}
	
	public String modifyBus(String formData){
		Business business = JSONObject.parseObject(formData, Business.class);
		Date createTime = bMapper.selectBusByBusId(business.getId()).getCreateTime();
		business.setCreateTime(createTime);
		int res = bMapper.updateBus(business);
		if(res > 0){
			return "true";
		}else{
			logger.info("更新业务失败");
			return "false";
		}
			
	}
	
	public String addBus(String formData){
		Business business = JSONObject.parseObject(formData, Business.class);
		business.setId(UUID.randomUUID().toString().replace("-", ""));
		business.setCreateTime(new Date());
		int res = bMapper.insertBus(business);
		if(res > 0){
			return "true";
		}else{
			logger.info("增加业务失败");
			return "false";
		}
	}
}
