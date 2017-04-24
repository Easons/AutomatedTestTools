package com.nantian.att.main.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nantian.att.main.web.entity.CaseCollection;
import com.nantian.att.main.web.service.CaseCollectionService;

@Controller
@RequestMapping("/caseColl")
public class CaseCollectionController {

	@Autowired
	private CaseCollectionService ccs = null;
	
	@RequestMapping("/findCaseCollsByBusId")
	public @ResponseBody List<CaseCollection> findCaseCollsByBusId(String busId){
		return ccs.findCaseCollsByBusId(busId);
	}
	
	@RequestMapping("/addCaseColl")
	public @ResponseBody String addCaseColl(String formData){
		 ccs.addCaseColl(formData);
		 return null;
	}
	
	@RequestMapping("/removeCaseColl")
	public @ResponseBody String removeCaseColl(String caseCollId){
		return ccs.removeCaseColl(caseCollId);
	}
	
	@RequestMapping("/modifyCaseColl")
	public @ResponseBody String modifyCaseColl(String formData){
		ccs.modifyCaseColl(formData);
		return null;
	}
}
