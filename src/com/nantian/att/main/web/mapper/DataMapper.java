package com.nantian.att.main.web.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.nantian.att.main.web.entity.Data;

@Mapper
public interface DataMapper {

	@Select("SELECT DATA_ID as id,DATA_NAME as name,DATA_VALUE as value,DATA_SIGN as sign,DATA_PARENTID as parentId,DATA_CASEID as caseId from TOOL_DATA  where DATA_CASEID  = #{caseId}")
	public List<Data> selectDatasByCaseId(String caseId);
	
}
