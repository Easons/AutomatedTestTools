package com.nantian.att.main.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nantian.att.main.web.entity.Dictionary;

@Mapper
public interface DictionaryMapper {

	@Select("SELECT DIC_NAME  as id ,DIC_NAME  as value, DIC_PARENTID  as parentId  from TOOL_DICTIONARY  where DIC_PARENTID = (select DIC_ID from TOOL_DICTIONARY   where DIC_NAME  = #{parentName})")
	public List<Dictionary> selectItems(String parentName);
	
	@Select("SELECT DIC_ID as id ,DIC_NAME  as value, DIC_PARENTID  as parentId  from TOOL_DICTIONARY  where DIC_PARENTID IS NULL")
	public List<Dictionary> selectParentItems();
	
	@Select("SELECT DIC_ID  as id ,DIC_NAME  as value, DIC_PARENTID  as parentId  from TOOL_DICTIONARY  where DIC_PARENTID = #{parentId}")
	public List<Dictionary> selectItemsByPid(String parentId);
	
	@Update("UPDATE TOOL_DICTIONARY SET DIC_NAME  = #{value}, DIC_PARENTID  = #{parentId} WHERE DIC_ID = #{id}")
	public int updateDictionary(Dictionary dictionary);
	
	@Delete("DELETE FROM TOOL_DICTIONARY WHERE DIC_ID = #{id}")
	public int deleteDicById(String dicId);
	
	@Insert("INSERT INTO TOOL_DICTIONARY VALUES(#{id},#{value}, #{parentId})")
	public int insertDictionary(Dictionary dictionary);
	
	@Select("SELECT DIC_ID as id ,DIC_NAME  as value, DIC_PARENTID  as parentId  from TOOL_DICTIONARY  where DIC_PARENTID IS NULL AND DIC_NAME != '渠道信息'")
	public List<Dictionary> selectParentItems2Select();
}
