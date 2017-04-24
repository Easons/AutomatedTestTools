package com.nantian.att.main.web.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.nantian.att.main.web.entity.Business;
import com.nantian.att.main.web.entity.BusinessCollection;
import com.nantian.att.main.web.mapper.BusinessCollectionMapper;
import com.nantian.att.main.web.mapper.BusinessMapper;
import com.nantian.att.main.web.util.GetUUID;

@Service
@Transactional(isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class BusinessCollectionService {
	private static Logger logger = LoggerFactory.getLogger(BusinessCollectionService.class);
	@Autowired
	private BusinessCollectionMapper bcMapper = null;
	@Autowired
	private BusinessMapper bMapper = null;
	@Autowired
	private BusinessService bService = null;
	
	public List<BusinessCollection> findAllBusinessColls(){
		List<BusinessCollection> bsColl = bcMapper.selectBusinessColl();
		for (BusinessCollection businessCollection : bsColl) {
			List<Business> business = bMapper.selectBusinessesbByBusCollId(businessCollection.getId());
			businessCollection.setChildren(business);
		}
		return bsColl;
	}
	
	public String removeBusCollById(String busCollId){
		List<Business> bus = bMapper.selectBusinessesbByBusCollId(busCollId);
		int res = bcMapper.deleteBusCollById(busCollId);
		if(res > 0){
			for (Business business : bus) {
				 bService.removeBusniessByBusId(business.getId());
			}
			return "true";
		}
		logger.info("删除业务集失败");
		return "false";
	}
	
	public String addBusColl(String formData){
		BusinessCollection busColl = JSONObject.parseObject(formData, BusinessCollection.class);
		busColl.setId(GetUUID.randomId());
		busColl.setIcon("dashboard");
		int res = bcMapper.insertBusinessColl(busColl);
		if(res > 0){
			return "true";
		}else{
			logger.info("增加业务集失败");
			return "false";
		}
	}
	
	public String modifyBusColl(String formData){
		BusinessCollection busColl = JSONObject.parseObject(formData, BusinessCollection.class);
		int res = bcMapper.updateBusCollId(busColl);
		if(res > 0){
			return "true";
		}else{
			logger.info("更新业务集失败");
			return "false";
		}
	}
	
	public List<BusinessCollection> findAllBusCollsForSelect(){
		return bcMapper.selectAllBusColls();
	}
}
