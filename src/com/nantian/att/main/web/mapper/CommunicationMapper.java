package com.nantian.att.main.web.mapper;

import java.util.List;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nantian.att.main.web.entity.Communication;

@Mapper
public interface CommunicationMapper {
	
	@Select("SELECT COMM_ID AS id, COMM_NAME AS name,COMM_IP AS ip,COMM_PORT AS port,COMM_SERVERNAME AS serverName FROM TOOL_COMMUNICATION WHERE COMM_ID = #{commId}")
	public Communication selectCommById(String commId);
	
	@Select("SELECT COMM_ID AS id, COMM_NAME AS name,COMM_IP AS ip,COMM_PORT AS port,COMM_SERVERNAME AS serverName FROM TOOL_COMMUNICATION")
	public List<Communication> selectComms();
	
	@Update("UPDATE TOOL_COMMUNICATION SET COMM_NAME = #{name},COMM_IP = #{ip},COMM_PORT = #{port},COMM_SERVERNAME = #{serverName} WHERE COMM_ID = #{id}")
	public int updateCommunication(Communication communication);
	
	@Delete("DELETE FROM TOOL_COMMUNICATION WHERE COMM_ID = #{id}")
	public int deleteCommByCommId(String id);
	
	@Insert("INSERT INTO TOOL_COMMUNICATION VALUES(#{id},#{name},#{ip}, #{port},#{serverName})")
	public int insertCommuncation(Communication communication);
}
