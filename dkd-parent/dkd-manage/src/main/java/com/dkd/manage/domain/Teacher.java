package com.dkd.manage.domain;

import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.dkd.common.annotation.Excel;
import com.dkd.common.core.domain.BaseEntity;

/**
 * 教师管理对象 tb_teacher
 * 
 * @author FangChuYu
 * @date 2025-06-24
 */
public class Teacher extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 唯一id */
    @Excel(name = "唯一id")
    private Long id;

    /** 工号 */
    @Excel(name = "工号")
    private Long teacherCode;

    /** 真实姓名 */
    @Excel(name = "真实姓名")
    private String name;

    /** 创建时间 */
    private Date createdTime;

    /** 学生管理信息 */
    private List<Student> studentList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTeacherCode(Long teacherCode) 
    {
        this.teacherCode = teacherCode;
    }

    public Long getTeacherCode() 
    {
        return teacherCode;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setCreatedTime(Date createdTime) 
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime() 
    {
        return createdTime;
    }

    public List<Student> getStudentList()
    {
        return studentList;
    }

    public void setStudentList(List<Student> studentList)
    {
        this.studentList = studentList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("teacherCode", getTeacherCode())
            .append("name", getName())
            .append("createdTime", getCreatedTime())
            .append("studentList", getStudentList())
            .toString();
    }
}
