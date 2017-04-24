package com.nantian.att.main.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nantian.att.main.web.entity.BusinessCollection;

@Mapper
public interface BusinessCollectionMapper {

	@Insert("INSERT INTO TOOL_BUSINESSCOLL(BUSINESSCOLL_ID,BUSINESSCOLL_NAME,BUSSINESS_ICON ) VALUES(#{id},#{name},#{icon})")
	public int insertBusinessColl(BusinessCollection bc);
	
	@Select("SELECT BUSINESSCOLL_ID as id, BUSINESSCOLL_NAME as name,BUSSINESS_ICON as icon FROM tool_businessColl")
	public List<BusinessCollection> selectBusinessColl();
	
	@Delete("DELETE FROM TOOL_BUSINESSCOLL WHERE BUSINESSCOLL_ID = #{id}")
	public int deleteBusCollById(String busCollId);
	
	@Update("UPDATE TOOL_BUSINESSCOLL SET  BUSINESSCOLL_NAME = #{name} WHERE BUSINESSCOLL_ID = #{id}")
	public int updateBusCollId(BusinessCollection bc);
	
	@Select("SELECT  BUSINESSCOLL_ID as id, BUSINESSCOLL_NAME as name FROM tool_businessColl")
	public List<BusinessCollection> selectAllBusColls();
}
