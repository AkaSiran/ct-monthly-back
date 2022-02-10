package com.ruoyi.project.paper.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.paper.domain.Pregnancy;
import com.ruoyi.project.paper.dto.RequestPregnancyDto;
import com.ruoyi.project.paper.dto.ResponsePregnancyDto;
import com.ruoyi.project.paper.mapper.PregnancyMapper;
import com.ruoyi.project.paper.service.PregnancyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Flareon on 2022-2-10.
 */
@Slf4j
@Service
public class PregnancyServiceImpl extends ServiceImpl<PregnancyMapper,Pregnancy> implements PregnancyService
{
    @Override
    public ResponsePregnancyDto selectPregnancyById(Long id)
    {
        return null;
    }

    @Override
    public List<ResponsePregnancyDto> selectPregnancyList(RequestPregnancyDto requestPregnancyDto)
    {
        return null;
    }

    @Override
    public AjaxResult insertPregnancy(RequestPregnancyDto requestPregnancyDto)
    {
        return null;
    }

    @Override
    public AjaxResult updatePregnancy(RequestPregnancyDto requestPregnancyDto)
    {
        return null;
    }

    @Override
    public AjaxResult deletePregnancyByIds(Long[] ids)
    {
        return null;
    }

    @Override
    public AjaxResult deletePregnancyById(Long id)
    {
        return null;
    }
}
