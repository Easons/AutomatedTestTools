package com.nantian.att.main.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nantian.att.main.web.entity.Dictionary;
import com.nantian.att.main.web.service.DictionaryService;

@Controller
@RequestMapping("/dictionary")
public class DictionaryController {

	@Autowired
	private DictionaryService dService = null;
	
	@RequestMapping("/findItemsByParentName")
	public @ResponseBody List<Dictionary> findItemsByParentName(String parentName){
		return dService.findItemsByParentName(parentName);
	}
	
	@RequestMapping("/findParentItems")
	public @ResponseBody List<Dictionary> findParentItems(){
		return dService.findParentItems();
	}
	
	@RequestMapping("/findItemsByParentId")
	public @ResponseBody List<Dictionary> findItemsByParentId(String parentId){
		return dService.findItemsByParentId(parentId);
	}
	
	@RequestMapping("/addDictionary")
	public @ResponseBody String addDictionary(String formData){
		int res = dService.addDictionary(formData);
		if(res > 0){
			return "true";
		}
		return "false";
	}
	
	@RequestMapping("/modifyDictionary")
	public @ResponseBody String modifyDictionary(String formData){
		int res = dService.modifyDictionary(formData);
		if(res > 0){
			return "true";
		}
		return "false";
	}
	
	@RequestMapping("/removeDictionary")
	public @ResponseBody String removeDictionary(String dicId){
		int res = dService.removeDictionary(dicId);
		if(res > 0){
			return "true";
		}
		return "false";
	}
	
	@RequestMapping("/findItems2Select")
	public @ResponseBody List<Dictionary> findItems2Select(){
		return dService.findItems2Select();
	}
}
