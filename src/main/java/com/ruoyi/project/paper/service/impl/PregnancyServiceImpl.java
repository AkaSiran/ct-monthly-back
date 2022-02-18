package com.ruoyi.project.paper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.paper.domain.Pregnancy;
import com.ruoyi.project.paper.dto.RequestPregnancyDto;
import com.ruoyi.project.paper.dto.ResponsePregnancyDto;
import com.ruoyi.project.paper.mapper.PregnancyMapper;
import com.ruoyi.project.paper.service.PregnancyService;
import com.ruoyi.project.system.service.ISysDictDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Flareon on 2022-2-10.
 */
@Slf4j
@Service
public class PregnancyServiceImpl extends ServiceImpl<PregnancyMapper,Pregnancy> implements PregnancyService
{

    @Autowired
    private ISysDictDataService sysDictDataService;

    @Override
    public ResponsePregnancyDto selectPregnancyById(Long id)
    {
        Pregnancy pregnancy = getById(id);
        ResponsePregnancyDto responsePregnancyDto = new ResponsePregnancyDto();
        if(null!=pregnancy && null!=pregnancy.getId())
        {
            BeanUtils.copyProperties(pregnancy,responsePregnancyDto);
            //试纸深度字典转换
            String degree = pregnancy.getDegree();
            if(StringUtils.isNotBlank(degree))
            {
                String degreeLabel = sysDictDataService.selectDictLabel("t_pregnancy_degree",degree);
                responsePregnancyDto.setDegreeLabel(degreeLabel);
            }
        }
        return responsePregnancyDto;
    }

    @Override
    public List<ResponsePregnancyDto> selectPregnancyList(RequestPregnancyDto requestPregnancyDto)
    {
        String degreeParams = requestPregnancyDto.getDegree();
        List<Pregnancy> list = list(new LambdaQueryWrapper<Pregnancy>()
                .eq(StringUtils.isNotBlank(degreeParams),Pregnancy::getDegree,degreeParams)
                .orderByDesc(Pregnancy::getOperateTime));
        List<ResponsePregnancyDto> resultList = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(list))
        {
            list.forEach(item ->
            {
                ResponsePregnancyDto resultItem = new ResponsePregnancyDto();
                BeanUtils.copyProperties(item,resultItem);
                resultList.add(resultItem);
            });
        }
        return resultList;
    }

    @Override
    public AjaxResult insertPregnancy(RequestPregnancyDto requestPregnancyDto)
    {
        Pregnancy pregnancy = new Pregnancy();
        BeanUtils.copyProperties(requestPregnancyDto,pregnancy);
        save(pregnancy);
        return AjaxResult.success();
    }

    @Override
    public AjaxResult updatePregnancy(RequestPregnancyDto requestPregnancyDto)
    {
        Pregnancy pregnancy = new Pregnancy();
        BeanUtils.copyProperties(requestPregnancyDto,pregnancy);
        updateById(pregnancy);
        return AjaxResult.success();
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
