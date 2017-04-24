package com.nantian.att.main.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.nantian.att.main.web.entity.Case;
import com.nantian.att.main.web.entity.Data;
import com.nantian.att.main.web.message.MessageHandler;
import com.nantian.att.main.web.message.impl.JsonFormat;
import com.nantian.att.main.web.message.impl.SopFormat;
import com.nantian.att.main.web.message.impl.XmlFormat;
import com.nantian.att.main.web.service.CaseService;
import com.nantian.att.main.web.util.GetUUID;
import com.nantian.att.main.web.util.ReadFile;

@Controller
@RequestMapping("/case")
public class CaseController {
	private static Logger logger = LoggerFactory.getLogger(CaseController.class);
	@Autowired
	private CaseService cs = null;
	
	@RequestMapping("/findCasesByCaseCollId")
	public @ResponseBody List<Case> findCasesByCaseCollId(String caseCollId){
		return cs.findCasesByCaseCollId(caseCollId);
	}
	
	@RequestMapping("/findCaseByCaseId")
	public @ResponseBody Case findCaseByCaseId(String caseId){
		return cs.findCaseByCaseId(caseId);
	}
	
	@RequestMapping("/findTreeDataByCaseId")
	public @ResponseBody String  findTreeDataByCaseId(String caseId){
		if(caseId != null){
			return cs.findCaseByCaseId(caseId).getTreeData();
		}
		return null;
	}
	
	@RequestMapping("/modifyCaseByCaseId")
	public  @ResponseBody int modifyCaseByCaseId(String formData){
		 return cs.modifyCaseByCaseId(formData);
	}
	
	@RequestMapping("/modifyTreeOfCaseByCaseId")
	public  @ResponseBody String modifyTreeOfCaseByCaseId(String treeData,String mesFormat,String caseId){
		 return cs.modifyTreeOfCaseByCaseId(treeData,mesFormat,caseId);
	}
	
	@RequestMapping("/addCase")
	public @ResponseBody String addCase(String formData,String treeData,String mesFormat){
		return cs.addCase(formData, treeData,mesFormat);
	}
	
	@RequestMapping("/removeCase")
	public @ResponseBody String removeCase(String caseId,String caseCollId){
		return cs.removeCase(caseId,caseCollId);
	}
	
	@RequestMapping("/messageScan")
	public @ResponseBody String messageScan(String treeData,String mesFormat){
		MessageHandler<Data> mFormat = null;
		String res = "";
		try {
			if(mesFormat.equals("xml")){
				mFormat = new XmlFormat();
				res = mFormat.parseTree2Message(treeData);
			}else if(mesFormat.equals("insop") || mesFormat.equals("outsop")){
				mFormat = new SopFormat();
				res = mFormat.parseTree2Message(treeData);
			}else if(mesFormat.equals("json")){
				mFormat = new JsonFormat();
				res = mFormat.parseTree2Message(treeData);
			}
			return res;
		} catch (Exception e) {
			logger.error("预览报文错误:"+e.getStackTrace().toString());
		}
		return res;
	}
	
