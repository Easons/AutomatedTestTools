package com.nantian.att.main.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nantian.att.main.web.entity.BusinessCollection;
import com.nantian.att.main.web.service.BusinessCollectionService;

@Controller
@RequestMapping("/bussColl")
public class BusinessCollectionController {

	@Autowired
	private BusinessCollectionService bs = null;
	
	@RequestMapping("/findAllBusColls")
	public @ResponseBody List<BusinessCollection> findAllBussColls(){
		return bs.findAllBusinessColls();
	}
	
	@RequestMapping("/findAllBusCollsForSelect")
	public @ResponseBody List<BusinessCollection> findAllBusCollsForSelect(){
		return bs.findAllBusCollsForSelect();
	}
	
	@RequestMapping("/addBusColl")
	public @ResponseBody String addBusColl(String formData){
		return bs.addBusColl(formData);
	}
	
	@RequestMapping("/modifyBusColl")
	public @ResponseBody String modifyBusColl(String formData){
		return bs.modifyBusColl(formData);
	}
	
	@RequestMapping("/removeBusColl")
	public @ResponseBody String removeBusColl(String busCollId){
		return bs.removeBusCollById(busCollId);
	}
}
