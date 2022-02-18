package com.ruoyi.project.paper.dto;

import com.ruoyi.project.paper.domain.Pregnancy;
import lombok.Data;

/**
 * Created by Flareon on 2022-2-10.
 */
@Data
public class ResponsePregnancyDto extends Pregnancy
{
    /**试纸深度数值*/
    private String degreeLabel;

}
