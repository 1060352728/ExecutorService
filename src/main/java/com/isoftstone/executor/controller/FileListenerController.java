package com.isoftstone.executor.controller;

import com.isoftstone.executor.listener.FileListenerFactory;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: likui
 * @Date: 2019/8/24 22:49
 * @Description: 监听容器，创建监听者，开始监听
 */
@RestController
public class FileListenerController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FileListenerFactory fileListenerFactory;

    @PostMapping("/start")
    public void start(String taskId) throws Exception {
        FileAlterationMonitor fileAlterationMonitor = fileListenerFactory.getMonitor(taskId);
        try {
            fileAlterationMonitor.start();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
