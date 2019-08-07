package com.isoftstone.executor;

import com.isoftstone.executor.config.TaskThreadPoolConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties({TaskThreadPoolConfig.class} ) // 开启配置属性支持
public class ExecutorServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExecutorServiceApplication.class, args);
    }

}
