package com.ruoyi.project.paper.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.project.paper.domain.Pregnancy;
import lombok.Data;

import java.util.Date;

/**
 * Created by Flareon on 2022-2-10.
 */
@Data
public class ResponsePregnancyDto extends Pregnancy
{
    /**试纸深度数值*/
    private String degreeLabel;

    /**上次等值时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String lastEquivalenceTime;

    /**上次峰值*/
    private String lastPeakDegreeLabel;

    /**上次峰值时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastPeakTime;

    /**本次预估峰值时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expectPeakTime;
}
