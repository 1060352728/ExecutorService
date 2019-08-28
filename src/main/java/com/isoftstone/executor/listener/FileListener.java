package com.isoftstone.executor.listener;

import com.isoftstone.executor.service.AsyncService;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * @Auther: likui
 * @Date: 2019/8/24 21:36
 * @Description: 监听文件夹的工具
 */
public class FileListener extends FileAlterationListenerAdaptor {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private AsyncService asyncService;

    public FileListener(AsyncService asyncService) {
        this.asyncService = asyncService;
    }
    /**
     * 文件创建执行
     */
    public void onFileCreate(File file) {
        logger.info("[新建]:" + file.getAbsolutePath());
        try {
            ArrayList<String> scanFiles = scanFilesWithRecursion(file.getParent());
            if(null != scanFiles){
                asyncService.doTask(scanFiles);
            }
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
    }
    /**
     * 文件创建修改
     */
    public void onFileChange(File file) {
        logger.info("[修改]:" + file.getAbsolutePath());
    }
    /**
     * 文件删除
     */
    public void onFileDelete(File file) {
        logger.info("[删除]:" + file.getAbsolutePath());
        try {
            if(file.getName().endsWith(".end")){
                String path = file.getParent();
                if(StringUtils.isNoneBlank(path) && path.contains("\\") && (path.lastIndexOf("\\") + 1) < path.length()){
                    asyncService.doClose(file.getParent().substring(file.getParent().lastIndexOf("\\") + 1));
                }
            }
        } catch (Exception e) {
            logger.error("删除对" + file.getParent().lastIndexOf("\\") + "文件夹的监控失败", e);
        }
    }
    /**
     * 目录创建
     */
    public void onDirectoryCreate(File directory) {
        logger.info("[新建]:" + directory.getAbsolutePath());
    }
    /**
     * 目录修改
     */
    public void onDirectoryChange(File directory) {
        logger.info("[修改]:" + directory.getAbsolutePath());
    }
    /**
     * 目录删除
     */
    public void onDirectoryDelete(File directory) {
        logger.info("[删除]:" + directory.getAbsolutePath());
    }

    /**
     * 递归扫描指定文件夹下面的指定文件
     */
    public static ArrayList<String> scanFilesWithRecursion(String folderPath) throws FileNotFoundException {
        boolean flag = false;
        ArrayList<String> scanFiles = new ArrayList<>();
        File directory = new File(folderPath);
        if(!directory.isDirectory()){
            throw new FileNotFoundException("folderPath:" + folderPath + "inexistence");
        }
        if(directory.isDirectory()){
            File [] fileList = directory.listFiles();
            if(null != fileList) {
                for (File aFileList : fileList) {
                    if(aFileList.getName().endsWith(".end")){
                        flag = true;
                    }
                    /*如果当前是文件夹，进入递归扫描文件夹**/
                    if (aFileList.isDirectory()) {
                        /*递归扫描下面的文件夹*/
                        scanFilesWithRecursion(aFileList.getAbsolutePath());
                    } else {
                        /*非文件夹*/
                        String filePath = aFileList.getAbsolutePath().replaceAll("\\\\", "//");
                        scanFiles.add(filePath);
                    }
                }
            }
        }
        if(flag){
            return scanFiles;
        }
        return null;
    }
}
