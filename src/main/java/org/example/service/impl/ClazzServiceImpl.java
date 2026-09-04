package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.exception.BusinessException;
import org.example.mapper.ClazzMapper;
import org.example.mapper.StudentMapper;
import org.example.pojo.Clazz;
import org.example.pojo.ClazzQueryParam;
import org.example.pojo.PageResult;
import org.example.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzMapper clazzMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public PageResult<Clazz> page(ClazzQueryParam clazzQueryParam) {

        PageHelper.startPage(clazzQueryParam.getPage(),clazzQueryParam.getPageSize());
        List<Clazz>clazzList=clazzMapper.pageSelect(clazzQueryParam);
        if(!clazzList.isEmpty()){
            for(Clazz clazz:clazzList){
                LocalDate now = LocalDate.now();
                LocalDate begin=clazz.getBeginDate();
                LocalDate end=clazz.getEndDate();

                if(now.isAfter(end)){
                    clazz.setStatus("已结课");
                }
                else if(now.isBefore(begin)){
                    clazz.setStatus("未开班");
                }
                else {
                    clazz.setStatus("在读中");
                }

            }
        }

        Page<Clazz>p=(Page<Clazz>)clazzList;

        return new PageResult<Clazz>(p.getTotal(),p.getResult());

    }

    @Override
    public void add(Clazz clazz) {
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());

        clazzMapper.insert(clazz);

    }

    @Override
    public Clazz getByID(Integer id) {
        return clazzMapper.selectById(id);
    }

    @Override
    public void revise(Clazz clazz) {
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.update(clazz);
    }

    @Override
    public void deleteById(Integer id) {
        if(studentMapper.selectByClazzId(id)>0){
            throw new BusinessException("对不起, 该班级下有学生, 不能直接删除");
        }
            clazzMapper.deleteById(id);
    }

    @Override
    public List<Clazz> list() {
        return clazzMapper.selectAll();
    }
}
