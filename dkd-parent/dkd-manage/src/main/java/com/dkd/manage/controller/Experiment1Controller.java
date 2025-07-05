package com.dkd.manage.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.dkd.common.annotation.Log;
import com.dkd.common.core.controller.BaseController;
import com.dkd.common.core.domain.AjaxResult;
import com.dkd.common.enums.BusinessType;
import com.dkd.manage.domain.Experiment1;
import com.dkd.manage.service.IExperiment1Service;
import com.dkd.common.utils.poi.ExcelUtil;
import com.dkd.common.core.page.TableDataInfo;

/**
 * 实验数据管理Controller
 * 
 * @author FangChuYu
 * @date 2025-07-01
 */
@RestController
@RequestMapping("/manage/experiment1")
public class Experiment1Controller extends BaseController
{
    @Autowired
    private IExperiment1Service experiment1Service;

    /**
     * 查询实验数据管理列表
     */
    //@PreAuthorize("@ss.hasPermi('manage:experiment1:list')")
    @GetMapping("/list")
    public TableDataInfo list(Experiment1 experiment1)
    {
        startPage();
        List<Experiment1> list = experiment1Service.selectExperiment1List(experiment1);
        return getDataTable(list);
    }

    /**
     * 导出实验数据管理列表
     */
    //@PreAuthorize("@ss.hasPermi('manage:experiment1:export')")
    @Log(title = "实验数据管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Experiment1 experiment1)
    {
        List<Experiment1> list = experiment1Service.selectExperiment1List(experiment1);
        ExcelUtil<Experiment1> util = new ExcelUtil<Experiment1>(Experiment1.class);
        util.exportExcel(response, list, "实验数据管理数据");
    }

    /**
     * 获取实验数据管理详细信息
     */
    //@PreAuthorize("@ss.hasPermi('manage:experiment1:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(experiment1Service.selectExperiment1ById(id));
    }

    /**
     * 新增实验数据管理
     */
    //@PreAuthorize("@ss.hasPermi('manage:experiment1:add')")
    @Log(title = "实验数据管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Experiment1 experiment1)
    {
        return toAjax(experiment1Service.insertExperiment1(experiment1));
    }

    /**
     * 修改实验数据管理
     */
    //@PreAuthorize("@ss.hasPermi('manage:experiment1:edit')")
    @Log(title = "实验数据管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Experiment1 experiment1)
    {
        return toAjax(experiment1Service.updateExperiment1(experiment1));
    }

    /**
     * 删除实验数据管理
     */
    //@PreAuthorize("@ss.hasPermi('manage:experiment1:remove')")
    @Log(title = "实验数据管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(experiment1Service.deleteExperiment1ByIds(ids));
    }
}
