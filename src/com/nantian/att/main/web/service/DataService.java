package com.nantian.att.main.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nantian.att.main.web.entity.Data;
import com.nantian.att.main.web.mapper.DataMapper;

@Service
@Transactional(isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class DataService {

	@Autowired
	private DataMapper dataMapper = null;
	
	public List<Data> findDatasByCaseId(String caseId){
		List<Data> datas = dataMapper.selectDatasByCaseId(caseId);
		for (Data data : datas) {
			if(data.getParentId()!=null){
				for (Data parentData : datas) {
					if(parentData.getId() == data.getParentId()){
						if(parentData.getData()== null){
							List<Data> list = new ArrayList<Data>();
							list.add(data);
							parentData.setData(list);
						}else{
							parentData.getData().add(data);
						}
					}
				}
			}
		}
		return datas;
	}
}
