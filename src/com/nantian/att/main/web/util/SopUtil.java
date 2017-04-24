package com.nantian.att.main.web.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.att.main.web.entity.Data;


public class SopUtil {
	private static Logger logger = LoggerFactory.getLogger(SopUtil.class);
	/**
	 * 以下方法为正解析的方法
	 */
	public static String removeTrim(String str){
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(str);
		str = m.replaceAll("");
		return str;
	}
	
	public static Integer getTotalLength(String str){
		if(str.length() > 2){
			return Integer.parseInt(str.substring(0, str.length()), 16);
		}
		return -1;
	}
	
	public static Integer getDataLength(String str){
		if(str.length() > 2){
			return Integer.parseInt(str.substring(0, 2), 16);
		}else if(str.equals("00")){
			return 0;
		}
		return -1;
	}
	
	public static String hexToString(String hexStr){
		byte[] baKeyword = new byte[hexStr.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
		try {
		baKeyword[i] = (byte) (0xff & Integer.parseInt(hexStr.substring(i * 2, i * 2 + 2), 16));
		} catch (Exception e) {
			logger.error("做报文正解析，16进制转换成字符串错误："+e.getStackTrace().toString());
		return "";
		}
		}
		try {
		hexStr = new String(baKeyword, "GBK");// UTF-16le:Not
		} catch (Exception e1) {
			logger.error("做报文正解析，16进制转换成字符串错误："+e1.getStackTrace().toString());
		return "";
		}
		return hexStr;
	}
	
	public static List<Data> getSystemMessageHead(String str,String pId){
		List<Data> head = new ArrayList<Data>();
	/*	head.add(new Data(GetUUID.randomId(),"数据包长度",getTotalLength(str.substring(0, 4)).toString(),pId));*/
		head.add(new Data(GetUUID.randomId(),"报文MAC", hexToString(str.substring(4,36)),pId));
		head.add(new Data(GetUUID.randomId(),"MAC机构号", hexToString(str.substring(36,44)),pId));
		head.add(new Data(GetUUID.randomId(),"PIN种子", hexToString(str.substring(44,76)),pId));
		head.add(new Data(GetUUID.randomId(),"渠道来源", hexToString(str.substring(76,82)),pId));
		head.add(new Data(GetUUID.randomId(),"渠道去向", hexToString(str.substring(82,88)),pId));
		head.add(new Data(GetUUID.randomId(),"加密标志", hexToString(str.substring(88,90)),pId));
		head.add(new Data(GetUUID.randomId(),"PIN标志", hexToString(str.substring(90,92)),pId));
		head.add(new Data(GetUUID.randomId(),"组合标志", hexToString(str.substring(92,94)),pId));
		head.add(new Data(GetUUID.randomId(),"主机服务名", hexToString(str.substring(94,112)),pId));
		head.add(new Data(GetUUID.randomId(),"信息结束标志", hexToString(str.substring(112,114)),pId));
		head.add(new Data(GetUUID.randomId(),"报文序号", getTotalLength(str.substring(114,118)).toString(),pId));
		head.add(new Data(GetUUID.randomId(),"校验标志", hexToString(str.substring(118,120)),pId));
		head.add(new Data(GetUUID.randomId(),"密钥版本号", getTotalLength(str.substring(120,128)).toString(),pId));
		head.add(new Data(GetUUID.randomId(),"系统标识符", hexToString(str.substring(128,134)),pId));
		head.add(new Data(GetUUID.randomId(),"密码偏移量", hexToString(str.substring(134,174)),pId));
		head.add(new Data(GetUUID.randomId(),"帐号偏移量", hexToString(str.substring(174,214)),pId));
		return head;
	}
	
	public static List<Data> getInDealPublishMessageHead(String str,String pId){
		List<Data> dpmHead = new ArrayList<Data>();
		dpmHead.add(new Data(GetUUID.randomId(),"终端号", hexToString(str.substring(214,224)),pId));
		dpmHead.add(new Data(GetUUID.randomId(),"城市代码", hexToString(str.substring(224,232)),pId));
		dpmHead.add(new Data(GetUUID.randomId(),"机构代码", hexToString(str.substring(232,240)),pId));
		dpmHead.add(new Data(GetUUID.randomId(),"交易柜员", hexToString(str.substring(240,256)),pId));
		return dpmHead;
	}
	
