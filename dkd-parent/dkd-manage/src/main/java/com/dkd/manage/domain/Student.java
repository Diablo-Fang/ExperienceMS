package com.dkd.manage.domain;

import java.util.List;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.dkd.common.annotation.Excel;
import com.dkd.common.core.domain.BaseEntity;

/**
 * 学生管理对象 tb_student
 * 
 * @author FangChuYu
 * @date 2025-06-24
 */
public class Student extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 学生表主键，自增整数 */
    @Excel(name = "学生表主键，自增整数")
    private Long id;

    /** 学号，唯一标识 */
    @Excel(name = "学号，唯一标识")
    private String studentCode;

    /** 学生姓名 */
    @Excel(name = "学生姓名")
    private String studentName;

    /** 授课教师id */
    @Excel(name = "授课教师id")
    private Long teacherId;

    /** 实验预约时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "实验预约时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date experimentAppointmentTime;

    /** 实验数据管理信息 */
    private List<Experiment1> experiment1List;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setStudentCode(String studentCode) 
    {
        this.studentCode = studentCode;
    }

    public String getStudentCode() 
    {
        return studentCode;
    }
    public void setStudentName(String studentName) 
    {
        this.studentName = studentName;
    }

    public String getStudentName() 
    {
        return studentName;
    }
    public void setTeacherId(Long teacherId) 
    {
        this.teacherId = teacherId;
    }

    public Long getTeacherId() 
    {
        return teacherId;
    }
    public void setExperimentAppointmentTime(Date experimentAppointmentTime) 
    {
        this.experimentAppointmentTime = experimentAppointmentTime;
    }

    public Date getExperimentAppointmentTime() 
    {
        return experimentAppointmentTime;
    }

    public List<Experiment1> getExperiment1List()
    {
        return experiment1List;
    }

    public void setExperiment1List(List<Experiment1> experiment1List)
    {
        this.experiment1List = experiment1List;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("studentCode", getStudentCode())
            .append("studentName", getStudentName())
            .append("teacherId", getTeacherId())
            .append("experimentAppointmentTime", getExperimentAppointmentTime())
            .append("createTime", getCreateTime())
            .append("remark", getRemark())
            .append("experiment1List", getExperiment1List())
            .toString();
    }
}
