package com.nantian.att.main.web.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/viewChange")
public class MakeViewChangeController {

	
	@RequestMapping("/addMyTab")
	public String addMyTab(String id,String busCollId,ModelMap modelMap){
		modelMap.addAttribute("id", id);
		modelMap.addAttribute("busCollId", busCollId);
		return "addTab";
	}
	
	@RequestMapping("/replaceMyDataView")
	public String replaceMyDataView(String caseCollId,String busId,ModelMap modelMap){
		modelMap.addAttribute("caseCollId", caseCollId);
		modelMap.addAttribute("busId",busId);
		return "replaceDataView";
	}
	
	@RequestMapping("/getMySelectedItem")
	public String getMySelectedItem(String caseId,ModelMap modelMap){
		modelMap.addAttribute("caseId", caseId);
		return "getSelectedItem";
	}
	
	@RequestMapping("/busCollHandle")
	public String busCollHandle(String buttonId,ModelMap modelMap){
		modelMap.addAttribute("buttonId", buttonId);
		return "busOperation";
	}
	
	@RequestMapping("/MysendOperation")
	public String MysendOperation(String caseId,ModelMap modelMap){
		modelMap.addAttribute("caseId", caseId);
		return "sendOperation";
	}
}
