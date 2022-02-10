package com.ruoyi.project.system.service.impl;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.enums.StorageModeEnum;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.FileUtils;
import com.ruoyi.common.utils.IdGen;
import com.ruoyi.common.utils.MapUtils;
import com.ruoyi.common.utils.MultipartFileUtils;
import com.ruoyi.common.utils.io.FileConfigurationProperties;
import com.ruoyi.common.utils.io.IOUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.system.domain.SysFile;
import com.ruoyi.project.system.mapper.SysFileMapper;
import com.ruoyi.project.system.service.ISysFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Fyc on 2022-2-10.
 * 文件管理
 */
@Slf4j
@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements ISysFileService
{

    @Autowired
    IOUtils ioUtils;

    @Autowired
    FileConfigurationProperties fileConfigurationProperties;

    @Override
    public AjaxResult saveFileToDisk(MultipartFile file)
    {
        MultipartFileUtils fileUtils = new MultipartFileUtils(file);
        String reName = fileUtils.getReName();
        String savePath = ioUtils.saveByDefaultPath(fileUtils.getFileBytes(), reName);
        if (StringUtils.isNotBlank(savePath))
        {
            SysFile sysFile = new SysFile();
            sysFile.setFilePath(savePath);
            sysFile.setFileRename(reName);
            sysFile.setStorageMode(1);
            sysFile.setOriginalName(fileUtils.getOriginalFileName());
            sysFile.setSuffix(fileUtils.getSuffix());
            save(sysFile);
            return getResult(sysFile);
        }
        return AjaxResult.error();
    }

    @Override
    public AjaxResult saveFileToDisk(MultipartFile file, String tmpFile)
    {
        String basePath = ioUtils.getFileBasePath();
        MultipartFileUtils fileUtils = new MultipartFileUtils(file);
        String reName = fileUtils.getReName();
        if (StringUtils.isNotEmpty(tmpFile))
        {
            basePath = basePath + tmpFile + File.separator;
        }
        String savePath = ioUtils.saveByPath(fileUtils.getFileBytes(), basePath, reName);
        if (StringUtils.isNotBlank(savePath))
        {
            SysFile sysFile = new SysFile();
            sysFile.setFilePath(savePath);
            sysFile.setFileRename(reName);
            sysFile.setStorageMode(1);
            sysFile.setOriginalName(fileUtils.getOriginalFileName());
            sysFile.setSuffix(fileUtils.getSuffix());
            save(sysFile);
            return getResult(sysFile);
        }
        return AjaxResult.error();
    }

    @Override
    public AjaxResult saveFileToDiskByPath(String data)
    {
        JSONObject jsonObject = JSONObject.parseObject(data);
        String path = jsonObject.getString("path");
        String documentId = jsonObject.getString("documentId");
        SysFile oldFile = getById(documentId);

        File file = new File(path);
        if (!file.exists())
        {
            throw new ServiceException("文件不存在");
        }
        String originalName = oldFile == null ? file.getName() : oldFile.getOriginalName();
        SysFile sysFile = new SysFile();
        sysFile.setFilePath(path);
        sysFile.setFileRename(file.getName());
        sysFile.setStorageMode(1);
        sysFile.setOriginalName(originalName);
        sysFile.setSuffix(getSuffix(file.getName()));
        sysFile.setExpire(0L);
        save(sysFile);
        return getResult(sysFile);
    }

    @Override
    public SysFile saveFileToDisk(File file, String tmpFile)
    {
        String basePath = ioUtils.getFileBasePath();
        String originalFileName = file.getName();
        String suffix = getSuffix(originalFileName);
        String reName = IdGen.get().netStringId() + suffix;
        byte[] bytes = FileUtils.File2byte(file);
        if (StringUtils.isNotEmpty(tmpFile))
        {
            basePath = basePath + tmpFile + File.separator;
        }
        String savePath = ioUtils.saveByPath(bytes, basePath, reName);
        if (StringUtils.isNotBlank(savePath))
        {
            SysFile sysFile = new SysFile();
            sysFile.setFilePath(savePath);
            sysFile.setFileRename(reName);
            sysFile.setStorageMode(1);
            sysFile.setOriginalName(originalFileName);
            sysFile.setSuffix(suffix);
            save(sysFile);
            return sysFile;
        }
        return null;
    }

    @Override
    public AjaxResult saveFileToDisk(byte[] bytes, String filePath, String realName, String originalName, String suffix, Long expire)
    {
        String savePath = ioUtils.saveByPath(bytes, filePath, realName);
        if (StringUtils.isNotBlank(savePath))
        {
            SysFile sysFileEntity = new SysFile();
            sysFileEntity.setFilePath(savePath);
            sysFileEntity.setFileRename(realName);
            sysFileEntity.setStorageMode(1);
            sysFileEntity.setOriginalName(originalName);
            sysFileEntity.setSuffix(suffix);
            sysFileEntity.setExpire(expire);
            save(sysFileEntity);
            return getResult(sysFileEntity);
        }
        return AjaxResult.error();
    }

    @Override
    public BufferedImage getBufferedImage(Long id)
    {
        SysFile sysFile = getById(id);
        try
        {
            File file = new File(sysFile.getFilePath());
            if (!file.exists())
            {
                return null;
            }
            return ImageIO.read(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getRequestUrl(Long id)
    {
        SysFile sysFile = getById(id);
        return getRequestUrl(sysFile);
    }

    @Override
    public String getRequestUrl(SysFile sysFile)
    {
        if (sysFile.getStorageMode().equals(StorageModeEnum.LOCAL_FILE.getCode()))
        {
            return fileConfigurationProperties.getLocalFileRequest() + sysFile.getId();
        }
        return null;
    }

    @Override
    public boolean move(SysFile sysFile, String directory)
    {
        File file = new File(sysFile.getFilePath());
        File moveTo = new File(ioUtils.getFileBasePath() + directory + File.separator + sysFile.getFileRename());
        FileUtil.copy(file, moveTo, true);
        if (!file.exists())
        {
            file.delete();
        }
        sysFile.setFilePath(moveTo.getPath());
        updateById(sysFile);
        return true;
    }

    @Override
    public boolean move(Long sysFileId, String directory)
    {
        return move(getById(sysFileId), directory);
    }

    @Override
    public String getName(String fileName)
    {
        if (!fileName.contains("."))
        {
            return fileName;
        }
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    @Override
    public String getSuffix(String fileName)
    {
        if (!fileName.contains("."))
        {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

    private AjaxResult getResult(Long id)
    {
        return getResult(getById(id));
    }

    private AjaxResult getResult(SysFile sysFile)
    {
        return AjaxResult.success().put(new MapUtils().put("id", sysFile.getId()).put("remoteUrl", getRequestUrl(sysFile)).put("originalName", sysFile.getOriginalName()).put("reName", sysFile.getFileRename()));
    }


}
