package com.ruoyi.project.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.system.domain.SysFile;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Fyc on 2022-2-10.
 * 文件管理
 */
public interface ISysFileService extends IService<SysFile>
{
    AjaxResult saveFileToDisk(MultipartFile file);

    AjaxResult saveFileToDisk(MultipartFile file,String tmpFile);

    AjaxResult saveFileToDiskByPath(String data);

    SysFile saveFileToDisk(File file, String tmpFile);

    AjaxResult saveFileToDisk(byte[] bytes, String filePath, String realName, String originalName, String suffix, Long expire);

    BufferedImage getBufferedImage(Long id);

    String getRequestUrl(Long id);

    String getRequestUrl(SysFile sysFile);

    boolean move(SysFile sysFile, String directory);

    boolean move(Long sysFileId, String directory);

    String getName(String fileName);

    String getSuffix(String fileName);
}
