package com.ruoyi.common.utils.io;


import cn.hutool.core.util.StrUtil;
import com.ruoyi.project.system.domain.SysFile;
import com.ruoyi.project.system.service.ISysFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;

@Component
public class FileOutputUtils
{
    @Autowired
    ISysFileService sysFileService;

    public void writer(Long fileId, HttpServletResponse response) throws Exception
    {
        SysFile sysFile = sysFileService.getById(fileId);
        if (sysFile == null)
        {
            return;
        }
        ServletOutputStream out = response.getOutputStream();
        File file = new File(sysFile.getFilePath());
        if (!file.exists())
        {
            return;
        }
        response.setHeader("content-disposition", "attachment;fileName=" + URLEncoder.encode(sysFile.getOriginalName(), "utf8"));
        int len = 0;
        FileInputStream in = new FileInputStream(file);
        byte[] buffer = new byte[1024 * 10];

        while ((len = in.read(buffer)) != -1)
        {
            out.write(buffer, 0, len);
        }
        in.close();
        out.flush();
        out.close();
    }

    public void writer(Long fileId, HttpServletResponse response, String fileName) throws Exception
    {
        SysFile sysFile = sysFileService.getById(fileId);
        fileName = fileName + sysFile.getSuffix();
        fileName = URLEncoder.encode(fileName, "UTF-8");

        ServletOutputStream out = response.getOutputStream();
        File file = new File(sysFile.getFilePath());
        if (!file.exists())
        {
            return;
        }
        response.setHeader("content-disposition", "attachment;fileName=" + fileName);
        int len = 0;
        FileInputStream in = new FileInputStream(file);
        byte[] buffer = new byte[1024 * 10];

        while ((len = in.read(buffer)) != -1)
        {
            out.write(buffer, 0, len);
        }
        in.close();
        out.flush();
        out.close();
    }

    /**
     * 下载文件
     *
     * @param fileId
     * @param fileName 设置的下载文件名，如果为空，则使用系统默认的
     * @param response
     * @throws Exception
     */
    public void writer(Long fileId, String fileName, HttpServletResponse response) throws Exception
    {
        SysFile sysFile = sysFileService.getById(fileId);

        ServletOutputStream out = response.getOutputStream();
        File file = new File(sysFile.getFilePath());
        if (!file.exists())
        {
            return;
        }

        if (StrUtil.isBlank(fileName))
        {
            fileName = sysFile.getFileRename();
        }
        fileName = URLDecoder.decode(fileName, "utf8") + sysFile.getSuffix();

        response.setHeader("content-disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "utf8"));
        response.setCharacterEncoding("utf8");

        int len = 0;
        FileInputStream in = new FileInputStream(file);
        byte[] buffer = new byte[1024 * 10];

        while ((len = in.read(buffer)) != -1)
        {
            out.write(buffer, 0, len);
        }
        in.close();
        out.flush();
        out.close();
    }

}
