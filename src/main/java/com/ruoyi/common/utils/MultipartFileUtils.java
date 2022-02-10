package com.ruoyi.common.utils;

import com.ruoyi.common.exception.ServiceException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

public class MultipartFileUtils
{

    private Double maxSize = 20d;
    private MultipartFile multipartFile;

    public MultipartFileUtils(MultipartFile multipartFile)
    {
        this.multipartFile = multipartFile;
        Float size = getFileSizeMB();
        if (size > 20f)
        {
            throw new ServiceException("单个文件最大允许上传：" + maxSize + "MB");
        }
    }

    public void setMaxSize(Double maxSize)
    {
        this.maxSize = maxSize;
    }

    public String getOriginalFileName()
    {
        return multipartFile.getOriginalFilename();
    }

    public String getReName()
    {
        String reName = IdGen.get().netStringId();
        String suffix = getSuffix();
        if (StringUtils.isNotBlank(suffix))
        {
            return reName + suffix;
        }
        return reName;
    }

    public Long getFileSize()
    {
        return multipartFile.getSize();
    }

    public Float getFileSizeMB()
    {
        Float size = Float.parseFloat(String.valueOf(multipartFile.getSize())) / 1024 / 1024;
        BigDecimal b = new BigDecimal(size);
        // 2表示2位 ROUND_HALF_UP表明四舍五入，
        size = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        System.out.println("文件大小:" + size);
        return size;
    }

    public String getSuffix()
    {
        String originalFileName = getOriginalFileName();
        if (!originalFileName.contains("."))
        {
            return "";
        }
        return originalFileName.substring(originalFileName.lastIndexOf("."));
    }

    public byte[] getFileBytes()
    {
        try
        {
            return multipartFile.getBytes();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public InputStream getInputStream()
    {
        try
        {
            return multipartFile.getInputStream();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
