package com.ruoyi.project.paper.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * pregnancy对象 t_pregnancy
 * 
 * @author Flareon
 * @date 2022-02-10
 */
@Data
@TableName(value = "t_pregnancy")
public class Pregnancy
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 文件标识 */
    private Long fileId;

    /** 试纸深度 */
    private String degree;

    /**图片base64*/
    private String imgUrl;

    /** 操作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operateTime;

    /** 创建人 */
    private Long createId;

    /** 修改人 */
    private Long updateId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

}
