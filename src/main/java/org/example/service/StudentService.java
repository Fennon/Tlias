package org.example.service;

import org.example.pojo.PageResult;
import org.example.pojo.Student;
import org.example.pojo.StudentQueryParam;

public interface StudentService {
    PageResult<Student> page(StudentQueryParam studentQueryParam);

    void save(Student student);

    Student getById(Integer id);

    void revise(Student student);

    void delete(Integer[] ids);

    void violation(Integer id, Integer score);
}
