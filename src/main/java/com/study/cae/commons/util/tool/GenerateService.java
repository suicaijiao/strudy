package com.study.cae.commons.util.tool;

import com.study.cae.commons.util.tool.util.CreateFile;
import com.study.cae.commons.util.tool.util.MySQLTableComment;

/**
 * @author suicaijiao
 * @Description:
 * @date 2019年6月14日
 */
public class GenerateService {

    /**
     * 生成service
     *
     * @param tableName   数据库表名
     * @param subNum      截取前缀位数
     * @param packageName 报名去掉package的包名称（com.example.demo）
     */
    public static void getServiceFile(String tableName, int subNum, String packageName) {
        String oldName = MySQLTableComment.replaceUnderlineAndfirstToUpper(tableName.substring(subNum), "_");
        String entityClassName = MySQLTableComment.firstCharacterToUpper(oldName);
        String dirName = "D:/" + entityClassName + "/service";// 创建目录
        CreateFile.createDir(dirName);// 调用方法创建目录
        String fileName = dirName + "/" + entityClassName + "Service.java";// 创建文件
        CreateFile.createFile(fileName);// 调用方法创建文件

        // 查询主键名称(数据库名)
        String keyNameDb = MySQLTableComment.getPrimaryKeyName(tableName).split(",")[0];
        String keyNameEntity = MySQLTableComment.replaceUnderlineAndfirstTo(keyNameDb, "_");

        // 查询主键类型(数据库名)
        String keyType = MySQLTableComment.getPrimaryKeyName(tableName).split(",")[1];

        StringBuilder builder = new StringBuilder();
        builder.append("package " + packageName + ".service;" + "\r\n\r\n");
        builder.append("import java.util.List;" + "\r\n\r\n");
        builder.append("import " + packageName + ".entity." + entityClassName + ";\r\n\r\n");

        builder.append("public interface " + entityClassName + "Service {" + "\r\n\r\n");

        builder.append("	public int insert(" + entityClassName + " " + oldName + ");" + "\r\n\r\n");

        builder.append("	public int updateByPrimaryKey(" + entityClassName + " " + oldName + ");" + "\r\n\r\n");
        if ("varchar(32)".equalsIgnoreCase(keyType)) {
            builder.append("	public int deleteByPrimaryKey(String " + keyNameEntity + ");" + "\r\n\r\n");
            builder.append("	public " + entityClassName + " selectByPrimaryKey(String " + keyNameEntity + ");" + "\r\n\r\n");
        } else if ("bigint".equalsIgnoreCase(keyType) || "bigint(20) unsigned".equalsIgnoreCase(keyType)) {
            builder.append("	public int deleteByPrimaryKey(Long " + keyNameEntity + ");" + "\r\n\r\n");
            builder.append("	public " + entityClassName + " selectByPrimaryKey(Long " + keyNameEntity + ");" + "\r\n\r\n");
        } else {
            builder.append("	public int deleteByPrimaryKey(Integer " + keyNameEntity + ");" + "\r\n\r\n");
            builder.append("	public " + entityClassName + " selectByPrimaryKey(Integer " + keyNameEntity + ");" + "\r\n\r\n");
        }

        builder.append("	public List<" + entityClassName + "> selectAll(" + entityClassName + " " + oldName + ");" + "\r\n\r\n");

        builder.append("\r\n}");

        String str = CreateFile.fileLinesWrite(fileName, builder.toString(), false);

        System.out.println("创建成功：" + str + fileName);
    }

}
