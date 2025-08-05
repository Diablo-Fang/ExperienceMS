package com.dkd.manage.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.dkd.common.annotation.Excel;
import com.dkd.common.core.domain.BaseEntity;

/**
 * 实验进度记录对象 tb_experiment1_progress
 * 
 * @author FangChuYu
 * @date 2025-08-03
 */
public class Experiment1Progress extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    @Excel(name = "自增主键")
    private Long id;

    /** 学号 */
    @Excel(name = "学号")
    private Long studentId;

    /** 实验进度，6位字符分别表示6个部分的完成状态，0-未完成，1-已完成 */
    @Excel(name = "实验进度，6位字符分别表示6个部分的完成状态，0-未完成，1-已完成")
    private String progress;

    /** 第一部分完成时间（分钟） */
    @Excel(name = "第一部分完成时间", readConverterExp = "分=钟")
    private Long part1Time;

    /** 第二部分完成时间（分钟） */
    @Excel(name = "第二部分完成时间", readConverterExp = "分=钟")
    private Long part2Time;

    /** 第三部分完成时间（分钟） */
    @Excel(name = "第三部分完成时间", readConverterExp = "分=钟")
    private Long part3Time;

    /** 第四部分完成时间（分钟） */
    @Excel(name = "第四部分完成时间", readConverterExp = "分=钟")
    private Long part4Time;

    /** 第五部分完成时间（分钟） */
    @Excel(name = "第五部分完成时间", readConverterExp = "分=钟")
    private Long part5Time;

    /** 第六部分完成时间（分钟） */
    @Excel(name = "第六部分完成时间", readConverterExp = "分=钟")
    private Long part6Time;

    /** 创建时间 */
    private Date createdAt;

    /** 更新时间 */
    private Date updatedAt;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setStudentId(Long studentId) 
    {
        this.studentId = studentId;
    }

    public Long getStudentId() 
    {
        return studentId;
    }
    public void setProgress(String progress) 
    {
        this.progress = progress;
    }

    public String getProgress() 
    {
        return progress;
    }
    public void setPart1Time(Long part1Time) 
    {
        this.part1Time = part1Time;
    }

    public Long getPart1Time() 
    {
        return part1Time;
    }
    public void setPart2Time(Long part2Time) 
    {
        this.part2Time = part2Time;
    }

    public Long getPart2Time() 
    {
        return part2Time;
    }
    public void setPart3Time(Long part3Time) 
    {
        this.part3Time = part3Time;
    }

    public Long getPart3Time() 
    {
        return part3Time;
    }
    public void setPart4Time(Long part4Time) 
    {
        this.part4Time = part4Time;
    }

    public Long getPart4Time() 
    {
        return part4Time;
    }
    public void setPart5Time(Long part5Time) 
    {
        this.part5Time = part5Time;
    }

    public Long getPart5Time() 
    {
        return part5Time;
    }
    public void setPart6Time(Long part6Time) 
    {
        this.part6Time = part6Time;
    }

    public Long getPart6Time() 
    {
        return part6Time;
    }
    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }
    public void setUpdatedAt(Date updatedAt) 
    {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() 
    {
        return updatedAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("studentId", getStudentId())
            .append("progress", getProgress())
            .append("part1Time", getPart1Time())
            .append("part2Time", getPart2Time())
            .append("part3Time", getPart3Time())
            .append("part4Time", getPart4Time())
            .append("part5Time", getPart5Time())
            .append("part6Time", getPart6Time())
            .append("createdAt", getCreatedAt())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
