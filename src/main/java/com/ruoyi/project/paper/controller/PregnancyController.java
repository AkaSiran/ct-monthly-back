package com.ruoyi.project.paper.controller;

import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.paper.dto.RequestPregnancyDto;
import com.ruoyi.project.paper.dto.ResponsePregnancyDto;
import com.ruoyi.project.paper.service.PregnancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Flareon on 2022-2-10.
 */
@RestController
@RequestMapping("/paper/pregnancy")
public class PregnancyController extends BaseController
{
    @Autowired
    private PregnancyService pregnancyService;

    /**
     * 查询pregnancy列表
     */
    @PreAuthorize("@ss.hasPermi('paper:pregnancy:list')")
    @GetMapping("/list")
    public TableDataInfo list(RequestPregnancyDto requestPregnancyDto)
    {
        startPage();
        List<ResponsePregnancyDto> list = pregnancyService.selectPregnancyList(requestPregnancyDto);
        return getDataTable(list);
    }

    /**
     * 获取pregnancy详细信息
     */
    @PreAuthorize("@ss.hasPermi('paper:pregnancy:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(pregnancyService.selectPregnancyById(id));
    }

    /**
     * 新增pregnancy
     */
    @PreAuthorize("@ss.hasPermi('paper:pregnancy:add')")
    @Log(title = "pregnancy", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RequestPregnancyDto requestPregnancyDto)
    {
        return pregnancyService.insertPregnancy(requestPregnancyDto);
    }

    /**
     * 修改pregnancy
     */
    @PreAuthorize("@ss.hasPermi('paper:pregnancy:edit')")
    @Log(title = "pregnancy", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RequestPregnancyDto requestPregnancyDto)
    {
        return pregnancyService.updatePregnancy(requestPregnancyDto);
    }

    /**
     * 删除pregnancy
     */
    @PreAuthorize("@ss.hasPermi('paper:pregnancy:remove')")
    @Log(title = "pregnancy", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return pregnancyService.deletePregnancyByIds(ids);
    }
}
