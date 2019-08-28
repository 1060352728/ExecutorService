package com.isoftstone.executor.listener;

import com.isoftstone.executor.service.AsyncService;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
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

    private FileAlterationMonitor fileAlterationMonitor = null;

    // 自动注入业务服务
    @Autowired
    private AsyncService asyncService;

    public FileAlterationMonitor getMonitor(String monitorDir) {
        monitorDir = "E:\\datafile\\" + monitorDir;
        if(null != fileAlterationMonitor){
            Iterable<FileAlterationObserver> files = fileAlterationMonitor.getObservers();
            for (FileAlterationObserver fileAlterationObserver : files) {
                File f = fileAlterationObserver.getDirectory();
                String path = f.getPath();
                if(monitorDir.equals(path)){
                    return fileAlterationMonitor;
                }
            }
        }
        // 创建过滤器
        //IOFileFilter directories = FileFilterUtils.and(FileFilterUtils.directoryFileFilter(), HiddenFileFilter.VISIBLE);
        //IOFileFilter files = FileFilterUtils.and(FileFilterUtils.fileFileFilter(), FileFilterUtils.suffixFileFilter(".*"));
        //IOFileFilter filter = FileFilterUtils.or(directories, files);
        // 装配过滤器
        //FileAlterationObserver observer = new FileAlterationObserver(new File(monitorDir), filter);
        // 不装配过滤器
        FileAlterationObserver observer = new FileAlterationObserver(new File(monitorDir));
        // 向监听者添加监听器，并注入业务服务
        observer.addListener(new FileListener(asyncService));
        // 返回监听者
        fileAlterationMonitor = new FileAlterationMonitor(interval, observer);
        return fileAlterationMonitor;
    }

}