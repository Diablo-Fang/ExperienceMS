package com.dkd.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;


import com.dkd.manage.domain.Experiment1Progress;
import com.dkd.manage.service.IExperiment1ProgressService;
import com.dkd.manage.utils.DeepSeek;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    @Autowired
    private IExperiment1ProgressService experiment1ProgressService;

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

    /**
     * 获取AI分析结果
     */
    //@PreAuthorize("@ss.hasPermi('manage:experiment1:aiAnalysis')")
    @GetMapping("/aiAnalysis/{studentId}")
    public AjaxResult getAIAnalysis(@PathVariable Long studentId)
    {
        try {
//            // 获取学生实验数据
//            Experiment1 experiment1 = experiment1Service.selectExperiment1ById(studentId);
//            if (experiment1 == null) {
//                return AjaxResult.error("未找到学生实验数据");
//            }
//
//            // 获取完成时间（单位：分钟）
//            int completionTime = experiment1.getCompletionTime(); // 假设实体类包含该字段
//
//            // 调用AI分析
//            String analysisResult = DeepSeek.judgeCheatingSuspicionWithAI(completionTime);
            Experiment1Progress experiment1Progress = experiment1ProgressService.selectExperiment1ProgressById(studentId);
            if (experiment1Progress == null) {
                return AjaxResult.error("未找到学生实验数据");
            }
            Long part1_time = experiment1Progress.getPart1Time();
            Long part2_time = experiment1Progress.getPart2Time();
            Long part3_time = experiment1Progress.getPart3Time();
            Long part5_time = experiment1Progress.getPart5Time();
            Long part6_time = experiment1Progress.getPart6Time();
            // 计算总完成时间（单位：分钟）
            int totalCompletionTime = (int)(part1_time + part2_time + part3_time + part5_time + part6_time);;
            String analysisResult = DeepSeek.judgeCheatingSuspicionWithAI(totalCompletionTime);

            // 解析JSON结果
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> resultMap = mapper.readValue(analysisResult, new TypeReference<Map<String, Object>>() {});

            // 添加各个实验部分耗时信息
            resultMap.put("part1Time", part1_time.intValue());
            resultMap.put("part2Time", part2_time.intValue());
            resultMap.put("part3Time", part3_time.intValue());
            resultMap.put("part4Time", part5_time.intValue());
            resultMap.put("part5Time", part6_time.intValue());

            // 验证返回格式
            if (!resultMap.containsKey("suspicious") ||
                    !resultMap.containsKey("probability") ||
                    !resultMap.containsKey("analysis")) {
                return AjaxResult.error("AI分析结果格式错误");
            }

            // 返回标准化响应
            return AjaxResult.success(resultMap);

        } catch (Exception e) {
            // 记录日志
            logger.error("AI分析失败", e);
            return AjaxResult.error("AI分析失败：" + e.getMessage());
        }
    }

    /**
     * 获取实验评分
     */
    @GetMapping("/experimentScore/{studentId}")
    public AjaxResult getExperimentScore(@PathVariable Long studentId) {
        try {
            // 获取学生实验数据
            Experiment1 experimentData = experiment1Service.selectExperiment1ById(studentId);
            if (experimentData == null) {
                return AjaxResult.error("未找到学生实验数据");
            }

            // 定义标准参考数据
            Map<String, Object> referenceData = new HashMap<>();
            referenceData.put("table1vc1Field", 7.3);
            referenceData.put("table1vb1Field", 2.5);
            referenceData.put("table1ve1Field", 1.8);
            referenceData.put("table1vc2Field", 7.4);
            referenceData.put("table1vb2Field", 2.6);
            referenceData.put("table1ve2Field", 1.9);
            referenceData.put("table2vs1Field", 10.5);
            referenceData.put("table2vol1Field", 0.74);
            referenceData.put("table2avl1Field", 148);
            referenceData.put("table2vo1Field", 1.13);
            referenceData.put("table2av1Field", 226);
            referenceData.put("table2ri1Field", 9.09);
            referenceData.put("table2ro1Field", 2.48);
            referenceData.put("table2vo3Field", 1.04);
            referenceData.put("table2av3Field", 208);
            referenceData.put("table2w1Field", 7.96);
            referenceData.put("table2vs2Field", 9.2);
            referenceData.put("table2vol2Field", 0.25);
            referenceData.put("table2avl2Field", 50);
            referenceData.put("table2vo2Field", 0.29);
            referenceData.put("table2av2Field", 58);
            referenceData.put("table2ri2Field", 11.90);
            referenceData.put("table2ro2Field", 0.75);
            referenceData.put("table2vo4Field", 0.28);
            referenceData.put("table2av4Field", 56);
            referenceData.put("table2w2Field", 3.45);
            referenceData.put("table3vc11Field", 6.18);
            referenceData.put("table3vc21Field", 6.17);
            referenceData.put("table3vb11Field", -0.06);
            referenceData.put("table3vb21Field", -0.07);
            referenceData.put("table3ve11Field", -0.73);
            referenceData.put("table3ve21Field", -0.72);
            referenceData.put("table3vc12Field", 4.87);
            referenceData.put("table3vc22Field", 4.88);
            referenceData.put("table3vb12Field", -0.08);
            referenceData.put("table3vb22Field", -0.09);
            referenceData.put("table3ve12Field", -0.75);
            referenceData.put("table3ve22Field", -0.74);
            referenceData.put("table4vo11Field", 1.03);
            referenceData.put("table4vo21Field", 1.03);
            referenceData.put("table4vo1Field", 2.06);
            referenceData.put("table4a1Field", 41.2);
            referenceData.put("table4k1Field", 588.6);
            referenceData.put("table4vo12Field", 0.44);
            referenceData.put("table4vo22Field", 0.37);
            referenceData.put("table4vo2Field", 0.07);
            referenceData.put("table4a2Field", 0.07);
            referenceData.put("table4vo13Field", 1.08);
            referenceData.put("table4vo23Field", 1.08);
            referenceData.put("table4vo3Field", 2.16);
            referenceData.put("table4a3Field", 43.2);
            referenceData.put("table4k2Field", 10800);
            referenceData.put("table4vo14Field", 0.023);
            referenceData.put("table4vo24Field", 0.019);
            referenceData.put("table4vo4Field", 0.004);
            referenceData.put("table4a4Field", 0.004);
            referenceData.put("table5vi1Field", 1);
            referenceData.put("table5vo1Field", -1.81);
            referenceData.put("table5vi2Field", 4);
            referenceData.put("table5vo2Field", -1.81);
            referenceData.put("currentText", "A、电压档");

            // 调用AI评分
            String scoreResult = DeepSeek.generateExperimentScore(experimentData, referenceData);

            // 解析JSON结果
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> resultMap = mapper.readValue(scoreResult, new TypeReference<Map<String, Object>>() {});

            // 验证返回格式
            if (!resultMap.containsKey("score") ||
                !resultMap.containsKey("accuracyScore") ||
                !resultMap.containsKey("waveScore") ||
                !resultMap.containsKey("operationScore") ||
                !resultMap.containsKey("evaluation")) {
                return AjaxResult.error("AI评分结果格式错误");
            }

            // 返回标准化响应
            return AjaxResult.success(resultMap);

        } catch (Exception e) {
            // 记录日志
            logger.error("实验评分失败", e);
            return AjaxResult.error("实验评分失败：" + e.getMessage());
        }
    }
}
