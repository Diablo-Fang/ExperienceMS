package com.dkd.manage.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.dkd.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.dkd.manage.domain.Student;
import com.dkd.manage.mapper.TeacherMapper;
import com.dkd.manage.domain.Teacher;
import com.dkd.manage.service.ITeacherService;

/**
 * 教师管理Service业务层处理
 * 
 * @author FangChuYu
 * @date 2025-06-24
 */
@Service
public class TeacherServiceImpl implements ITeacherService 
{
    @Autowired
    private TeacherMapper teacherMapper;

    /**
     * 查询教师管理
     * 
     * @param id 教师管理主键
     * @return 教师管理
     */
    @Override
    public Teacher selectTeacherById(Long id)
    {
        return teacherMapper.selectTeacherById(id);
    }

    /**
     * 查询教师管理列表
     * 
     * @param teacher 教师管理
     * @return 教师管理
     */
    @Override
    public List<Teacher> selectTeacherList(Teacher teacher)
    {
        return teacherMapper.selectTeacherList(teacher);
    }

    /**
     * 新增教师管理
     * 
     * @param teacher 教师管理
     * @return 结果
     */
    @Transactional
    @Override
    public int insertTeacher(Teacher teacher)
    {
        int rows = teacherMapper.insertTeacher(teacher);
        insertStudent(teacher);
        return rows;
    }

    /**
     * 修改教师管理
     * 
     * @param teacher 教师管理
     * @return 结果
     */
    @Transactional
    @Override
    public int updateTeacher(Teacher teacher)
    {
        teacherMapper.deleteStudentByTeacherId(teacher.getId());
        insertStudent(teacher);
        return teacherMapper.updateTeacher(teacher);
    }

    /**
     * 批量删除教师管理
     * 
     * @param ids 需要删除的教师管理主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteTeacherByIds(Long[] ids)
    {
        teacherMapper.deleteStudentByTeacherIds(ids);
        return teacherMapper.deleteTeacherByIds(ids);
    }

    /**
     * 删除教师管理信息
     * 
     * @param id 教师管理主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteTeacherById(Long id)
    {
        teacherMapper.deleteStudentByTeacherId(id);
        return teacherMapper.deleteTeacherById(id);
    }

    /**
     * 新增学生管理信息
     * 
     * @param teacher 教师管理对象
     */
    public void insertStudent(Teacher teacher)
    {
        List<Student> studentList = teacher.getStudentList();
        Long id = teacher.getId();
        if (StringUtils.isNotNull(studentList))
        {
            List<Student> list = new ArrayList<Student>();
            for (Student student : studentList)
            {
                student.setTeacherId(id);
                list.add(student);
            }
            if (list.size() > 0)
            {
                teacherMapper.batchStudent(list);
            }
        }
    }
}