	public static List<Data> getDealDataHead(String str,String pId){
		List<Data> ddHead = new ArrayList<Data>();
		ddHead.add(new Data(GetUUID.randomId(),"交易代码",hexToString(str.substring(0, 8)),pId));
		ddHead.add(new Data(GetUUID.randomId(),"交易子码",hexToString(str.substring(8,12)),pId));
		ddHead.add(new Data(GetUUID.randomId(),"交易模式",hexToString(str.substring(12,14)),pId));
		ddHead.add(new Data(GetUUID.randomId(),"交易序号",getTotalLength(str.substring(14,22)).toString(),pId));
		ddHead.add(new Data(GetUUID.randomId(),"本交易包长度",getTotalLength(str.substring(22,26)).toString(),pId));
		ddHead.add(new Data(GetUUID.randomId(),"系统偏移1",getTotalLength(str.substring(26,30)).toString(),pId));
		ddHead.add(new Data(GetUUID.randomId(),"系统偏移2",getTotalLength(str.substring(30,34)).toString(),pId));
		ddHead.add(new Data(GetUUID.randomId(),"前台流水号",hexToString(str.substring(34,58)),pId));
		ddHead.add(new Data(GetUUID.randomId(),"前台日期",hexToString(str.substring(58,74)),pId));
		ddHead.add(new Data(GetUUID.randomId(),"授权柜员",hexToString(str.substring(74,90)),pId));
		ddHead.add(new Data(GetUUID.randomId(),"授权密码",hexToString(str.substring(90,122)),pId));
		ddHead.add(new Data(GetUUID.randomId(),"授权柜员有无卡标志",hexToString(str.substring(122,124)),pId));
		ddHead.add(new Data(GetUUID.randomId(),"授权柜员卡序号",hexToString(str.substring(124,128)),pId));
		return ddHead;
 	}
	
	public static List<Data> getOutDealPublishMessageHead(String str,String pId){
		List<Data> dpmHead = new ArrayList<Data>();
		dpmHead.add(new Data(GetUUID.randomId(),"交易代码", hexToString(str.substring(214,222)),pId));
		dpmHead.add(new Data(GetUUID.randomId(),"联动交易码", hexToString(str.substring(222,230)),pId));
		dpmHead.add(new Data(GetUUID.randomId(),"交易日期", hexToString(str.substring(230,246)),pId));
		dpmHead.add(new Data(GetUUID.randomId(),"交易时间", String.valueOf(Integer.parseInt(str.substring(246,254), 16)),pId));
		dpmHead.add(new Data(GetUUID.randomId(),"柜员流水号", hexToString(str.substring(254,278)),pId));
		dpmHead.add(new Data(GetUUID.randomId(),"出错交易序号", String.valueOf(Integer.parseInt(str.substring(278,282), 16)),pId));
		dpmHead.add(new Data(GetUUID.randomId(),"错误代号", hexToString(str.substring(282,296)),pId));
		return dpmHead;
	}
	
