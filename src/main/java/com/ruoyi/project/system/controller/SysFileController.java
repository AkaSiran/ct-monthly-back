package com.ruoyi.project.system.controller;

import cn.hutool.core.codec.Base64;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.io.FileOutputUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.system.service.ISysFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * Created by Fyc on 2022-2-10.
 * 文件管理
 */
@RestController
@RequestMapping("/system/file")
public class SysFileController
{
    @Autowired
    FileOutputUtils fileOutputUtils;

    @Autowired
    ISysFileService sysFileService;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/fileUpload/disk")
    public AjaxResult fileUpload(@RequestParam("file") MultipartFile file)
    {
        if (file.isEmpty())
        {
            throw new ServiceException("上传文件不能为空");
        }
        return sysFileService.saveFileToDisk(file);
    }

    /**
     * 文件上传
     * @param path
     * @return
     */
    @PostMapping("/fileUpload/path")
    public AjaxResult fileUpload(@RequestBody String path)
    {
        String decodePath = Base64.decodeStr(path, StandardCharsets.UTF_8);
        return sysFileService.saveFileToDiskByPath(decodePath);
    }

    /**
     * 图片预览
     * @param id
     * @param response
     * @throws Exception
     */
    @GetMapping("/online/{id}")
    public void onlineFile(@PathVariable("id") Long id, HttpServletResponse response) throws Exception
    {
        if (id != null && id != 0)
        {
            fileOutputUtils.writer(id, response);
        }
    }

    /**
     * 下载文件
     * @param id
     * @param fileName
     * @param response
     * @throws Exception
     */
    @GetMapping("/online/{id}/{fileName}")
    public void downloadFile(@PathVariable("id") Long id, @PathVariable String fileName, HttpServletResponse response) throws Exception
    {
        fileOutputUtils.writer(id, fileName, response);
    }
}
