package com.ruoyi.common.enums;

/*
 * 文件存储方式
 */
public enum StorageModeEnum
{

    LOCAL_FILE(1,"本地文件存储"),
    REMOTE_FILE(2,"远程文件存储")
    ;
    private Integer code;
    private String name;

    private StorageModeEnum(Integer code, String name)
    {
        this.code = code;
        this.name = name;
    }

    public Integer getCode()
    {
        return this.code;
    }

    public String getName()
    {
        return this.name;
    }
}
