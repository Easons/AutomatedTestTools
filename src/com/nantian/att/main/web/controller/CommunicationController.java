package com.nantian.att.main.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nantian.att.main.web.entity.Communication;
import com.nantian.att.main.web.service.CommunicationService;

@Controller
@RequestMapping("/communication")
public class CommunicationController {

	@Autowired
	private CommunicationService commService = null;
	
	@RequestMapping("/findAllComms")
	public @ResponseBody List<Communication> findAllComms(){
		return commService.findComms();
	}
	
	@RequestMapping("/addCommunication")
	public @ResponseBody String addCommunication(String formData){
		int res = commService.addCommunication(formData);
		if(res > 0){
			return "true";
		}
		return "false";
	}
	
	@RequestMapping("/modifyCommunication")
	public @ResponseBody String modifyCommunication(String formData){
		int res = commService.modifyCommunication(formData);
		if(res > 0){
			return "true";
		}
		return "false";
	}
	
	@RequestMapping("/removeCommunication")
	public @ResponseBody String removeCommunication(String commId){
		int res = commService.removeCommunication(commId);
		if(res > 0){
			return "true";
		}
		return "false";
	}
}
