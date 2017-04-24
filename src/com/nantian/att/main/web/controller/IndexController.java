package com.nantian.att.main.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IndexController {
	/*@Autowired
	private IndexService indexService = null;*/
	
	@RequestMapping("/")
    public String index(ModelMap map) {
        return "index";  
    }
	
	@RequestMapping("/test")
    public String test(ModelMap map) {
        return "test";  
    }
	
	@RequestMapping("/main")
    public String main(ModelMap map) {
        return "main";  
    }
	
	@RequestMapping("/maintenance")
    public String maintenance(ModelMap map) {
        return "maintenance";  
    }
}
