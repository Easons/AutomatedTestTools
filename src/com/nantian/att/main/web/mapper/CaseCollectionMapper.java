package com.nantian.att.main.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nantian.att.main.web.entity.CaseCollection;

@Mapper
public interface CaseCollectionMapper {

	@Select("SELECT CASECOLL_ID  as id, CASECOLL_NAME as name, CASECOLL_BUSSINESSID as busId FROM TOOL_CASECOLL where CASECOLL_BUSSINESSID =#{bussinssId}")
	public List<CaseCollection> selectCaseGroupsByBusId(String bussinssId);
	
	@Insert("INSERT INTO TOOL_CASECOLL VALUES(#{id},#{name},#{busId})")
	public int insertCaseGroup(CaseCollection cc);
	
	@Update("UPDATE TOOL_CASECOLL SET  CASECOLL_NAME = #{name}, CASECOLL_BUSSINESSID = #{busId} WHERE CASECOLL_ID  = #{id}")
	public int updateCaseGroup(CaseCollection cc);
	
	@Delete("DELETE FROM TOOL_CASECOLL WHERE CASECOLL_ID  = #{id}")
	public int deleteCaseGroup(String caseCollId);
	
	@Delete("DELETE FROM TOOL_CASECOLL WHERE CASECOLL_BUSSINESSID = #{busId}")
	public int deletecaseCollByBusId(String busId);
}
