package com.isoftstone.executor.component;

import com.isoftstone.executor.listener.FileListenerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Auther: likui
 * @Date: 2019/8/29 21:59
 * @Description: 初始化监控容器
 */
@Component
@Order(2)
public class InitMonitorComp implements CommandLineRunner {

    @Autowired
    private FileListenerFactory fileListenerFactory;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("初始化Monitor");
        fileListenerFactory.initMonitor().start();
    }
}
