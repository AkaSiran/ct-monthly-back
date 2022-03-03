package com.ruoyi.common.enums;

/**
 * Created by Fyc on 2022-3-3.
 * 删除标志
 */
public enum DeleteFlagEnum
{
    NORMAL("0","正常"),

    DELETE("1","删除")
    ;
    private final String code;

    private final String info;

    public String getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }

    DeleteFlagEnum(String code, String info)
    {
        this.code = code;
        this.info = info;
    }
}
