package com.dkd.manage.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dkd.common.annotation.Log;
import com.dkd.common.core.controller.BaseController;
import com.dkd.common.core.domain.AjaxResult;
import com.dkd.common.enums.BusinessType;
import com.dkd.manage.domain.Experiment1Progress;
import com.dkd.manage.service.IExperiment1ProgressService;
import com.dkd.common.utils.poi.ExcelUtil;
import com.dkd.common.core.page.TableDataInfo;

/**
 * 实验进度记录Controller
 * 
 * @author FangChuYu
 * @date 2025-08-03
 */
@RestController
@RequestMapping("/manage/progress")
public class Experiment1ProgressController extends BaseController
{
    @Autowired
    private IExperiment1ProgressService experiment1ProgressService;

    /**
     * 查询实验进度记录列表
     */
    //@PreAuthorize("@ss.hasPermi('manage:progress:list')")
    @GetMapping("/list")
    public TableDataInfo list(Experiment1Progress experiment1Progress)
    {
        startPage();
        List<Experiment1Progress> list = experiment1ProgressService.selectExperiment1ProgressList(experiment1Progress);
        return getDataTable(list);
    }

    /**
     * 导出实验进度记录列表
     */
    //@PreAuthorize("@ss.hasPermi('manage:progress:export')")
    @Log(title = "实验进度记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Experiment1Progress experiment1Progress)
    {
        List<Experiment1Progress> list = experiment1ProgressService.selectExperiment1ProgressList(experiment1Progress);
        ExcelUtil<Experiment1Progress> util = new ExcelUtil<Experiment1Progress>(Experiment1Progress.class);
        util.exportExcel(response, list, "实验进度记录数据");
    }

    /**
     * 获取实验进度记录详细信息
     */
    //@PreAuthorize("@ss.hasPermi('manage:progress:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(experiment1ProgressService.selectExperiment1ProgressById(id));
    }

    /**
     * 新增实验进度记录
     */
    //@PreAuthorize("@ss.hasPermi('manage:progress:add')")
    @Log(title = "实验进度记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Experiment1Progress experiment1Progress)
    {
        return toAjax(experiment1ProgressService.insertExperiment1Progress(experiment1Progress));
    }

    /**
     * 修改实验进度记录
     */
    //@PreAuthorize("@ss.hasPermi('manage:progress:edit')")
    @Log(title = "实验进度记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Experiment1Progress experiment1Progress)
    {
        return toAjax(experiment1ProgressService.updateExperiment1Progress(experiment1Progress));
    }

    /**
     * 删除实验进度记录
     */
    //@PreAuthorize("@ss.hasPermi('manage:progress:remove')")
    @Log(title = "实验进度记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(experiment1ProgressService.deleteExperiment1ProgressByIds(ids));
    }
}
