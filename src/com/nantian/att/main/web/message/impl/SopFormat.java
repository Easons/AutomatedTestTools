package com.nantian.att.main.web.message.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.nantian.att.main.web.entity.Data;
import com.nantian.att.main.web.message.MessageHandler;
import com.nantian.att.main.web.util.GetUUID;
import com.nantian.att.main.web.util.MakeTreeData2List;
import com.nantian.att.main.web.util.SopUtil;

public class SopFormat implements MessageHandler<Data>{

	@Override
	public List<Data> parseMessage2Tree(String message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String parseTree2Message(String treeData) {
		String sopjson = MakeTreeData2List.TreeData2ListJson(treeData);
		List<Data> sopTree = JSONObject.parseArray(sopjson, Data.class);
		//求本包交易长度
		List<Integer> packageDealLen = new ArrayList<Integer>();
		String res = "";
		for (Data data : sopTree) {
			String name = data.getName();
			List<Data> temp = data.getData();
			if(name.equals("系统信息头")){
				res += SopUtil.getHexSystemMessageHead(temp);
			}else if(name.equals("交易公共信息头")){
				res += SopUtil.getHexDealPublishMessageHead(temp);
			}else if(name.equals("交易返回公共信息头")){
				res += SopUtil.getHexDealReturnPublicMessageHead(temp);
			}else if(name.equals("交易数据头")){
				res += SopUtil.getHexDealDataHead(temp);
			}else if (name.equals("业务数据")){
				String str = "";
				String bussiDataStr = SopUtil.forBussiDataChildren(temp, str);
				res += bussiDataStr;
				Integer packageDealLength = SopUtil.getPackageDealLength(bussiDataStr);
				packageDealLen.add(packageDealLength);
			}
		}
		//计算本包交易长度
		for (int i = 0; i < packageDealLen.size(); i++) {
			Integer beforeDealPackageLen = 0;//当为组合交易是求得非第一个交易的本交易包长度数据的下标
			for (int j = 0; j < i; j++) {
				beforeDealPackageLen += packageDealLen.get(j);
				beforeDealPackageLen = beforeDealPackageLen*2;
			}
			//此时res并无加上此报文总长度所以是256-4=252
			String pre = res.substring(0,252+beforeDealPackageLen+22);//获取本包交易长度前面的字符串
			String replace = SopUtil.number2Hex(packageDealLen.get(i).toString(), 4);//获取最新的本包交易长度
			String after = res.substring(252+beforeDealPackageLen+22+4,res.length());//获取本包交易长度以后的字符串
			res = pre+replace+after;
		}
		res = SopUtil.makeResformat(res);
		return res;
	}

	public List<Data> parseMessage2Tree(String message,Boolean isOutSop) {
		List<Data> sopMesage = new ArrayList<Data>();
		List<Data> res = null;
		message = SopUtil.removeTrim(message);
		Integer isGroup = 0;
		Data sysMesHead = new Data(GetUUID.randomId(), "系统信息头", "");
		List<Data> systemMessageHeadChild = SopUtil.getSystemMessageHead(message, sysMesHead.getId());
		for (Data data : systemMessageHeadChild) {
			String name = data.getName();
			if(name.equals("组合标志")){
				isGroup = Integer.parseInt(data.getValue());
			}
		}
		sysMesHead.setData(systemMessageHeadChild);
		sopMesage.add(sysMesHead);
		if(!isOutSop){
			Data dealPubMesHead = new Data(GetUUID.randomId(), "交易公共信息头", "");
			List<Data> dealPublishMessageHeadChild = SopUtil.getInDealPublishMessageHead(message, dealPubMesHead.getId());
			dealPubMesHead.setData(dealPublishMessageHeadChild);
			sopMesage.add(dealPubMesHead);
			res = SopUtil.getBuss(message.substring(256,message.length()), sopMesage,false,isGroup);
		}else{
			Data dealReturnPubMesHead = new Data(GetUUID.randomId(), "交易返回公共信息头", "");
			List<Data> dealReturnPublishMessageHeadChild = SopUtil.getOutDealPublishMessageHead(message, dealReturnPubMesHead.getId());
			dealReturnPubMesHead.setData(dealReturnPublishMessageHeadChild);
			sopMesage.add(dealReturnPubMesHead);
			res = SopUtil.getBuss(message.substring(296,message.length()), sopMesage,true,isGroup);
		}
		return res;
	}
}
