package com.ruoyi.project.paper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.ruoyi.common.enums.DeleteFlagEnum;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.paper.domain.Pregnancy;
import com.ruoyi.project.paper.dto.RequestPregnancyDto;
import com.ruoyi.project.paper.dto.ResponsePregnancyDto;
import com.ruoyi.project.paper.mapper.PregnancyMapper;
import com.ruoyi.project.paper.service.PregnancyService;
import com.ruoyi.project.system.domain.SysDictData;
import com.ruoyi.project.system.service.ISysDictDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Flareon on 2022-2-10.
 */
@Slf4j
@Service
public class PregnancyServiceImpl extends MPJBaseServiceImpl<PregnancyMapper,Pregnancy> implements PregnancyService
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
            //上月峰值信息获取
            Date thisTime = pregnancy.getOperateTime();
            String thisTimeStr = DateUtils.parseDateToStr(DateUtils.YYYY_MM,thisTime);
            String lastDateStr = DateUtils.calculateTime(thisTimeStr,0,-1,0,DateUtils.YYYY_MM);
            log.info("本月时间 = {},上月时间 = {}",thisTimeStr,lastDateStr);
            //获取上月峰值信息
            List<Pregnancy> peakList = list(new MPJLambdaWrapper<Pregnancy>()
                    .selectAll(Pregnancy.class)
                    .eq(Pregnancy::getDelFlag, DeleteFlagEnum.NORMAL.getCode())
                    .apply("DATE_FORMAT(operate_time, '%Y-%m') = '"+lastDateStr+"'")
                    .leftJoin(SysDictData.class,SysDictData::getDictValue,Pregnancy::getDegree)
                    .eq(SysDictData::getDictType,"t_pregnancy_degree")
                    .orderByDesc(SysDictData::getDictLabel).last("LIMIT 1")
            );
            if(CollectionUtils.isNotEmpty(peakList))
            {
                Pregnancy peakPregnancy = peakList.get(0);
                Date peakOperateTime = peakPregnancy.getOperateTime();
                String peakDegree = peakPregnancy.getDegree();
                String peakDegreeLabel = sysDictDataService.selectDictLabel("t_pregnancy_degree",peakDegree);
                responsePregnancyDto.setLastPeakDegreeLabel(peakDegreeLabel);
                responsePregnancyDto.setLastPeakTime(peakOperateTime);
            }
            //获取上月等值信息
            List<Pregnancy> equivalenceList = list(new MPJLambdaWrapper<Pregnancy>()
                    .selectAll(Pregnancy.class)
                    .eq(Pregnancy::getDegree,degree)
                    .eq(Pregnancy::getDelFlag, DeleteFlagEnum.NORMAL.getCode())
                    .apply("DATE_FORMAT(operate_time, '%Y-%m') = '"+lastDateStr+"'")
                    .leftJoin(SysDictData.class,SysDictData::getDictValue,Pregnancy::getDegree)
                    .orderByDesc(SysDictData::getDictLabel).last("LIMIT 1")
            );
            if(CollectionUtils.isNotEmpty(equivalenceList))
            {
                Pregnancy equivalencePregnancy = equivalenceList.get(0);
                Date equivalenceOperateTime = equivalencePregnancy.getOperateTime();
                responsePregnancyDto.setLastEquivalenceTime(equivalenceOperateTime);
            }
            //预估时间、预估时间差
            if(CollectionUtils.isNotEmpty(peakList) && CollectionUtils.isNotEmpty(equivalenceList))
            {
                Pregnancy peakPregnancy = peakList.get(0);
                Pregnancy equivalencePregnancy = equivalenceList.get(0);
                Date peakOperateTime = peakPregnancy.getOperateTime();
                Date equivalenceOperateTime = equivalencePregnancy.getOperateTime();
                int dayDifference = DateUtils.dayDifference(peakOperateTime,equivalenceOperateTime);
                String thisDayStr = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD,thisTime);
                String expectPeakTimeStr =  DateUtils.calculateTime(thisDayStr,0,0,dayDifference,DateUtils.YYYY_MM_DD);
                Date expectPeakTime = DateUtils.dateTime(DateUtils.YYYY_MM_DD,expectPeakTimeStr);
                responsePregnancyDto.setDayDifference(dayDifference);
                responsePregnancyDto.setExpectPeakTime(expectPeakTime);
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
                .eq(Pregnancy::getDelFlag, DeleteFlagEnum.NORMAL.getCode())
                .orderByDesc(Pregnancy::getOperateTime));
        List<ResponsePregnancyDto> resultList = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(list))
        {
            list.forEach(item ->
            {
                ResponsePregnancyDto resultItem = new ResponsePregnancyDto();
                BeanUtils.copyProperties(item,resultItem);
                //试纸深度字典转换
                String degree = item.getDegree();
                if(StringUtils.isNotBlank(degree))
                {
                    String degreeLabel = sysDictDataService.selectDictLabel("t_pregnancy_degree",degree);
                    resultItem.setDegreeLabel(degreeLabel);
                }
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
        List<Pregnancy> list = Lists.newArrayList();
        if(null!=ids && ids.length>0)
        {
            for(Long id : ids)
            {
                Pregnancy pregnancy = new Pregnancy();
                pregnancy.setId(id);
                pregnancy.setDelFlag(DeleteFlagEnum.DELETE.getCode());
                pregnancy.setUpdateTime(new Date());
                updateById(pregnancy);
                list.add(pregnancy);
            }
            saveOrUpdateBatch(list);
            return AjaxResult.success();
        }else
        {
            return AjaxResult.error("未获取到需删除的数据");
        }
    }

    @Override
    public AjaxResult deletePregnancyById(Long id)
    {
        Pregnancy pregnancy = new Pregnancy();
        pregnancy.setId(id);
        pregnancy.setDelFlag(DeleteFlagEnum.DELETE.getCode());
        pregnancy.setUpdateTime(new Date());
        updateById(pregnancy);
        return AjaxResult.success();
    }
}
