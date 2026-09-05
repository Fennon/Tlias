package org.example.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.anno.Log;
import org.example.mapper.OperateLogMapper;
import org.example.pojo.OperateLog;
import org.example.utils.CurrentHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class OperateLogAspect {

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Around("@annotation(org.example.anno.Log)")
    public Object recordOperateLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // 记录方法开始时间
        long start = System.currentTimeMillis();
        Object result = null;
        try {
            // 执行目标Controller方法
            result = joinPoint.proceed();
        } finally {
            // 无论正常执行还是抛出异常，都记录日志
            long costTime = System.currentTimeMillis() - start;

            OperateLog operateLog = new OperateLog();
            // 操作时间
            operateLog.setOperateTime(LocalDateTime.now());

            // 目标类全类名
            Class<?> targetCls = joinPoint.getTarget().getClass();
            operateLog.setClassName(targetCls.getName());

            // 方法名
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            operateLog.setMethodName(signature.getMethod().getName());

            operateLog.setMethodParams(Arrays.toString(joinPoint.getArgs()));
            operateLog.setReturnValue(result != null ? result.toString() : "void");


            // 执行耗时
            operateLog.setCostTime(costTime);

            // ----------------操作人ID----------------
            operateLog.setOperateEmpId(CurrentHolder.getCurrentId());
            

            // 保存日志
            operateLogMapper.insert(operateLog);
        }
        return result;
    }
}