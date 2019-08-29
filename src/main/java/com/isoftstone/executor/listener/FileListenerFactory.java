package com.isoftstone.executor.listener;

import com.isoftstone.executor.service.AsyncService;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: likui
 * @Date: 2019/8/24 22:39
 * @Description: 监听工厂
 */
@Component
public class FileListenerFactory {

    // 设置轮询间隔
    private final long interval = TimeUnit.SECONDS.toMillis(1);

    private static FileAlterationMonitor fileAlterationMonitor = null;

    // 自动注入业务服务
    @Autowired
    private AsyncService asyncService;

    public FileAlterationMonitor initMonitor() throws Exception {
        fileAlterationMonitor = new FileAlterationMonitor(interval);
        return fileAlterationMonitor;
    }

    public void getMonitor(String monitorDir) {
        // 创建过滤器
        //IOFileFilter directories = FileFilterUtils.and(FileFilterUtils.directoryFileFilter(), HiddenFileFilter.VISIBLE);
        //IOFileFilter files = FileFilterUtils.and(FileFilterUtils.fileFileFilter(), FileFilterUtils.suffixFileFilter(".csv"));
        //IOFileFilter filter = FileFilterUtils.or(directories, files);
        // 装配过滤器
        //FileAlterationObserver observer = new FileAlterationObserver(new File(monitorDir), filter);
        monitorDir = "E:\\datafile\\" + monitorDir;
        // 不装配过滤器
        FileAlterationObserver observer = new FileAlterationObserver(new File(monitorDir));
        // 向监听者添加监听器，并注入业务服务
        observer.addListener(new FileListener(asyncService));
        // 返回监听者
        //fileAlterationMonitor = new FileAlterationMonitor(interval, observer);
        fileAlterationMonitor.addObserver(observer);
    }

    public FileAlterationMonitor closeMonitor() {
        return fileAlterationMonitor;
    }

}