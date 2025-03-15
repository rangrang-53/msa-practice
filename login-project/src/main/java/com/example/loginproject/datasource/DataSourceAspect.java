package com.example.loginproject.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Component
public class DataSourceAspect {

    @Before("@annotation(transactional)")
    public void setDataSource(JoinPoint joinPoint,
                              Transactional transactional) {
        if (transactional.readOnly()) {
            DataSourceContextHolder.setDataSource("slave");
        } else {
            DataSourceContextHolder.setDataSource("master");
        }
    }

    @After("@annotation(transactional)")
    public void clearDataSource(JoinPoint joinPoint,
                                Transactional transactional) {
        DataSourceContextHolder.clearDataSource();
    }

}
