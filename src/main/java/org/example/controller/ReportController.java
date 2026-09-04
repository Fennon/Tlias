package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.ClazzData;
import org.example.pojo.JobOption;
import org.example.pojo.Result;
import org.example.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    ReportService reportService;

    @GetMapping("/empJobData")
    public Result getEmpJobData() {

        JobOption jobOption =reportService.getEmpJobData();
        return Result.success(jobOption);
    }

    @GetMapping("/empGenderData")
    public Result getEmpGenderData() {
        List<Map<String,Object>>genderList=reportService.getEmpGenderData();
        return Result.success(genderList);
    }

    @GetMapping("/studentCountData")
    public Result getStudentCountData() {
        ClazzData clazzData=reportService.getStudentCountData();
        return Result.success(clazzData);
    }

    @GetMapping("/studentDegreeData")
    public Result getStudentDegreeData() {
        List<Map<String,Object>>degreeList=reportService.getStudentDegreeData();
        return Result.success(degreeList);
    }
}
