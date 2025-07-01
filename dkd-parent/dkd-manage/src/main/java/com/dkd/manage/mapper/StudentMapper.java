package com.dkd.manage.mapper;

import java.util.List;
import com.dkd.manage.domain.Student;
import com.dkd.manage.domain.Experiment1;

/**
 * 学生管理Mapper接口
 * 
 * @author FangChuYu
 * @date 2025-06-24
 */
public interface StudentMapper 
{
    /**
     * 查询学生管理
     * 
     * @param id 学生管理主键
     * @return 学生管理
     */
    public Student selectStudentById(Long id);

    /**
     * 查询学生管理列表
     * 
     * @param student 学生管理
     * @return 学生管理集合
     */
    public List<Student> selectStudentList(Student student);

    /**
     * 新增学生管理
     * 
     * @param student 学生管理
     * @return 结果
     */
    public int insertStudent(Student student);

    /**
     * 修改学生管理
     * 
     * @param student 学生管理
     * @return 结果
     */
    public int updateStudent(Student student);

    /**
     * 删除学生管理
     * 
     * @param id 学生管理主键
     * @return 结果
     */
    public int deleteStudentById(Long id);

    /**
     * 批量删除学生管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStudentByIds(Long[] ids);

    /**
     * 批量删除实验数据管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteExperiment1ByStudentIds(Long[] ids);
    
    /**
     * 批量新增实验数据管理
     * 
     * @param experiment1List 实验数据管理列表
     * @return 结果
     */
    public int batchExperiment1(List<Experiment1> experiment1List);
    

    /**
     * 通过学生管理主键删除实验数据管理信息
     * 
     * @param id 学生管理ID
     * @return 结果
     */
    public int deleteExperiment1ByStudentId(Long id);
}
