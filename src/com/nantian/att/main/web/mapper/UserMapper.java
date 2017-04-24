package com.nantian.att.main.web.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
	@Select("SELECT * FROM T_USER WHERE NAME = #{name}")
	public List<Map<String,Object>> findByName(@Param("name") String name);

	@Insert("INSERT INTO T_USER(NAME, AGE) VALUES(#{name,jdbcType=VARCHAR}, #{age})")
	public int insert(@Param("name") String name, @Param("age") String age);
	
	@Update("UPDATE user SET age=#{age} WHERE name=#{name}")
    void update(Map<String, Object> parasMap);
	
	@Delete("DELETE FROM user WHERE id =#{name}")
    void delete(@Param("name") String name);
}
