package com.dkd.quartz.task;

import org.springframework.stereotype.Component;
import com.dkd.common.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时任务调度测试
 * 
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask
{
    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams()
    {
        System.out.println("执行无参方11法");
    }
    private static final String DB_USER = "root"; // 数据库用户名
    private static final String DB_PASS = "hl.123123"; // 数据库密码
    private static final String DB_NAME = "bookms"; // 数据库名称
    private static final String BACKUP_DIR = "D:\\1project\\bookMS\\backup"; // 备份目录路径
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd_HHmmss");

    public static void main(String[] args) {
        backupTables();
    }

    public static void backupTables() {
        try {
            // 获取当前日期时间作为文件名的一部分
            String timestamp = DATE_FORMAT.format(new Date());

            // 定义要备份的表
            String[] tables = {"tb_class", "tb_student", "tb_instructor"};

            for (String table : tables) {
                String filename = BACKUP_DIR + File.separator + table + "_" + timestamp + ".sql";
                File file = new File(filename);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                // 构建 mysqldump 命令
                ProcessBuilder pb = new ProcessBuilder(
                        "mysqldump",
                        "-u" + DB_USER,
                        "-p" + DB_PASS,
                        DB_NAME,
                        table
                );
                pb.redirectOutput(file); // 将输出重定向到文件

                // 启动进程并等待完成
                Process process = pb.start();
                int exitCode = process.waitFor();

                if (exitCode == 0) {
                    System.out.println("成功备份表 " + table + " 到 " + filename);
                } else {
                    System.err.println("备份表 " + table + " 失败");
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