	/**
	 * 
	 * @param str 数据字符串
	 * @param list 存放各个数据的list
	 * @param FormUnitId 表格单元的ID
	 * @param recordId 表格单元中的每条记录的Id
	 * @param formLine 表格有多少行
	 * @param formColumn2Sub 表格的列递减，为了方法中能够得知这条记录中包含的单元
	 * @param formColumn 表格中有多少列
	 * @param dataOversizeId 超长的数据单元的Id
	 * @param objId 对象单元的Id
	 * @param pId 交易数据头的Id
	 * @return
	 */
	public static List<Data> getBussinessData(String str, List<Data> list,
			String FormUnitId, String recordId, Integer formLine,
			Integer formColumn2Sub, Integer formColumn, String dataOversizeId, String objId ,String pId) {
		Integer dataLength = getDataLength(str);
		Integer totalLength = str.length();
		if (dataLength != -1) {
			if(dataLength == 255){
				dataLength = 250;
			}
			Integer dataContentEndIndex = dataLength * 2 + 2;
			if (dataContentEndIndex > totalLength) {
				logger.error("做报文正解析中业务数据,内容大于长度错误：totalLength（总长度）："+totalLength+"dataContentEndIndex:（内容结束下标）"+dataContentEndIndex);
				throw new RuntimeException("数据错误");
			} else {
				String dataContent = hexToString(str.substring(2,
						dataContentEndIndex).toString());
				if (dataContent.startsWith("F")) {// 表格单元
					String formName = dataContent;
					Integer line = Integer.parseInt(str.substring(
							dataContentEndIndex, dataContentEndIndex + 2), 16);
					Integer column = Integer.parseInt(str.substring(
							dataContentEndIndex + 2, dataContentEndIndex + 4),
							16);
					/*String FormContent = "表格名称[" + formName + "]行数["
							+ line.toString() + "]列数[" + column.toString() + "]";*/
					Data formSop = new Data(GetUUID.randomId(), "表格单元", formName, pId);
					Data ColumnSop = new Data(GetUUID.randomId(), "列", column.toString(), formSop.getId());
					Data LineSop = new Data(GetUUID.randomId(), "行", line.toString(), formSop.getId());
					if(objId != null){
						formSop.setParentId(objId);
					}
					list.add(formSop);
					list.add(LineSop);
					list.add(ColumnSop);
					getBussinessData(
							str.substring(dataContentEndIndex + 4, totalLength),
							list, formSop.getId(), null, line, column, column,null,objId,
							pId);// 穿表格单元id找到表格单元包含的数据单元
				} else if(dataContent.startsWith("O")){
					String ObjName = dataContent;
					Data sop = new Data(GetUUID.randomId(), "对象单元", ObjName, pId);
					list.add(sop);
					getBussinessData(str.substring(dataContentEndIndex,totalLength), list, null, null, 0, 0, 0,null, sop.getId(), pId);
				}else {// 数据单元
					Data unitData = null;
					if (FormUnitId != null || formLine > 0) {//表格中的数据单元
						Data record = null;
						if (recordId == null){//判断当前数据单元属于哪一个记录(一个记录可能包含多个数据单元)
							record = new Data(GetUUID.randomId(), "记录" + formLine, null,
									FormUnitId);
							list.add(record);
						}else{
							record = new Data(recordId);
						}
						unitData = new Data(GetUUID.randomId(), "单元数据", dataContent,
								record.getId());
						if(dataOversizeId != null){
							unitData.setParentId(dataOversizeId);
						}
						if(dataLength == 250){
							dataOversizeId = unitData.getId();
						}else{
							dataOversizeId = null;
						}
						list.add(unitData);
						formColumn2Sub--;
						if (formColumn2Sub > 0) {
							getBussinessData(str.substring(dataContentEndIndex,
									totalLength), list, FormUnitId,
									record.getId(), formLine, formColumn2Sub,
									formColumn,dataOversizeId,objId,pId);
						} else {
							formLine--;
							if (formLine > 0)
								getBussinessData(str.substring(
										dataContentEndIndex, totalLength),
										list, FormUnitId, null, formLine,
										formColumn, formColumn,dataOversizeId, objId ,pId);
							else
								getBussinessData(str.substring(
										dataContentEndIndex, totalLength),
										list, null, null, 0, 0, 0,dataOversizeId, objId ,pId);
						}
					}else{
						unitData = new Data(GetUUID.randomId(), "单元数据", dataContent, pId);
						if(objId != null){
							unitData.setParentId(objId);
						}
						if(dataOversizeId != null){
							unitData.setParentId(dataOversizeId);
						}
						if(dataLength == 250){
							dataOversizeId = unitData.getId();
						}else{
							dataOversizeId = null;
						}
						list.add(unitData);
						getBussinessData(
								str.substring(dataContentEndIndex, totalLength),
								list, null, null, 0, 0, 0,dataOversizeId ,objId, pId);
					}
				}
			}
			return list;
		}
		return list;
	}
	
