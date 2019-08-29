package com.isoftstone.executor.service;

import com.isoftstone.executor.dao.AsyncTaskDao;
import com.isoftstone.executor.listener.FileListenerFactory;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
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

    @Autowired
    private FileListenerFactory fileListenerFactory;

    public void doTask(ArrayList<String> scanFiles) {
        try {
            for (String scanFile : scanFiles) {
                File file = new File(scanFile);
                if(file.getName().endsWith(".csv")) {
                    asyncTaskDao.doTask(scanFile);
                } else {
                    boolean flag = file.delete();
                    if(flag){
                        logger.info("删除文件" + file.getName() + "成功");
                    } else {
                        logger.error("删除文件" + file.getName() + "失败", new Throwable("文件" + file.getName() + "不存在"));
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void doClose(String taskId) throws Exception {
        try{
            FileAlterationMonitor fileAlterationMonitor = fileListenerFactory.closeMonitor();
            Iterable<FileAlterationObserver> files = fileAlterationMonitor.getObservers();
            for (FileAlterationObserver fileAlterationObserver : files) {
                String path = fileAlterationObserver.getDirectory().getAbsolutePath();
                if(StringUtils.isNoneBlank(path) && path.contains("\\") && (path.lastIndexOf("\\") + 1) < path.length()){
                    if(taskId.equals(path.substring(path.lastIndexOf("\\") + 1))) {
                        fileAlterationMonitor.removeObserver(fileAlterationObserver);
                    }
                }
            }
            logger.info("删除对" + taskId + "文件夹的监控成功");
        } catch (Exception e) {
            logger.error("删除对" + taskId + "文件夹的监控失败", e);
        }
    }
}
