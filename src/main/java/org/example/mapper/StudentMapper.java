package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.pojo.Student;
import org.example.pojo.StudentQueryParam;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {
    @Select("select count(*)from student where clazz_id=#{clazzId} ")
    public Integer selectByClazzId(Integer clazzId);

    //@Select("select s.*,c.name clazz_name from student s,clazz c where s.clazz_id=c.id order by update_time desc ")
    List<Student> selectAll(StudentQueryParam studentQueryParam);

    void insertStudent(Student student);

    @Select("select * from student where id=#{id}")
    Student selectById(Integer id);

    void update(Student student);

    void deleteByIds(Integer[] ids);

    List<Map<String,Object>> getClazzStudentCount();

    List<Map<String, Object>> countStudentDegreeData();
}
