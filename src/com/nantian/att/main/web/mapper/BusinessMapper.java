package com.nantian.att.main.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nantian.att.main.web.entity.Business;

@Mapper
public interface BusinessMapper {

	@Select("SELECT BUSINESS_ID as id,BUSINESS_NAME as name, BUSINESS_CREATETIME as createTime,BUSINESS_GROUPID as bussCollId FROM TOOL_BUSINESS where BUSINESS_GROUPID = #{busCollId}")
	public List<Business> selectBusinessesbByBusCollId(String busCollId);
		
	@Insert("INSERT INTO TOOL_BUSINESS VALUES(#{id},#{name},#{createTime},#{bussCollId})")
	public int insertBus(Business business);
	
	@Update("UPDATE TOOL_BUSINESS SET BUSINESS_NAME = #{name}, BUSINESS_CREATETIME = #{createTime},BUSINESS_GROUPID = #{bussCollId} WHERE BUSINESS_ID = #{id}")
	public int updateBus(Business business);
	
	@Delete("DELETE FROM TOOL_BUSINESS WHERE BUSINESS_ID = #{id}")
	public int deleteBusByBusId(String busId);
	
	@Select("SELECT BUSINESS_ID as id,BUSINESS_NAME as name, BUSINESS_CREATETIME as createTime,BUSINESS_GROUPID as bussCollId FROM TOOL_BUSINESS where BUSINESS_ID = #{busId}")
	public Business selectBusByBusId(String busId);
	
}
