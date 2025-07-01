package com.dkd.manage.mapper;

import java.util.List;
import com.dkd.manage.domain.Experiment1;

/**
 * 实验数据管理Mapper接口
 * 
 * @author FangChuYu
 * @date 2025-07-01
 */
public interface Experiment1Mapper 
{
    /**
     * 查询实验数据管理
     * 
     * @param id 实验数据管理主键
     * @return 实验数据管理
     */
    public Experiment1 selectExperiment1ById(Long id);

    /**
     * 查询实验数据管理列表
     * 
     * @param experiment1 实验数据管理
     * @return 实验数据管理集合
     */
    public List<Experiment1> selectExperiment1List(Experiment1 experiment1);

    /**
     * 新增实验数据管理
     * 
     * @param experiment1 实验数据管理
     * @return 结果
     */
    public int insertExperiment1(Experiment1 experiment1);

    /**
     * 修改实验数据管理
     * 
     * @param experiment1 实验数据管理
     * @return 结果
     */
    public int updateExperiment1(Experiment1 experiment1);

    /**
     * 删除实验数据管理
     * 
     * @param id 实验数据管理主键
     * @return 结果
     */
    public int deleteExperiment1ById(Long id);

    /**
     * 批量删除实验数据管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteExperiment1ByIds(Long[] ids);
}
