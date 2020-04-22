package com.zw.admin.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;

import com.zw.admin.server.model.News;

@Mapper
public interface NewsDao {

    @Select("select * from news t where t.id = #{id}")
    News getById(Long id);

    @Delete("delete from news where id = #{id}")
    int delete(Long id);

    int update(News news);
    
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into news(title, content, create_time) values(#{title}, #{content}, #{createTime})")
    int save(News news);
    
    int count(@Param("params") Map<String, Object> params);

    List<News> list(@Param("params") Map<String, Object> params, @Param("offset") Integer offset, @Param("limit") Integer limit);
}