	@RequestMapping("/upload")
	public @ResponseBody String upload(@RequestParam("upload") MultipartFile files,HttpSession session,String fileFormat,HttpServletResponse response) {
		String path = session.getServletContext().getRealPath("/up");
		File myDir = new File(path);
		String fileNameServe = null;
		String resJson = "";
		File file = null;
		try {
			if(!myDir.exists() && !myDir.isDirectory()){
				myDir.mkdir();
			}
			String fileName = GetUUID.randomId();
			String extension = FilenameUtils.getExtension(files.getOriginalFilename());
			fileNameServe = fileName+"."+extension;
			file = new File(path+File.separator+fileNameServe);
			files.transferTo(file);
			String fileContent = ReadFile.readFile2Byte(file);
			MessageHandler<Data> mFormat = null;
			List<Data> list = null;
			if(fileFormat.equals("xml")){
				mFormat= new XmlFormat();
				list = mFormat.parseMessage2Tree(fileContent);
			}else if(fileFormat.equals("insop")){
				SopFormat sopFormat = new SopFormat();
				list = sopFormat.parseMessage2Tree(fileContent, false);
			}else if(fileFormat.equals("outsop")){
				SopFormat sopFormat = new SopFormat();
				list = sopFormat.parseMessage2Tree(fileContent, true);
			}else if(fileFormat.equals("json")){
				mFormat= new JsonFormat();
				list = mFormat.parseMessage2Tree(fileContent);
			}
			resJson = JSONObject.toJSONString(list);
			return resJson;
		} catch (IllegalStateException e) {
			logger.error("上传报文文件中错误："+e.getStackTrace().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("上传报文文件中IO错误："+e.getStackTrace().toString());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("上传报文文件中IO错误："+e.getStackTrace().toString());
		}
		resJson = JSONObject.toJSONString("false");
		return resJson;
	}
	
	@RequestMapping("/sendSop2Server")
	public @ResponseBody List<String> sendSop2Server(String caseId,String dataFormat){
		List<String> receiveData = new ArrayList<String>();
		try {
			receiveData = cs.sendSop2Server(caseId,dataFormat);
			cs.modifyResultByCaseId(caseId, JSONObject.toJSONString(receiveData));
			return receiveData;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("发送报文数据有误："+e.getMessage());
		}
		receiveData.add("可能数据有误，请检查......");
		return receiveData;
	}
	
	@RequestMapping("/makeReceiveData2Tree")
	public @ResponseBody List<Data> makeReceiveData2Tree(String message,String dataFormat){
		MessageHandler<Data> mFormat = null;
		List<Data> list = null;
		if(dataFormat.equals("xml")){
			mFormat= new XmlFormat();
			list = mFormat.parseMessage2Tree(message);
		}else if(dataFormat.equals("insop")){
			SopFormat sopFormat = new SopFormat();
			list = sopFormat.parseMessage2Tree(message, false);
		}else if(dataFormat.equals("outsop")){
			SopFormat sopFormat = new SopFormat();
			list = sopFormat.parseMessage2Tree(message, true);
		}else if(dataFormat.equals("json")){
			mFormat= new JsonFormat();
			list = mFormat.parseMessage2Tree(message);
		}
		return list;
	}
	
	@RequestMapping("/uploadMeaasgeLogFile")
	public @ResponseBody String uploadMeaasgeLogFile(@RequestParam("upload") MultipartFile messageLogFiles,HttpSession session,String fileFormat,HttpServletResponse response){
		String path = session.getServletContext().getRealPath("");
		path =  path.substring(0, path.lastIndexOf("\\", path.lastIndexOf("\\")-1))+"\\messageLogFiles";
		File myDir = new File(path);
		String fileNameServe = null;
		File file = null;
		try {
			if(!myDir.exists() && !myDir.isDirectory()){
				myDir.mkdir();
			}
			String fileName = GetUUID.randomId();
			String extension = FilenameUtils.getExtension(messageLogFiles.getOriginalFilename());
			fileNameServe = fileName+"."+extension;
			file = new File(path+File.separator+fileNameServe);
			messageLogFiles.transferTo(file);
			return JSONObject.toJSONString(file.getAbsolutePath());
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			logger.error("上传报文日志文件错误："+e.getStackTrace().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("上传报文日志文件中IO错误："+e.getStackTrace().toString());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("上传报文日志文件错误："+e.getStackTrace().toString());
		}
		return JSONObject.toJSONString("false");
	}
	
	@RequestMapping("/downMessageLogFile")
	public @ResponseBody String downMessageFile(String caseId,HttpServletResponse res){
		try {
			Case caze = cs.findCaseByCaseId(caseId);
			String messageLogUrl = caze.getMessageLogUrl();
			String cazeName = caze.getName(); 
			InputStream input = new FileInputStream(messageLogUrl);
			res.setHeader("content-disposition", "attachment;filename="+cazeName+".log");
			IOUtils.copy(input, res.getOutputStream());
			return null;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("下载报文日志文件找不到文件路径错误："+e.getStackTrace().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("下载报文日志文件IO错误："+e.getStackTrace().toString());
		}
		return null;
	}
	
	@RequestMapping("/modifyMessageLogUrl")
	public @ResponseBody String modifyMessageLogUrl(String caseId,String logPath){
		int res = cs.modifyMessageLogUrl(caseId, logPath);
		if(res > 0){
			return "true";
		}else{
			return "false";
		}
	}
}
