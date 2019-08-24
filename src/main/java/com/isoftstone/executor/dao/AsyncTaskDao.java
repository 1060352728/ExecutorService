package com.isoftstone.executor.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * @Auther: likui
 * @Date: 2019/8/6 23:34
 * @Description: service处理数据入库
 */
@Repository
@Transactional
public class AsyncTaskDao {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext //注入的是实体管理器,执行持久化操作
    private EntityManager entityManager;

    @Async
    public void doTask(String scanFile) throws Exception{
        logger.info("Task-Native"+ scanFile +" started.");
        entityManager.createNativeQuery("load data infile '"+ scanFile +"' replace into table user_info fields terminated by '###' enclosed by '\n'").executeUpdate();
    }
}
