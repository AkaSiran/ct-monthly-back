package com.ruoyi.project.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Fyc on 2022-2-10.
 * 文件管理
 */
@Data
@TableName("sys_file")
public class SysFile implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 上传后的文件名
     */
    private String fileRename;

    /**
     * 文件原始文件名
     */
    private String originalName;

    /**
     * 文件存储路径
     */
    private String filePath;

    /**
     * 1本地存储，2远程存储
     */
    private Integer storageMode;

    /**
     * 远程访问url
     */
    private String remoteUrl;

    /**
     * 文件格式
     */
    private String suffix;

    /**
     * 到期时间戳
     * 如果为空，则永远不过期， 到期后，会自动删除该数据用于临时文件
     */
    private Long expire;
}
