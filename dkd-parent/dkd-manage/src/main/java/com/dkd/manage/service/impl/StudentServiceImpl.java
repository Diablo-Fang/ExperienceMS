package com.dkd.manage.service.impl;

import java.util.List;
import com.dkd.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.dkd.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.dkd.manage.domain.Experiment1;
import com.dkd.manage.mapper.StudentMapper;
import com.dkd.manage.domain.Student;
import com.dkd.manage.service.IStudentService;

/**
 * 学生管理Service业务层处理
 * 
 * @author FangChuYu
 * @date 2025-06-24
 */
@Service
public class StudentServiceImpl implements IStudentService 
{
    @Autowired
    private StudentMapper studentMapper;

    /**
     * 查询学生管理
     * 
     * @param id 学生管理主键
     * @return 学生管理
     */
    @Override
    public Student selectStudentById(Long id)
    {
        return studentMapper.selectStudentById(id);
    }

    /**
     * 查询学生管理列表
     * 
     * @param student 学生管理
     * @return 学生管理
     */
    @Override
    public List<Student> selectStudentList(Student student)
    {
        return studentMapper.selectStudentList(student);
    }

    /**
     * 新增学生管理
     * 
     * @param student 学生管理
     * @return 结果
     */
    @Transactional
    @Override
    public int insertStudent(Student student)
    {
        student.setCreateTime(DateUtils.getNowDate());
        int rows = studentMapper.insertStudent(student);
        insertExperiment1(student);
        return rows;
    }

    /**
     * 修改学生管理
     * 
     * @param student 学生管理
     * @return 结果
     */
    @Transactional
    @Override
    public int updateStudent(Student student)
    {
        studentMapper.deleteExperiment1ByStudentId(student.getId());
        insertExperiment1(student);
        return studentMapper.updateStudent(student);
    }

    /**
     * 批量删除学生管理
     * 
     * @param ids 需要删除的学生管理主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteStudentByIds(Long[] ids)
    {
        studentMapper.deleteExperiment1ByStudentIds(ids);
        return studentMapper.deleteStudentByIds(ids);
    }

    /**
     * 删除学生管理信息
     * 
     * @param id 学生管理主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteStudentById(Long id)
    {
        studentMapper.deleteExperiment1ByStudentId(id);
        return studentMapper.deleteStudentById(id);
    }

    /**
     * 新增实验数据管理信息
     * 
     * @param student 学生管理对象
     */
    public void insertExperiment1(Student student)
    {
        List<Experiment1> experiment1List = student.getExperiment1List();
        Long id = student.getId();
        if (StringUtils.isNotNull(experiment1List))
        {
            List<Experiment1> list = new ArrayList<Experiment1>();
            for (Experiment1 experiment1 : experiment1List)
            {
                experiment1.setStudentId(id);
                list.add(experiment1);
            }
            if (list.size() > 0)
            {
                studentMapper.batchExperiment1(list);
            }
        }
    }
}
