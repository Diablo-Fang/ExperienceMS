package com.dkd.manage.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dkd.manage.mapper.Experiment1ProgressMapper;
import com.dkd.manage.domain.Experiment1Progress;
import com.dkd.manage.service.IExperiment1ProgressService;

/**
 * 实验进度记录Service业务层处理
 * 
 * @author FangChuYu
 * @date 2025-08-03
 */
@Service
public class Experiment1ProgressServiceImpl implements IExperiment1ProgressService 
{
    @Autowired
    private Experiment1ProgressMapper experiment1ProgressMapper;

    /**
     * 查询实验进度记录
     * 
     * @param id 实验进度记录主键
     * @return 实验进度记录
     */
    @Override
    public Experiment1Progress selectExperiment1ProgressById(Long id)
    {
        return experiment1ProgressMapper.selectExperiment1ProgressById(id);
    }

    /**
     * 查询实验进度记录列表
     * 
     * @param experiment1Progress 实验进度记录
     * @return 实验进度记录
     */
    @Override
    public List<Experiment1Progress> selectExperiment1ProgressList(Experiment1Progress experiment1Progress)
    {
        return experiment1ProgressMapper.selectExperiment1ProgressList(experiment1Progress);
    }

    /**
     * 新增实验进度记录
     * 
     * @param experiment1Progress 实验进度记录
     * @return 结果
     */
    @Override
    public int insertExperiment1Progress(Experiment1Progress experiment1Progress)
    {
        return experiment1ProgressMapper.insertExperiment1Progress(experiment1Progress);
    }

    /**
     * 修改实验进度记录
     * 
     * @param experiment1Progress 实验进度记录
     * @return 结果
     */
    @Override
    public int updateExperiment1Progress(Experiment1Progress experiment1Progress)
    {
        return experiment1ProgressMapper.updateExperiment1Progress(experiment1Progress);
    }

    /**
     * 批量删除实验进度记录
     * 
     * @param ids 需要删除的实验进度记录主键
     * @return 结果
     */
    @Override
    public int deleteExperiment1ProgressByIds(Long[] ids)
    {
        return experiment1ProgressMapper.deleteExperiment1ProgressByIds(ids);
    }

    /**
     * 删除实验进度记录信息
     * 
     * @param id 实验进度记录主键
     * @return 结果
     */
    @Override
    public int deleteExperiment1ProgressById(Long id)
    {
        return experiment1ProgressMapper.deleteExperiment1ProgressById(id);
    }
}
