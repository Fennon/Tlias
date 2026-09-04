package org.example.service.impl;

import org.example.mapper.EmpMapper;
import org.example.mapper.StudentMapper;
import org.example.pojo.ClazzData;
import org.example.pojo.JobOption;
import org.example.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    EmpMapper empMapper;
    @Autowired
    StudentMapper studentMapper;

    @Override
    public JobOption getEmpJobData() {
        List<Map<String, Object>> list = empMapper.countEmpJobData();
        List<Object>jobList=new ArrayList<>();
        List<Object>dataList=new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            jobList.add(map.get("pos"));
            dataList.add(map.get("num"));
        }

        return new JobOption(jobList,dataList);
    }

    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        return empMapper.countEmpGenderData();
    }

    @Override
    public ClazzData getStudentCountData() {

        List<Map<String,Object>>list=studentMapper.getClazzStudentCount();
        List<Object>clazzList=new ArrayList<>();
        List<Object>dataList=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            clazzList.add(map.get("clazz"));
            dataList.add(map.get("num"));
        }
        return new ClazzData(clazzList,dataList);
    }

    @Override
    public List<Map<String, Object>> getStudentDegreeData() {
        return studentMapper.countStudentDegreeData();
    }
}
