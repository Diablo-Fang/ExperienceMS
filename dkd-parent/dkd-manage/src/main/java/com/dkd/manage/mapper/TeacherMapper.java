package com.dkd.manage.mapper;

import java.util.List;
import com.dkd.manage.domain.Teacher;
import com.dkd.manage.domain.Student;

/**
 * 教师管理Mapper接口
 * 
 * @author FangChuYu
 * @date 2025-06-24
 */
public interface TeacherMapper 
{
    /**
     * 查询教师管理
     * 
     * @param id 教师管理主键
     * @return 教师管理
     */
    public Teacher selectTeacherById(Long id);

    /**
     * 查询教师管理列表
     * 
     * @param teacher 教师管理
     * @return 教师管理集合
     */
    public List<Teacher> selectTeacherList(Teacher teacher);

    /**
     * 新增教师管理
     * 
     * @param teacher 教师管理
     * @return 结果
     */
    public int insertTeacher(Teacher teacher);

    /**
     * 修改教师管理
     * 
     * @param teacher 教师管理
     * @return 结果
     */
    public int updateTeacher(Teacher teacher);

    /**
     * 删除教师管理
     * 
     * @param id 教师管理主键
     * @return 结果
     */
    public int deleteTeacherById(Long id);

    /**
     * 批量删除教师管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTeacherByIds(Long[] ids);

    /**
     * 批量删除学生管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStudentByTeacherIds(Long[] ids);
    
    /**
     * 批量新增学生管理
     * 
     * @param studentList 学生管理列表
     * @return 结果
     */
    public int batchStudent(List<Student> studentList);
    

    /**
     * 通过教师管理主键删除学生管理信息
     * 
     * @param id 教师管理ID
     * @return 结果
     */
    public int deleteStudentByTeacherId(Long id);
}
