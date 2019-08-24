package com.isoftstone.executor.service;

import com.isoftstone.executor.dao.AsyncTaskDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @Auther: likui
 * @Date: 2019/8/6 23:13
 * @Description:
 */
@Service
public class AsyncService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AsyncTaskDao asyncTaskDao;

    public void doTask(ArrayList<String> scanFiles) {
        logger.info("start----------------------"+System.currentTimeMillis());
        try {
            for (String scanFile : scanFiles) {
                asyncTaskDao.doTask(scanFile);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("end----------------------"+System.currentTimeMillis());
    }
}
