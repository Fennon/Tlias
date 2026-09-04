package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.anno.Log;
import org.example.pojo.Emp;
import org.example.pojo.EmpQueryParam;
import org.example.pojo.PageResult;
import org.example.pojo.Result;
import org.example.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Slf4j
@RequestMapping("/emps")
@RestController
public class EmpController {
    @Autowired
    private EmpService empService;

    @GetMapping("/{id}")
    public Result getEmpById(@PathVariable ("id") Integer empId) {

        Emp emp=empService.getEmpById(empId);
        return Result.success(emp);

    }


    @GetMapping
    public Result page(EmpQueryParam empQueryParam) {
        PageResult<Emp> pageResult=empService.page(empQueryParam);
        return Result.success(pageResult);

    }

    @Log
    @PostMapping
    public Result save(@RequestBody Emp emp) {
        log.info("请求参数：{}", emp);
        empService.save(emp);
        return Result.success();

    }

    @Log
    @DeleteMapping
    public Result delete(Integer [] ids) {

        log.info("删除员工：{}", Arrays.toString(ids));
        empService.delete(ids);
        return Result.success();
    }

    @Log
    @PutMapping
    public Result revise(@RequestBody Emp emp) {
        log.info("修改员工id:{}", emp.getId());
        empService.revise(emp);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list() {
        return Result.success(empService.list());
    }


}
