package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.example.mapper.EmpExprMapper;
import org.example.mapper.EmpMapper;
import org.example.pojo.*;
import org.example.service.EmpLogService;
import org.example.service.EmpService;
import org.example.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
 public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpExprMapper empExprMapper;

    @Autowired
    EmpLogService empLogService;
//    @Override
//    public PageResult<Emp> page(Integer page, Integer pageSize) {
//
//        Long total = empMapper.count();
//
//        Integer start = (page - 1) * pageSize;
//        List<Emp>rows =empMapper.list(start,pageSize);
//
//        PageResult<Emp> pageResult = new PageResult<>();
//        pageResult.setTotal(total);
//        pageResult.setRows(rows);
//
//        return pageResult;
//
//    }

    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {

        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());
        List<Emp>empList=empMapper.list(empQueryParam);
        log.info("查询成功");

        Page<Emp>p=(Page<Emp>)empList;

        return new PageResult<Emp>(p.getTotal(),p.getResult());

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(Emp emp) {
        try {
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());

            empMapper.insert(emp);

            List<EmpExpr>exprList=emp.getExprList();
            if(!CollectionUtils.isEmpty(exprList)){
                exprList.forEach(expr->{
                    expr.setEmpId(emp.getId());
                });
                empExprMapper.insertBatch(exprList);
            }
        } finally {
            //记录操作日志
            EmpLog empLog=new EmpLog(null,LocalDateTime.now(),"新增员工："+emp);
            empLogService.insertLog(empLog);
        }



    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Integer[] ids) {
        empMapper.deltetByIds(ids);
        empExprMapper.deleteByEmpIds(ids);
    }

    @Override
    public Emp getEmpById(Integer empId) {
        Emp emp=empMapper.selectById(empId);
        emp.setExprList(empExprMapper.selectByEmpId(empId));

        log.info("查询结果：{}",emp);
        return emp;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void revise(Emp emp) {
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.update(emp);
        empExprMapper.deleteByEmpId(emp.getId());

        List<EmpExpr>exprList=emp.getExprList();
        if(!CollectionUtils.isEmpty(exprList)){
            exprList.forEach(expr->{
                expr.setEmpId(emp.getId());

            });

            empExprMapper.insertBatch(exprList);
        }

    }

    @Override
    public List<Emp>list() {
        return empMapper.selectAll();

    }

    @Override
    public LoginInfo login(Emp emp) {

        Emp e=empMapper.selectByUsernameAndPassword(emp);
        if(e!=null){
            log.info("登录成功，员工信息：{}",e);

            Map<String,Object>claims=new HashMap<>();
            claims.put("id",e.getId());
            claims.put("username",e.getUsername());
            String jwt= JwtUtils.generateJwt(claims);

            return new LoginInfo(e.getId(),e.getUsername(),e.getName(),jwt);
        }
        return null;
    }
}
