package com.nantian.att.main.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nantian.att.main.web.mapper.DataForSelection;
import com.nantian.att.main.web.service.BusinessService;

@Controller
@RequestMapping("/bussiness")
public class BussinessController {

	@Autowired
	private BusinessService bService = null;
	
	@RequestMapping("/findBusinessesbByBusCollId")
	public @ResponseBody List<DataForSelection> findBusinessesbByBusCollId(String busCollId){
		return bService.findBusinessesbByBusCollId(busCollId);
	}
	
	@RequestMapping("/addBusiness")
	public @ResponseBody String addBusniess(String formData){
		return bService.addBus(formData);
	}
	
	@RequestMapping("/removeBusiness")
	public @ResponseBody String removeBusiness(String busId){
		return bService.removeBusniessByBusId(busId);
	}
	
	@RequestMapping("/modifyBusiness")
	public @ResponseBody String modifyBusniess(String formData){
		return bService.modifyBus(formData);
	}
	
}
