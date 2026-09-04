package org.example.controller;

import org.example.pojo.Dept;
import org.example.pojo.Result;
import org.example.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/depts")
@RestController
public class DeptController {

    @Autowired
    public DeptService deptService;

    //@RequestMapping(value = "/depts",method = RequestMethod.GET)
    @GetMapping
    public Result list() {

        List<Dept>deptList=deptService.findAll();
        return Result.success(deptList);

    }

    @DeleteMapping
    public Result deleteById(Integer id) {

        deptService.deleteById(id);
        return Result.success();

    }

    @PostMapping
    public Result insert(@RequestBody Dept dept) {

        deptService.insert(dept);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        Dept dept=deptService.getById(id);
        return Result.success(dept);
    }

    @PutMapping
    public Result update(@RequestBody Dept dept) {

        deptService.update(dept);
        return Result.success();
    }

}
