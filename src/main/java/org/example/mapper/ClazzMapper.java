package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.Clazz;
import org.example.pojo.ClazzQueryParam;

import java.util.List;

@Mapper
public interface ClazzMapper {

    //@Select("select c.*,e.name master_name from clazz c,emp e where c.master_id=e.id order by update_time desc")
    public List<Clazz> pageSelect(ClazzQueryParam clazzQueryParam);

    @Insert("insert into clazz(name, room, begin_date, end_date, master_id, subject, create_time, update_time)" +
            "values (#{name},#{room},#{beginDate},#{endDate},#{masterId},#{subject},#{createTime},#{updateTime})")
    void insert(Clazz clazz);

    @Select("select * from clazz where id=#{id}")
    Clazz selectById(Integer id);

    @Update("update clazz set name=#{name},room=#{room},begin_date=#{beginDate},end_date=#{endDate},master_id=#{masterId}," +
            "subject=#{subject},update_time=#{updateTime} where id=#{id}")
    void update(Clazz clazz);

    @Delete("delete from clazz where id=#{id}")
    void deleteById(Integer id);

    @Select("select *from clazz")
    List<Clazz> selectAll();
}
