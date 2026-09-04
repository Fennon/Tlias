package org.example.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.EmpExpr;

import java.util.List;

@Mapper
public interface EmpExprMapper {

    void insertBatch(List<EmpExpr> exprList);

    void deleteByEmpIds(Integer[] ids);

    @Select("select * from emp_expr where emp_id=#{empId}")
    List<EmpExpr> selectByEmpId(Integer empId);


    @Delete("delete from emp_expr where emp_id=#{id}")
    void deleteByEmpId(Integer id);
}
