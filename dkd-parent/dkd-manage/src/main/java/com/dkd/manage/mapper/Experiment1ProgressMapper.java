package com.dkd.manage.mapper;

import java.util.List;
import com.dkd.manage.domain.Experiment1Progress;

/**
 * 实验进度记录Mapper接口
 * 
 * @author FangChuYu
 * @date 2025-08-03
 */
public interface Experiment1ProgressMapper 
{
    /**
     * 查询实验进度记录
     * 
     * @param id 实验进度记录主键
     * @return 实验进度记录
     */
    public Experiment1Progress selectExperiment1ProgressById(Long id);

    /**
     * 查询实验进度记录列表
     * 
     * @param experiment1Progress 实验进度记录
     * @return 实验进度记录集合
     */
    public List<Experiment1Progress> selectExperiment1ProgressList(Experiment1Progress experiment1Progress);

    /**
     * 新增实验进度记录
     * 
     * @param experiment1Progress 实验进度记录
     * @return 结果
     */
    public int insertExperiment1Progress(Experiment1Progress experiment1Progress);

    /**
     * 修改实验进度记录
     * 
     * @param experiment1Progress 实验进度记录
     * @return 结果
     */
    public int updateExperiment1Progress(Experiment1Progress experiment1Progress);

    /**
     * 删除实验进度记录
     * 
     * @param id 实验进度记录主键
     * @return 结果
     */
    public int deleteExperiment1ProgressById(Long id);

    /**
     * 批量删除实验进度记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteExperiment1ProgressByIds(Long[] ids);
}
