package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.Emp;
import org.example.pojo.EmpQueryParam;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {

//原始分页查询：
//    @Select("select count(*)  from  emp e left join dept d on e.dept_id=d.id ")
//    public Long count();
//
//    @Select("select e.*,d.name deptName from  emp e left join dept d on e.dept_id=d.id order by e.update_time desc limit #{start},#{pageSize}")
//    public List<Emp>list(Integer start, Integer pageSize);

    //基于PageHelper分页查询：

    //@Select("select e.*,d.name deptName from  emp e left join dept d on e.dept_id=d.id order by e.update_time desc")
    public List<Emp>list(EmpQueryParam empQueryParam);

    @Options(useGeneratedKeys = true ,keyProperty ="id" )
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time)" +
            "values(#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime}) ")
    void insert(Emp emp);

    void deltetByIds(Integer[] ids);

    @Select("select * from emp where id=#{empId}")
    Emp selectById(Integer empId);


    void update(Emp emp);

    List<Map<String,Object>>countEmpJobData();

    List<Map<String,Object>>countEmpGenderData();

    @Select("select *from emp")
    List<Emp> selectAll();

    @Select("select id,username,name from emp where username=#{username} and password=#{password}")
    Emp selectByUsernameAndPassword(Emp emp);
}
