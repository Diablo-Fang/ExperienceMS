package com.dkd.manage.service.impl;

import java.util.List;
import com.dkd.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dkd.manage.mapper.Experiment1Mapper;
import com.dkd.manage.domain.Experiment1;
import com.dkd.manage.service.IExperiment1Service;

/**
 * 实验数据管理Service业务层处理
 * 
 * @author FangChuYu
 * @date 2025-07-01
 */
@Service
public class Experiment1ServiceImpl implements IExperiment1Service 
{
    @Autowired
    private Experiment1Mapper experiment1Mapper;

    /**
     * 查询实验数据管理
     * 
     * @param id 实验数据管理主键
     * @return 实验数据管理
     */
    @Override
    public Experiment1 selectExperiment1ById(Long id)
    {
        return experiment1Mapper.selectExperiment1ById(id);
    }

    /**
     * 查询实验数据管理列表
     * 
     * @param experiment1 实验数据管理
     * @return 实验数据管理
     */
    @Override
    public List<Experiment1> selectExperiment1List(Experiment1 experiment1)
    {
        return experiment1Mapper.selectExperiment1List(experiment1);
    }

    /**
     * 新增实验数据管理
     * 
     * @param experiment1 实验数据管理
     * @return 结果
     */
    @Override
    public int insertExperiment1(Experiment1 experiment1)
    {
        experiment1.setCreateTime(DateUtils.getNowDate());
        return experiment1Mapper.insertExperiment1(experiment1);
    }

    /**
     * 修改实验数据管理
     * 
     * @param experiment1 实验数据管理
     * @return 结果
     */
    @Override
    public int updateExperiment1(Experiment1 experiment1)
    {
        experiment1.setUpdateTime(DateUtils.getNowDate());
        return experiment1Mapper.updateExperiment1(experiment1);
    }

    /**
     * 批量删除实验数据管理
     * 
     * @param ids 需要删除的实验数据管理主键
     * @return 结果
     */
    @Override
    public int deleteExperiment1ByIds(Long[] ids)
    {
        return experiment1Mapper.deleteExperiment1ByIds(ids);
    }

    /**
     * 删除实验数据管理信息
     * 
     * @param id 实验数据管理主键
     * @return 结果
     */
    @Override
    public int deleteExperiment1ById(Long id)
    {
        return experiment1Mapper.deleteExperiment1ById(id);
    }
}