	public static List<Data> getBuss(String sop,List<Data> list,Boolean isOut,Integer isGroup){
		int total = sop.length();
		if(!isOut){
			Data dealDataHead = new Data(GetUUID.randomId(), "交易数据头", "");
			List<Data> inDealDataHeadChild = getDealDataHead(sop.substring(0, 128), dealDataHead.getId());
			dealDataHead.setData(inDealDataHeadChild);
			list.add(dealDataHead);
			//有本包交易长度时，解析组合报文使用，现不使用
			if(isGroup == 1){
				int thisDataLength = total;
				for (Data data : inDealDataHeadChild) {
					if(data.getName().equals("本交易包长度")){
						if((Integer.parseInt(data.getValue()) > 64)) //判断本包交易长度是否有 
							thisDataLength = Integer.parseInt(data.getValue())*2;
						}
				}
				Data bussiData = new Data(GetUUID.randomId(), "业务数据", "");
				List<Data> bussinessDataChild = null;
				if(thisDataLength < total){
					bussinessDataChild = getBussinessData(sop.substring(128, thisDataLength), new ArrayList<Data>(), null, null, 0, 0, 0, null,null, bussiData.getId());
					bussiData.setData(MakeTreeData2List.listBussinessData2TreeList(bussinessDataChild,bussiData.getId()));
					list.add(bussiData);
					getBuss(sop.substring(thisDataLength, total), list,false,1);
				}else{
					bussinessDataChild = getBussinessData(sop.substring(128, total), new ArrayList<Data>(), null, null, 0, 0, 0,null, null, bussiData.getId());
					bussiData.setData(MakeTreeData2List.listBussinessData2TreeList(bussinessDataChild,bussiData.getId()));
					list.add(bussiData);
				}
			}else{
				Data bussiData = new Data(GetUUID.randomId(), "业务数据", "");
				List<Data> bussinessDataChild = null;
				bussinessDataChild = getBussinessData(sop.substring(128, total), new ArrayList<Data>(), null, null, 0, 0, 0,null, null, bussiData.getId());
				bussiData.setData(MakeTreeData2List.listBussinessData2TreeList(bussinessDataChild,bussiData.getId()));
				list.add(bussiData);
			}
			/*int thisDataLength = total;
			for (Data data : inDealDataHeadChild) {
				if(data.getName().equals("本交易包长度")){
					if(data.getValue()!=null && !data.getValue().equals("0")) //判断本包交易长度是否有 
						thisDataLength = Integer.parseInt(data.getValue())*2;
					}
			}
			Data bussiData = new Data(GetUUID.randomId(), "业务数据", "");
			List<Data> bussinessDataChild = null;
			if(thisDataLength < total){
				bussinessDataChild = getBussinessData(sop.substring(128, thisDataLength), new ArrayList<Data>(), null, null, 0, 0, 0, null,null, bussiData.getId());
				bussiData.setData(bussinessDataChild);
				list.add(bussiData);
				getBuss(sop.substring(thisDataLength, total), list,false);
			}else{
				bussinessDataChild = getBussinessData(sop.substring(128, total), new ArrayList<Data>(), null, null, 0, 0, 0,null, null, bussiData.getId());
				bussiData.setData(bussinessDataChild);
				list.add(bussiData);
			}*/
		}else{
			Data bussiData = new Data(GetUUID.randomId(), "业务数据", "");
			List<Data> bussinessDataChild = null;
			bussinessDataChild = getBussinessData(sop.substring(0, total), new ArrayList<Data>(), null, null, 0, 0, 0, null,null, bussiData.getId());
			bussiData.setData(MakeTreeData2List.listBussinessData2TreeList(bussinessDataChild,bussiData.getId()));
			list.add(bussiData);
		}
		return list;
		
	}
	
	/**
	 * 以下方法未反解析的方法
	 */
	//获取数字字符串的hex值
	public static String number2Hex(String str,int length){
		Integer integer = Integer.parseInt(str);
		String hexString = Integer.toHexString(integer);
		int sub = length - hexString.length();
		if(sub > 0){
			for (int i = 0; i < sub; i++) {
				hexString = 0+hexString;
			}
		}
		return hexString;
	}
	//获取char类型的hex值
	public static String string2Hex(String str,Integer length){
		String hexResult = HexUtils.getHexResult(str);
		if(length != null){
			int blankLength = length - hexResult.length();
			if(blankLength > 0){
				hexResult = hexResult + HexUtils.addZero(blankLength);
			}
		}
		return hexResult;

	}
	//对结果进行格式化和计算长度
	public static String makeResformat(String str){
		Integer totalLength = (str.length()+4)/2;
		String soplength = number2Hex(totalLength.toString(), 4);
		str = soplength + str;
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
		String res = sb.toString();
		String regex = "(.{2})";
		res = res.replaceAll (regex, "$1 ");
		return res;
	}
	//反解析系统信息头
	public static String getHexSystemMessageHead(List<Data> smhead){
		String res = ""; 
		for (Data data : smhead) {
			String name = data.getName();
			String value = data.getValue();
			if(name.equals("数据包长度")){
				continue;
			}else if(name.equals("报文序号")){
				res += number2Hex(value, 4);
			}else if(name.equals("密钥版本号")){
				res += number2Hex(value, 8);
			}else if(name.equals("报文MAC") || name.equals("PIN种子")){
				res += string2Hex(value,32);
			}else if(name.equals("MAC机构号")){
				res += string2Hex(value,8);
			}else if(name.equals("加密标志") || name.equals("PIN标志")|| name.equals("组合标志")|| name.equals("信息结束标志")|| name.equals("校验标志")){
				res += string2Hex(value,2);
			}else if(name.equals("系统标识符") || name.equals("渠道来源")|| name.equals("渠道去向")){
				res += string2Hex(value,6);
			}else if(name.equals("密码偏移量") || name.equals("帐号偏移量")){
				res += string2Hex(value,40);
			}else if(name.equals("主机服务名")){
				res += string2Hex(value,18);
			}
		}
		return res;
	}
	
