package com.dkd.manage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.dkd.common.annotation.Excel;
import com.dkd.common.core.domain.BaseEntity;

/**
 * 实验数据管理对象 tb_experiment1
 * 
 * @author FangChuYu
 * @date 2025-07-01
 */
public class Experiment1 extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 实验数据ID */
    @Excel(name = "实验数据ID")
    private Long id;

    /** 学生ID */
    @Excel(name = "学生ID")
    private Long studentId;

    /** 表1-第一级-VC */
    private Long table1vc1Field;

    /** 表1-第一级-VB */
    private Long table1vb1Field;

    /** 表1-第一级-VE */
    private Long table1ve1Field;

    /** 表1-第二级-VC */
    private Long table1vc2Field;

    /** 表1-第二级-VB */
    private Long table1vb2Field;

    /** 表1-第二级-VE */
    private Long table1ve2Field;

    /** 表2-基本放大-Vs */
    private Long table2vs1Field;

    /** 表2-基本放大-Vol */
    private Long table2vol1Field;

    /** 表2-基本放大-Avl */
    private Long table2avl1Field;

    /** 表2-基本放大-Vo */
    private Long table2vo1Field;

    /** 表2-基本放大-Av */
    private Long table2av1Field;

    /** 表2-基本放大-Ri */
    private Long table2ri1Field;

    /** 表2-基本放大-Ro */
    private Long table2ro1Field;

    /** 表2-基本放大-Vo(10V) */
    private Long table2vo3Field;

    /** 表2-基本放大-Av(10V) */
    private Long table2av3Field;

    /** 表2-基本放大-W */
    private Long table2w1Field;

    /** 表2-负反馈放大-Vs */
    private Long table2vs2Field;

    /** 表2-负反馈放大-Vol */
    private Long table2vol2Field;

    /** 表2-负反馈放大-Avl */
    private Long table2avl2Field;

    /** 表2-负反馈放大-Vo */
    private Long table2vo2Field;

    /** 表2-负反馈放大-Av */
    private Long table2av2Field;

    /** 表2-负反馈放大-Ri */
    private Long table2ri2Field;

    /** 表2-负反馈放大-Ro */
    private Long table2ro2Field;

    /** 表2-负反馈放大-Vo(10V) */
    private Long table2vo4Field;

    /** 表2-负反馈放大-Av(10V) */
    private Long table2av4Field;

    /** 表2-负反馈放大-W */
    private Long table2w2Field;

    /** 表3-典型差放-VC1 */
    private Long table3vc11Field;

    /** 表3-典型差放-VC2 */
    private Long table3vc21Field;

    /** 表3-典型差放-VB1 */
    private Long table3vb11Field;

    /** 表3-典型差放-VB2 */
    private Long table3vb21Field;

    /** 表3-典型差放-VE1 */
    private Long table3ve11Field;

    /** 表3-典型差放-VE2 */
    private Long table3ve21Field;

    /** 表3-恒流源差放-VC1 */
    private Long table3vc12Field;

    /** 表3-恒流源差放-VC2 */
    private Long table3vc22Field;

    /** 表3-恒流源差放-VB1 */
    private Long table3vb12Field;

    /** 表3-恒流源差放-VB2 */
    private Long table3vb22Field;

    /** 表3-恒流源差放-VE1 */
    private Long table3ve12Field;

    /** 表3-恒流源差放-VE2 */
    private Long table3ve22Field;

    /** 表4-典型差放差模-VO1 */
    private Long table4vo11Field;

    /** 表4-典型差放差模-VO2 */
    private Long table4vo21Field;

    /** 表4-典型差放差模-VO */
    private Long table4vo1Field;

    /** 表4-典型差放差模-增益 */
    private Long table4a1Field;

    /** 表4-典型差放差模-KCMR */
    private Long table4k1Field;

    /** 表4-典型差放共模-VO1 */
    private Long table4vo12Field;

    /** 表4-典型差放共模-VO2 */
    private Long table4vo22Field;

    /** 表4-典型差放共模-VO */
    private Long table4vo2Field;

    /** 表4-典型差放共模-增益 */
    private Long table4a2Field;

    /** 表4-恒流源差放差模-VO1 */
    private Long table4vo13Field;

    /** 表4-恒流源差放差模-VO2 */
    private Long table4vo23Field;

    /** 表4-恒流源差放差模-VO */
    private Long table4vo3Field;

    /** 表4-恒流源差放差模-增益 */
    private Long table4a3Field;

    /** 表4-恒流源差放差模-KCMR */
    private Long table4k2Field;

    /** 表4-恒流源差放共模-VO1 */
    private Long table4vo14Field;

    /** 表4-恒流源差放共模-VO2 */
    private Long table4vo24Field;

    /** 表4-恒流源差放共模-VO */
    private Long table4vo4Field;

    /** 表4-恒流源差放共模-增益 */
    private Long table4a4Field;

    /** 表5-反向比例-Vo1 */
    private Long table5vi1Field;

    /** 表5-反向比例-运放序号1 */
    private Long table5vo1Field;

    /** 表5-反向比例-Vo2 */
    private Long table5vi2Field;

    /** 表5-反向比例-运放序号2 */
    private Long table5vo2Field;

    /** 万用表档位 */
    private String currentText;

    /** vi1vo1图像路径 */
    private String vi1vo1Image;

    /** vi1vo2图像路径 */
    private String vi1vo2Image;

    /** vo1vo2图像路径 */
    private String vo1vo2Image;

    /** 实验成绩 */
    @Excel(name = "实验成绩")
    private Long experimentScore;

    /** 实验进度 */
    @Excel(name = "实验进度")
    private String progress;

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
    public void setTable1vc1Field(Long table1vc1Field) 
    {
        this.table1vc1Field = table1vc1Field;
    }

    public Long getTable1vc1Field() 
    {
        return table1vc1Field;
    }
    public void setTable1vb1Field(Long table1vb1Field) 
    {
        this.table1vb1Field = table1vb1Field;
    }

    public Long getTable1vb1Field() 
    {
        return table1vb1Field;
    }
    public void setTable1ve1Field(Long table1ve1Field) 
    {
        this.table1ve1Field = table1ve1Field;
    }

    public Long getTable1ve1Field() 
    {
        return table1ve1Field;
    }
    public void setTable1vc2Field(Long table1vc2Field) 
    {
        this.table1vc2Field = table1vc2Field;
    }

    public Long getTable1vc2Field() 
    {
        return table1vc2Field;
    }
    public void setTable1vb2Field(Long table1vb2Field) 
    {
        this.table1vb2Field = table1vb2Field;
    }

    public Long getTable1vb2Field() 
    {
        return table1vb2Field;
    }
    public void setTable1ve2Field(Long table1ve2Field) 
    {
        this.table1ve2Field = table1ve2Field;
    }

    public Long getTable1ve2Field() 
    {
        return table1ve2Field;
    }
    public void setTable2vs1Field(Long table2vs1Field) 
    {
        this.table2vs1Field = table2vs1Field;
    }

    public Long getTable2vs1Field() 
    {
        return table2vs1Field;
    }
    public void setTable2vol1Field(Long table2vol1Field) 
    {
        this.table2vol1Field = table2vol1Field;
    }

    public Long getTable2vol1Field() 
    {
        return table2vol1Field;
    }
    public void setTable2avl1Field(Long table2avl1Field) 
    {
        this.table2avl1Field = table2avl1Field;
    }

    public Long getTable2avl1Field() 
    {
        return table2avl1Field;
    }
    public void setTable2vo1Field(Long table2vo1Field) 
    {
        this.table2vo1Field = table2vo1Field;
    }

    public Long getTable2vo1Field() 
    {
        return table2vo1Field;
    }
    public void setTable2av1Field(Long table2av1Field) 
    {
        this.table2av1Field = table2av1Field;
    }

    public Long getTable2av1Field() 
    {
        return table2av1Field;
    }
    public void setTable2ri1Field(Long table2ri1Field) 
    {
        this.table2ri1Field = table2ri1Field;
    }

    public Long getTable2ri1Field() 
    {
        return table2ri1Field;
    }
    public void setTable2ro1Field(Long table2ro1Field) 
    {
        this.table2ro1Field = table2ro1Field;
    }

    public Long getTable2ro1Field() 
    {
        return table2ro1Field;
    }
    public void setTable2vo3Field(Long table2vo3Field) 
    {
        this.table2vo3Field = table2vo3Field;
    }

    public Long getTable2vo3Field() 
    {
        return table2vo3Field;
    }
    public void setTable2av3Field(Long table2av3Field) 
    {
        this.table2av3Field = table2av3Field;
    }

    public Long getTable2av3Field() 
    {
        return table2av3Field;
    }
    public void setTable2w1Field(Long table2w1Field) 
    {
        this.table2w1Field = table2w1Field;
    }

    public Long getTable2w1Field() 
    {
        return table2w1Field;
    }
    public void setTable2vs2Field(Long table2vs2Field) 
    {
        this.table2vs2Field = table2vs2Field;
    }

    public Long getTable2vs2Field() 
    {
        return table2vs2Field;
    }
    public void setTable2vol2Field(Long table2vol2Field) 
    {
        this.table2vol2Field = table2vol2Field;
    }

    public Long getTable2vol2Field() 
    {
        return table2vol2Field;
    }
    public void setTable2avl2Field(Long table2avl2Field) 
    {
        this.table2avl2Field = table2avl2Field;
    }

    public Long getTable2avl2Field() 
    {
        return table2avl2Field;
    }
    public void setTable2vo2Field(Long table2vo2Field) 
    {
        this.table2vo2Field = table2vo2Field;
    }

    public Long getTable2vo2Field() 
    {
        return table2vo2Field;
    }
    public void setTable2av2Field(Long table2av2Field) 
    {
        this.table2av2Field = table2av2Field;
    }

    public Long getTable2av2Field() 
    {
        return table2av2Field;
    }
    public void setTable2ri2Field(Long table2ri2Field) 
    {
        this.table2ri2Field = table2ri2Field;
    }

    public Long getTable2ri2Field() 
    {
        return table2ri2Field;
    }
    public void setTable2ro2Field(Long table2ro2Field) 
    {
        this.table2ro2Field = table2ro2Field;
    }

    public Long getTable2ro2Field() 
    {
        return table2ro2Field;
    }
    public void setTable2vo4Field(Long table2vo4Field) 
    {
        this.table2vo4Field = table2vo4Field;
    }

    public Long getTable2vo4Field() 
    {
        return table2vo4Field;
    }
    public void setTable2av4Field(Long table2av4Field) 
    {
        this.table2av4Field = table2av4Field;
    }

    public Long getTable2av4Field() 
    {
        return table2av4Field;
    }
    public void setTable2w2Field(Long table2w2Field) 
    {
        this.table2w2Field = table2w2Field;
    }

    public Long getTable2w2Field() 
    {
        return table2w2Field;
    }
    public void setTable3vc11Field(Long table3vc11Field) 
    {
        this.table3vc11Field = table3vc11Field;
    }

    public Long getTable3vc11Field() 
    {
        return table3vc11Field;
    }
    public void setTable3vc21Field(Long table3vc21Field) 
    {
        this.table3vc21Field = table3vc21Field;
    }

    public Long getTable3vc21Field() 
    {
        return table3vc21Field;
    }
    public void setTable3vb11Field(Long table3vb11Field) 
    {
        this.table3vb11Field = table3vb11Field;
    }

    public Long getTable3vb11Field() 
    {
        return table3vb11Field;
    }
    public void setTable3vb21Field(Long table3vb21Field) 
    {
        this.table3vb21Field = table3vb21Field;
    }

    public Long getTable3vb21Field() 
    {
        return table3vb21Field;
    }
    public void setTable3ve11Field(Long table3ve11Field) 
    {
        this.table3ve11Field = table3ve11Field;
    }

    public Long getTable3ve11Field() 
    {
        return table3ve11Field;
    }
    public void setTable3ve21Field(Long table3ve21Field) 
    {
        this.table3ve21Field = table3ve21Field;
    }

    public Long getTable3ve21Field() 
    {
        return table3ve21Field;
    }
    public void setTable3vc12Field(Long table3vc12Field) 
    {
        this.table3vc12Field = table3vc12Field;
    }

    public Long getTable3vc12Field() 
    {
        return table3vc12Field;
    }
    public void setTable3vc22Field(Long table3vc22Field) 
    {
        this.table3vc22Field = table3vc22Field;
    }

    public Long getTable3vc22Field() 
    {
        return table3vc22Field;
    }
    public void setTable3vb12Field(Long table3vb12Field) 
    {
        this.table3vb12Field = table3vb12Field;
    }

    public Long getTable3vb12Field() 
    {
        return table3vb12Field;
    }
    public void setTable3vb22Field(Long table3vb22Field) 
    {
        this.table3vb22Field = table3vb22Field;
    }

    public Long getTable3vb22Field() 
    {
        return table3vb22Field;
    }
    public void setTable3ve12Field(Long table3ve12Field) 
    {
        this.table3ve12Field = table3ve12Field;
    }

    public Long getTable3ve12Field() 
    {
        return table3ve12Field;
    }
    public void setTable3ve22Field(Long table3ve22Field) 
    {
        this.table3ve22Field = table3ve22Field;
    }

    public Long getTable3ve22Field() 
    {
        return table3ve22Field;
    }
    public void setTable4vo11Field(Long table4vo11Field) 
    {
        this.table4vo11Field = table4vo11Field;
    }

    public Long getTable4vo11Field() 
    {
        return table4vo11Field;
    }
    public void setTable4vo21Field(Long table4vo21Field) 
    {
        this.table4vo21Field = table4vo21Field;
    }

    public Long getTable4vo21Field() 
    {
        return table4vo21Field;
    }
    public void setTable4vo1Field(Long table4vo1Field) 
    {
        this.table4vo1Field = table4vo1Field;
    }

    public Long getTable4vo1Field() 
    {
        return table4vo1Field;
    }
    public void setTable4a1Field(Long table4a1Field) 
    {
        this.table4a1Field = table4a1Field;
    }

    public Long getTable4a1Field() 
    {
        return table4a1Field;
    }
    public void setTable4k1Field(Long table4k1Field) 
    {
        this.table4k1Field = table4k1Field;
    }

    public Long getTable4k1Field() 
    {
        return table4k1Field;
    }
    public void setTable4vo12Field(Long table4vo12Field) 
    {
        this.table4vo12Field = table4vo12Field;
    }

    public Long getTable4vo12Field() 
    {
        return table4vo12Field;
    }
    public void setTable4vo22Field(Long table4vo22Field) 
    {
        this.table4vo22Field = table4vo22Field;
    }

    public Long getTable4vo22Field() 
    {
        return table4vo22Field;
    }
    public void setTable4vo2Field(Long table4vo2Field) 
    {
        this.table4vo2Field = table4vo2Field;
    }

    public Long getTable4vo2Field() 
    {
        return table4vo2Field;
    }
    public void setTable4a2Field(Long table4a2Field) 
    {
        this.table4a2Field = table4a2Field;
    }

    public Long getTable4a2Field() 
    {
        return table4a2Field;
    }
    public void setTable4vo13Field(Long table4vo13Field) 
    {
        this.table4vo13Field = table4vo13Field;
    }

    public Long getTable4vo13Field() 
    {
        return table4vo13Field;
    }
    public void setTable4vo23Field(Long table4vo23Field) 
    {
        this.table4vo23Field = table4vo23Field;
    }

    public Long getTable4vo23Field() 
    {
        return table4vo23Field;
    }
    public void setTable4vo3Field(Long table4vo3Field) 
    {
        this.table4vo3Field = table4vo3Field;
    }

    public Long getTable4vo3Field() 
    {
        return table4vo3Field;
    }
    public void setTable4a3Field(Long table4a3Field) 
    {
        this.table4a3Field = table4a3Field;
    }

    public Long getTable4a3Field() 
    {
        return table4a3Field;
    }
    public void setTable4k2Field(Long table4k2Field) 
    {
        this.table4k2Field = table4k2Field;
    }

    public Long getTable4k2Field() 
    {
        return table4k2Field;
    }
    public void setTable4vo14Field(Long table4vo14Field) 
    {
        this.table4vo14Field = table4vo14Field;
    }

    public Long getTable4vo14Field() 
    {
        return table4vo14Field;
    }
    public void setTable4vo24Field(Long table4vo24Field) 
    {
        this.table4vo24Field = table4vo24Field;
    }

    public Long getTable4vo24Field() 
    {
        return table4vo24Field;
    }
    public void setTable4vo4Field(Long table4vo4Field) 
    {
        this.table4vo4Field = table4vo4Field;
    }

    public Long getTable4vo4Field() 
    {
        return table4vo4Field;
    }
    public void setTable4a4Field(Long table4a4Field) 
    {
        this.table4a4Field = table4a4Field;
    }

    public Long getTable4a4Field() 
    {
        return table4a4Field;
    }
    public void setTable5vi1Field(Long table5vi1Field) 
    {
        this.table5vi1Field = table5vi1Field;
    }

    public Long getTable5vi1Field() 
    {
        return table5vi1Field;
    }
    public void setTable5vo1Field(Long table5vo1Field) 
    {
        this.table5vo1Field = table5vo1Field;
    }

    public Long getTable5vo1Field() 
    {
        return table5vo1Field;
    }
    public void setTable5vi2Field(Long table5vi2Field) 
    {
        this.table5vi2Field = table5vi2Field;
    }

    public Long getTable5vi2Field() 
    {
        return table5vi2Field;
    }
    public void setTable5vo2Field(Long table5vo2Field) 
    {
        this.table5vo2Field = table5vo2Field;
    }

    public Long getTable5vo2Field() 
    {
        return table5vo2Field;
    }
    public void setCurrentText(String currentText) 
    {
        this.currentText = currentText;
    }

    public String getCurrentText() 
    {
        return currentText;
    }
    public void setVi1vo1Image(String vi1vo1Image) 
    {
        this.vi1vo1Image = vi1vo1Image;
    }

    public String getVi1vo1Image() 
    {
        return vi1vo1Image;
    }
    public void setVi1vo2Image(String vi1vo2Image) 
    {
        this.vi1vo2Image = vi1vo2Image;
    }

    public String getVi1vo2Image() 
    {
        return vi1vo2Image;
    }
    public void setVo1vo2Image(String vo1vo2Image) 
    {
        this.vo1vo2Image = vo1vo2Image;
    }

    public String getVo1vo2Image() 
    {
        return vo1vo2Image;
    }
    public void setExperimentScore(Long experimentScore) 
    {
        this.experimentScore = experimentScore;
    }

    public Long getExperimentScore() 
    {
        return experimentScore;
    }
    public void setProgress(String progress) 
    {
        this.progress = progress;
    }

    public String getProgress() 
    {
        return progress;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("studentId", getStudentId())
            .append("table1vc1Field", getTable1vc1Field())
            .append("table1vb1Field", getTable1vb1Field())
            .append("table1ve1Field", getTable1ve1Field())
            .append("table1vc2Field", getTable1vc2Field())
            .append("table1vb2Field", getTable1vb2Field())
            .append("table1ve2Field", getTable1ve2Field())
            .append("table2vs1Field", getTable2vs1Field())
            .append("table2vol1Field", getTable2vol1Field())
            .append("table2avl1Field", getTable2avl1Field())
            .append("table2vo1Field", getTable2vo1Field())
            .append("table2av1Field", getTable2av1Field())
            .append("table2ri1Field", getTable2ri1Field())
            .append("table2ro1Field", getTable2ro1Field())
            .append("table2vo3Field", getTable2vo3Field())
            .append("table2av3Field", getTable2av3Field())
            .append("table2w1Field", getTable2w1Field())
            .append("table2vs2Field", getTable2vs2Field())
            .append("table2vol2Field", getTable2vol2Field())
            .append("table2avl2Field", getTable2avl2Field())
            .append("table2vo2Field", getTable2vo2Field())
            .append("table2av2Field", getTable2av2Field())
            .append("table2ri2Field", getTable2ri2Field())
            .append("table2ro2Field", getTable2ro2Field())
            .append("table2vo4Field", getTable2vo4Field())
            .append("table2av4Field", getTable2av4Field())
            .append("table2w2Field", getTable2w2Field())
            .append("table3vc11Field", getTable3vc11Field())
            .append("table3vc21Field", getTable3vc21Field())
            .append("table3vb11Field", getTable3vb11Field())
            .append("table3vb21Field", getTable3vb21Field())
            .append("table3ve11Field", getTable3ve11Field())
            .append("table3ve21Field", getTable3ve21Field())
            .append("table3vc12Field", getTable3vc12Field())
            .append("table3vc22Field", getTable3vc22Field())
            .append("table3vb12Field", getTable3vb12Field())
            .append("table3vb22Field", getTable3vb22Field())
            .append("table3ve12Field", getTable3ve12Field())
            .append("table3ve22Field", getTable3ve22Field())
            .append("table4vo11Field", getTable4vo11Field())
            .append("table4vo21Field", getTable4vo21Field())
            .append("table4vo1Field", getTable4vo1Field())
            .append("table4a1Field", getTable4a1Field())
            .append("table4k1Field", getTable4k1Field())
            .append("table4vo12Field", getTable4vo12Field())
            .append("table4vo22Field", getTable4vo22Field())
            .append("table4vo2Field", getTable4vo2Field())
            .append("table4a2Field", getTable4a2Field())
            .append("table4vo13Field", getTable4vo13Field())
            .append("table4vo23Field", getTable4vo23Field())
            .append("table4vo3Field", getTable4vo3Field())
            .append("table4a3Field", getTable4a3Field())
            .append("table4k2Field", getTable4k2Field())
            .append("table4vo14Field", getTable4vo14Field())
            .append("table4vo24Field", getTable4vo24Field())
            .append("table4vo4Field", getTable4vo4Field())
            .append("table4a4Field", getTable4a4Field())
            .append("table5vi1Field", getTable5vi1Field())
            .append("table5vo1Field", getTable5vo1Field())
            .append("table5vi2Field", getTable5vi2Field())
            .append("table5vo2Field", getTable5vo2Field())
            .append("currentText", getCurrentText())
            .append("vi1vo1Image", getVi1vo1Image())
            .append("updateTime", getUpdateTime())
            .append("vi1vo2Image", getVi1vo2Image())
            .append("vo1vo2Image", getVo1vo2Image())
            .append("experimentScore", getExperimentScore())
            .append("createTime", getCreateTime())
            .append("progress", getProgress())
            .toString();
    }
}
