package com.ruoyi.project.paper.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.paper.domain.Pregnancy;
import com.ruoyi.project.paper.dto.RequestPregnancyDto;
import com.ruoyi.project.paper.dto.ResponsePregnancyDto;

import java.util.List;

/**
 * Created by Flareon on 2022-2-10.
 */
public interface PregnancyService extends IService<Pregnancy>
{
    /**
     * 查询pregnancy
     *
     * @param id pregnancy主键
     * @return pregnancy
     */
    ResponsePregnancyDto selectPregnancyById(Long id);

    /**
     * 查询pregnancy列表
     *
     * @param requestPregnancyDto
     * @return pregnancy集合
     */
    List<ResponsePregnancyDto> selectPregnancyList(RequestPregnancyDto requestPregnancyDto);

    /**
     * 新增pregnancy
     *
     * @param requestPregnancyDto
     * @return 结果
     */
    AjaxResult insertPregnancy(RequestPregnancyDto requestPregnancyDto);

    /**
     * 修改pregnancy
     *
     * @param requestPregnancyDto
     * @return 结果
     */
    AjaxResult updatePregnancy(RequestPregnancyDto requestPregnancyDto);

    /**
     * 批量删除pregnancy
     *
     * @param ids 需要删除的pregnancy主键集合
     * @return 结果
     */
    AjaxResult deletePregnancyByIds(Long[] ids);

    /**
     * 删除pregnancy信息
     *
     * @param id pregnancy主键
     * @return 结果
     */
    AjaxResult deletePregnancyById(Long id);
}
