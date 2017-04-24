package com.nantian.att.main.web.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.nantian.att.main.web.client.ATTClient;
import com.nantian.att.main.web.entity.Case;
import com.nantian.att.main.web.entity.Data;
import com.nantian.att.main.web.mapper.CaseMapper;
import com.nantian.att.main.web.message.MessageHandler;
import com.nantian.att.main.web.message.impl.JsonFormat;
import com.nantian.att.main.web.message.impl.SopFormat;
import com.nantian.att.main.web.message.impl.XmlFormat;
import com.nantian.att.main.web.util.MakeTreeData2List;
import com.nantian.att.main.web.util.SopUtil;

@Service
@Transactional(isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class CaseService {
	private static Logger logger = LoggerFactory.getLogger(CaseService.class);
	@Autowired
	private CaseMapper cMapper = null;
	@Autowired
	private CommunicationService communicationService = null;
	
	public List<Case> findCasesByCaseCollId(String caseCollId){
		 List<Case> cases = cMapper.selectCasesByCaseCollId(caseCollId);
		 if(cases.size() == 0 ){
			 List<Case> temp = new ArrayList<Case>();
			 temp.add(new Case("请添加", "请添加"));
			 return temp;
		 }
		 return cases;
	}
	
	public Case findCaseByCaseId(String caseId){
		return  cMapper.selectCasesByCaseId(caseId);
	}
	
	public int modifyCaseByCaseId(String formData){
		Case c = JSONObject.parseObject(formData, Case.class);
		c.setModifyTime(new Date());
		int res = cMapper.updateCaseByCaseId(c);
		if(res > 0){
			 return res;
		 }
		logger.info("更新案例失败");
		return 0;
	}
	
	public String modifyTreeOfCaseByCaseId(String treeData,String mesFormat,String caseId){
		Case c = new Case();
		c.setId(caseId);
		c.setModifyTime(new Date());
		String jsonString = null;
		if(mesFormat.equals("insop") || mesFormat.equals("outsop")){
			SopFormat sopFormat = new SopFormat();
			String sop = sopFormat.parseTree2Message(treeData);
			List<Data> temp = new ArrayList<Data>();
			if(mesFormat.equals("insop")){
				temp = sopFormat.parseMessage2Tree(sop, false);
			}else{
				temp = sopFormat.parseMessage2Tree(sop, true);
			}
			jsonString = JSONObject.toJSONString(temp);
		}else{
			jsonString = MakeTreeData2List.TreeData2ListJson(treeData);
		}
		c.setTreeData(jsonString);
		int res = cMapper.updateTreeOfCaseByCaseId(c);
		if(res > 0){
			return jsonString;
		}
		logger.info("更新案例中的树信息失败");
		return null;
	}
	
	public String addCase(String formData,String treeData,String mesFormat){
		Case c = JSONObject.parseObject(formData, Case.class);
		c.setId(UUID.randomUUID().toString().replace("-", ""));
		c.setCreateTime(new Date());
		c.setModifyTime(new Date());
		String jsonString = null;
		try {
			if(mesFormat.equals("insop") || mesFormat.equals("outsop")){
				SopFormat sopFormat = new SopFormat();
				String sop = sopFormat.parseTree2Message(treeData);
				List<Data> temp = new ArrayList<Data>();
				if(mesFormat.equals("insop"))
					temp = sopFormat.parseMessage2Tree(sop, false);
				else
					temp = sopFormat.parseMessage2Tree(sop, true);
				jsonString = JSONObject.toJSONString(temp);
			}else{
				jsonString = MakeTreeData2List.TreeData2ListJson(treeData);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("增加案例中，报文转换成树出错："+e.getStackTrace().toString());
			return "false";
		}
		c.setTreeData(jsonString);
		int res = cMapper.insertCase(c);
		if(res > 0){
			return "true";
		}
		logger.info("增加案例失败");
		return "false";
	}
	
	public String removeCase(String caseId,String caseCollId){
		int res = cMapper.deleteCaseByCaseId(caseId);
		if(res > 0){
			return "true";
		}
		logger.info("删除案例失败");
		return "false";
	}
	/**
	 * 组装发送报文
	 * @param caseId
	 * @param dataFormat
	 * @return
	 */
	public List<String> sendSop2Server(String caseId,String dataFormat){
		List<String> receiveData = null;
		Case cas = cMapper.selectCasesByCaseId(caseId);
		try {
			if(cas != null){
				String commId = cas.getCommunicationId();
				String treeData = cas.getTreeData();
				MessageHandler<Data> messageFormat = null;
				byte messageByte[] = null;
				if(dataFormat.equals("insop") || dataFormat.equals("outsop")){
					SopFormat sopFormat = new SopFormat();
					String sop = sopFormat.parseTree2Message(treeData);
					sop = SopUtil.removeTrim(sop);
					messageByte = new byte[sop.length() / 2];
					for (int i = 0; i < messageByte.length; i++) {
						messageByte[i] = (byte) (0xff & Integer.parseInt(sop.substring(i * 2, i * 2 + 2), 16));
					}
				}else if(dataFormat.equals("json")){
					messageFormat = new JsonFormat();
					String json = messageFormat.parseTree2Message(treeData);
					messageByte = json.getBytes();
				}else if(dataFormat.equals("xml")){
					messageFormat = new XmlFormat();
					String xml = messageFormat.parseTree2Message(treeData);
					xml = SopUtil.removeTrim(xml);
					messageByte = xml.getBytes();
				}
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				if(commId != null){
					byte[] communicationByte = communicationService.getCommunicationByte(commId);
					bos.write(communicationByte);
				}
					bos.write(messageByte);
					byte[] temp = bos.toByteArray();
					//得到总长度byte数组，长度不够4时 自动补0
					byte[] totalLengthBytes = BigInteger.valueOf(temp.length).toByteArray();
					int length = totalLengthBytes.length;
					byte[] tempForAddLen = null;
					if(length < 4){
						tempForAddLen = new byte[4-length];
						for (int i = 0; i < tempForAddLen.length; i++) {
							tempForAddLen[i] = 0;
						}
						
					}
					ByteArrayOutputStream bosRes = new ByteArrayOutputStream();
					if(tempForAddLen != null){
						bosRes.write(tempForAddLen);
					}
					bosRes.write(totalLengthBytes);
					bosRes.write(temp);
					byte[] ResByte = bosRes.toByteArray();
					ATTClient attClient = new ATTClient();
					receiveData = attClient.sendData(ResByte,dataFormat);
			}
		} catch (NumberFormatException e) {
			logger.error("发送报文时，报文数字转换异常："+e.getStackTrace().toString());
		} catch (IOException e) {
			logger.error("发送报文时io异常："+e.getStackTrace().toString());
		}
		List<String> res = new ArrayList<String>();
		if(receiveData != null){
			for (String str : receiveData) {
				StringBuffer sb = new StringBuffer(str);
				int index ;
				int limit = str.length()/32 + str.length();
				if(str.length() % 32 != 0){
					limit += 1;
				}
				for (index = 32; index < limit; index+=33) {
						sb.insert(index, "\n");
				}
				//给2位16进制数加空格
				str= sb.toString();
				String regex = "(.{2})";
				str = str.replaceAll (regex, "$1 ");
				res.add(str);
			}
		}else{
			logger.info("发送报文时，没有数据返回");
			res.add("没有数据返回......");
		}
		return res;
	}
	
	public int modifyResultByCaseId(String id,String result){
		Case caze = new Case();
		caze.setId(id);
		caze.setModifyTime(new Date());
		caze.setResult(result);
		return cMapper.updateResultByCaseId(caze);
	}
	
	public int modifyMessageLogUrl(String id,String logUrl){
		Case caze = new Case();
		caze.setId(id);
		caze.setModifyTime(new Date());
		caze.setMessageLogUrl(logUrl);
		return cMapper.updateMessageLogUrlByCaseId(caze);
	}
}
