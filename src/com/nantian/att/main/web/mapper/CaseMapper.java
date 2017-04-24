package com.nantian.att.main.web.mapper;

import java.util.List;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nantian.att.main.web.entity.Case;

@Mapper
public interface CaseMapper {

	@Select("SELECT  CASE_ID as id,CASE_NAME as name,CASE_DESCRIPTION as descp,CASE_PROTOCOL as protocol,CASE_DATAFORMAT as dataFormat,CASE_RESULT as result,CASE_CREATETIME as createTime ,CASE_MODIFYTIME  as modifyTime,CASE_GROUPID  as caseCollId,CASE_COMM_ID as communicationId ,CASE_LOGURL as messageLogUrl ,CASE_REMARK as remark,CASE_ISTEST  as isTest FROM TOOL_CASE   where CASE_GROUPID =#{casecollId} ")
	public List<Case> selectCasesByCaseCollId(String casecollId);
	
	@Select("SELECT  CASE_ID as id,CASE_NAME as name,CASE_DESCRIPTION as descp,CASE_PROTOCOL as protocol,CASE_DATAFORMAT as dataFormat,CASE_RESULT as result,CASE_CREATETIME as createTime ,CASE_MODIFYTIME  as modifyTime,CASE_GROUPID  as caseCollId, CASE_TREE as treeData ,CASE_COMM_ID as communicationId ,CASE_LOGURL as messageLogUrl ,CASE_REMARK as remark,CASE_ISTEST  as isTest FROM  TOOL_CASE  where  CASE_ID =#{caseId} ")
	public Case selectCasesByCaseId(String caseId);
	
	@Update("UPDATE TOOL_CASE SET CASE_NAME  = #{name},CASE_DESCRIPTION  = #{descp},CASE_PROTOCOL  = #{protocol},CASE_DATAFORMAT  = #{dataFormat},CASE_RESULT = #{result},CASE_CREATETIME = #{createTime},CASE_MODIFYTIME = #{modifyTime},CASE_TREE = #{treeData} ,CASE_COMM_ID = #{communicationId},CASE_LOGURL = #{messageLogUrl} ,CASE_REMARK = #{remark},CASE_ISTEST = #{isTest} WHERE CASE_ID = #{id}")
	public int updateCaseByCaseId(Case c);
	
	@Update("UPDATE TOOL_CASE SET CASE_MODIFYTIME = #{modifyTime},CASE_TREE = #{treeData}  WHERE CASE_ID = #{id}")
	public int updateTreeOfCaseByCaseId(Case c);
	
	@Delete("DELETE FROM TOOL_CASE WHERE CASE_GROUPID  = #{caseCollId}")
	public int deleteCasesByCaseCollId(String caseCollId);
	
	@Insert("INSERT INTO TOOL_CASE VALUES( #{id},#{name},#{descp},#{protocol},#{dataFormat},#{result} ,#{createTime} ,#{modifyTime},#{caseCollId},#{treeData}, #{communicationId}, #{messageLogUrl},#{remark},#{isTest})")
	public int insertCase(Case c);
	
	@Delete("DELETE FROM TOOL_CASE WHERE CASE_ID = #{caseId}")
	public int deleteCaseByCaseId(String caseId);
	
	@Update("UPDATE TOOL_CASE SET CASE_RESULT = #{result}, CASE_MODIFYTIME = #{modifyTime} WHERE CASE_ID = #{id}")
	public int updateResultByCaseId(Case c);
	
	@Update("UPDATE TOOL_CASE SET CASE_LOGURL = #{messageLogUrl},CASE_MODIFYTIME = #{modifyTime}  WHERE CASE_ID = #{id}")
	public int updateMessageLogUrlByCaseId(Case c);
}