	//反解析交易公共信息头
	public static String getHexDealPublishMessageHead(List<Data> dpmHead){
		String res = "";
		for (Data data : dpmHead) {
			String name = data.getName();
			String value = data.getValue();
			if(name.equals("终端号")){
				res += string2Hex(value, 10);
			}else if(name.equals("城市代码") || name.equals("机构代码")){
				res += string2Hex(value, 8);
			}else if(name.equals("交易柜员")){
				res += string2Hex(value, 16);
			}
		}
		return res;
	}
	
	//反解析交易返回公共信息头
	public static String getHexDealReturnPublicMessageHead(List<Data> drpmHead){
		String res = "";
		for (Data data : drpmHead) {
			String name = data.getName();
			String value = data.getValue();
			if(name.equals("交易时间")){
				res += number2Hex(value, 8);
			}else if(name.equals("出错交易序号")){
				res += number2Hex(value, 4);
			}else if(name.equals("交易代码") || name.equals("联动交易码")){
				res += string2Hex(value, 8);
			}else if(name.equals("交易日期")){
				res += string2Hex(value, 16);
			}else if(name.equals("柜员流水号")){
				res += string2Hex(value, 24);
			}else if(name.equals("错误代号")){
				res += string2Hex(value, 14);
			}
		}
		return res;
	}
	
	//反解析交易数据头
	public static String getHexDealDataHead(List<Data> ddHead){
		String res = "";
		for (Data data : ddHead) {
			String name = data.getName();
			String value = data.getValue();
			if(name.equals("交易序号")){
				res += number2Hex(value, 8);
			}else if(name.equals("本交易包长度") || name.equals("系统偏移1") || name.equals("系统偏移2")){
				res += number2Hex(value, 4);
			}else if(name.equals("交易代码")){
				res += string2Hex(value, 8);
			}else if(name.equals("交易子码") || name.equals("授权柜员卡序号")){
				res += string2Hex(value, 4);
			}else if(name.equals("交易模式") || name.equals("授权柜员有无卡标志")){
				res += string2Hex(value, 2);
			}else if(name.equals("前台日期") || name.equals("授权柜员")){
				res += string2Hex(value, 16);
			}else if(name.equals("前台流水号")){
				res += string2Hex(value, 24);
			}else if(name.equals("授权密码")){
				res += string2Hex(value, 32);
			}
		}
		return res;
	}
	
	//反解析业务数据
	public static String getHexBussinessData(Data data){
		String res = "";
		String value = data.getValue();
		Integer length = 0;
		if(value.contains("·")){
			length += 1;
		}
		for (int i = 0; i < value.length(); i++) {
			char tempStr = value.charAt(i);  
            String temp = String.valueOf(tempStr); 
	           if (HexUtils.isCN(temp)) {  
	        	   length += 2;   
	           }else{    
	        	   length += 1;    
	           }    
		}
		if(data.getName().equals("行") || data.getName().equals("列")){
			res += number2Hex(value, 2);
		}else{
			String temp2ength = number2Hex(length.toString(), 2);
	        res += temp2ength;
	        res += string2Hex(value,null);
		}
		return res;
	}
	public static String forBussiDataChildren(List<Data> busData,String res){
		if(busData != null){
			for (Data data : busData) {
				if(!data.getName().contains("记录")){
					res += getHexBussinessData(data);
				}
				if(data.getData() != null){
					String str = "";
					res += forBussiDataChildren(data.getData(), str);
				}
			}
			return res;
		}else{
			return res;
		}
	}
	//拿到交易数据头的长度
	public static Integer getPackageDealLength(String str){
		Integer totalLength = (str.length()+128)/2;
		return totalLength;
	}
}
