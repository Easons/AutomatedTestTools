package com.nantian.att.main.web.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.nantian.att.main.web.entity.Communication;
import com.nantian.att.main.web.mapper.CommunicationMapper;
import com.nantian.att.main.web.util.GetUUID;

@Service
@Transactional(isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class CommunicationService {

	@Autowired
	private CommunicationMapper commMapper = null;
	
	public List<Communication> findComms(){
		return commMapper.selectComms();
	}
	/**
	 * 拿到发送数据关于ip、port、服务名组成的数组
	 * @return
	 * @throws IOException 
	 */
	public byte[] getCommunicationByte(String commId) throws IOException{
		Communication communication = commMapper.selectCommById(commId);
		String ip = communication.getIp();
		String port = communication.getPort();
		String serverName = communication.getServerName();
		byte[] ipBytes = ip.getBytes();
		byte[] portBytes = port.getBytes();
		byte[] serveerBytes = serverName.getBytes();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos.write(new byte[]{3});
		bos.write(new byte[]{(byte) ipBytes.length});
		bos.write(ipBytes);
		bos.write(new byte[]{(byte) portBytes.length});
		bos.write(portBytes);
		bos.write(new byte[]{(byte) serveerBytes.length});
		bos.write(serveerBytes);
		return bos.toByteArray();
	}
	
	public int addCommunication(String formData){
		Communication communication = JSONObject.parseObject(formData,Communication.class);
		communication.setId(GetUUID.randomId());
		return commMapper.insertCommuncation(communication);
	}
	
	public int modifyCommunication(String formData){
		Communication communication = JSONObject.parseObject(formData,Communication.class);
		return commMapper.updateCommunication(communication);
	}
	
	public int removeCommunication(String commId){
		return commMapper.deleteCommByCommId(commId);
	}
	
}
