package com.isoftstone.executor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * @Auther: likui
 * @Date: 2019/8/6 23:34
 * @Description:
 */
@Service
@Transactional
public class AsyncTaskService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext //注入的是实体管理器,执行持久化操作
    private EntityManager entityManager;

    @Async
    public void doTask(int i) throws Exception{
        logger.info("Task-Native"+i+" started.");
        entityManager.createNativeQuery("load data infile 'E:\\data.csv' replace into table user_info fields terminated by '###' enclosed by '\n'").executeUpdate();
    }

}
