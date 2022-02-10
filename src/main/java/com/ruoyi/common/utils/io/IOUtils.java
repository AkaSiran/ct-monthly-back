package com.ruoyi.common.utils.io;

import cn.hutool.core.io.IoUtil;
import com.ruoyi.common.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
@Slf4j
public class IOUtils
{
    @Autowired
    FileConfigurationProperties fileConfigurationProperties;

    /**
     * 获取文件基础存储路径
     *
     * @return
     */
    public String getFileBasePath()
    {
        String basePath;
        if (fileConfigurationProperties != null && StringUtils.isNotBlank(fileConfigurationProperties.getSaveUrl()))
        {
            basePath = fileConfigurationProperties.getSaveUrl();
        }
        else
        {
            basePath = System.getProperty("user.dir");
            basePath = basePath + File.separator + "_tmpFile" + File.separator;
        }
        basePath = pathSeparator(basePath);
        return basePath;
    }

    public String pathSeparator(String basePath)
    {
        int endSymbol1 = basePath.lastIndexOf("/");
        int endSymbol2 = basePath.lastIndexOf("\\");
        int basePathLength = basePath.length();
        if (basePathLength - 1 != endSymbol1 && basePathLength - 1 != endSymbol2)
        {
            basePath = basePath + File.separator;
        }
        return basePath;
    }

    public String saveByPath(byte[] bytes, String basePath, String fileName)
    {

        String fullPath = pathSeparator(basePath) + fileName;
        try
        {
            File file = new File(basePath);
            if (!file.exists())
            {
                file.mkdirs();
            }
            log.info("save file = {}", fullPath);
            FileOutputStream outputStream = new FileOutputStream(fullPath);
            IoUtil.write(outputStream, true, bytes);
            return fullPath;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public String saveByPath(InputStream inputStream, String basePath, String fileName)
    {
        try
        {
            return saveByPath(FileUtils.toByteArray(inputStream), basePath, fileName);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public String saveByDefaultPath(InputStream inputStream, String fileName)
    {
        try
        {
            return saveByPath(FileUtils.toByteArray(inputStream), getFileBasePath(), fileName);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public String saveByDefaultPath(byte[] bytes, String fileName)
    {
        return saveByPath(bytes, getFileBasePath(), fileName);
    }
}
