package com.nantian.att.main.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nantian.att.main.web.entity.Data;
import com.nantian.att.main.web.service.DataService;

@Controller
@RequestMapping("/data")
public class DataController {

	@Autowired
	private DataService dataService = null;
	
	@RequestMapping("/findDatasByCaseId")
	public @ResponseBody List<Data> findDatasByCaseId(String caseId){
		List<Data> datas = dataService.findDatasByCaseId(caseId);
		return datas;
	}
}
