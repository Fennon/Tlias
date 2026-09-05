package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Clazz;
import org.example.pojo.ClazzQueryParam;
import org.example.pojo.PageResult;
import org.example.pojo.Result;
import org.example.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/clazzs")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    @GetMapping
    public Result page(ClazzQueryParam clazzQueryParam) {
        PageResult<Clazz>pageResult=clazzService.page(clazzQueryParam);
        return Result.success(pageResult);
    }

    @PostMapping
    public Result add(@RequestBody Clazz clazz) {
        clazzService.add(clazz);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getByID(@PathVariable Integer id) {

        return Result.success(clazzService.getByID(id));
    }

    @PutMapping
    public Result revise(@RequestBody Clazz clazz) {

        clazzService.revise(clazz);

        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {

        clazzService.deleteById(id);
        return  Result.success();
    }

    @GetMapping("/list")
    public Result list() {

        return Result.success(clazzService.list());
    }

}
