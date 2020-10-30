/**
 *
 */
package com.study.cae.commons.util.tool;

import com.study.cae.commons.util.tool.util.CreateFile;
import com.study.cae.commons.util.tool.util.MySQLTableComment;

import java.util.Map;

/**
 * @Description:
 * @author suicaijiao
 * @date 2019年4月25日
 */
public class GenerateMybatis {

    /**
     * 生成mpper.xml
     * @param tableName 数据库表名
     * @param subNum    截取前缀位数
     */
    public static void getMybatisFile(String tableName, int subNum, String packageName) {
        //获得表名
        String oldName = MySQLTableComment.replaceUnderlineAndfirstToUpper(tableName.substring(subNum), "_");
        String entityClassName = MySQLTableComment.firstCharacterToUpper(oldName);
        // 查询主键名称(数据库名)
        String keyNameDb = MySQLTableComment.getPrimaryKeyName(tableName).split(",")[0];
        String keyNameEntity = MySQLTableComment.replaceUnderlineAndfirstTo(keyNameDb, "_");

        // 查询主键类型(数据库名)
        String keyType = MySQLTableComment.getPrimaryKeyName(tableName).split(",")[1];


        String dirName = "D:/" + entityClassName + "/mybatis";// 创建目录
        CreateFile.createDir(dirName);// 调用方法创建目录
        String fileName = dirName + "/" + entityClassName + "Mapper.xml";// 创建文件
        CreateFile.createFile(fileName);// 调用方法创建文件

        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
        builder.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\r\n");
        builder.append("<mapper namespace=\"" + packageName + ".mapper." + entityClassName + "Mapper\">\r\n\r\n");
        builder.append("	<resultMap id=\"BaseResultMap\" type=\"" + packageName + ".entity." + entityClassName + "\">\r\n");
        //写入文件内容 replaceUnderlineAndfirstTo
        Map<String, String> tablesTypeInfo = MySQLTableComment.getColumnTypes(tableName);
        // 生成resultMap
        for (String key : tablesTypeInfo.keySet()) {
            String entityCol = MySQLTableComment.replaceUnderlineAndfirstTo(key, "_");
            builder.append("		<result property=\"" + entityCol + "\" column=\"" + key + "\" />\r\n");
        }
        builder.append("	</resultMap>\r\n\r\n");

        // 生成where条件
        builder.append("	<sql id=\"dynamicWhere\">\r\n");
        builder.append("		<trim suffixOverrides=\"and\">\r\n");
        for (String key : tablesTypeInfo.keySet()) {
            String value = tablesTypeInfo.get(key);
            String entityType = MySQLTableComment.sqlType2JavaType(value);
            String entityCol = MySQLTableComment.replaceUnderlineAndfirstTo(key, "_");
            if ("String".equals(entityType)) {
                builder.append("			<if test=\"" + entityCol + " != null and " + entityCol + " != '' \">\r\n");
            } else {
                builder.append("			<if test=\"" + entityCol + " != null \">\r\n");
            }
            builder.append("				AND " + tableName + "." + key + " = #{" + entityCol + "}\r\n");
            builder.append("			</if>\r\n");
        }
        builder.append("		</trim>\r\n");
        builder.append("	</sql>\r\n\r\n");
        // 生成查询字段
        builder.append("	<sql id=\"queryColumns\">\r\n");
        int size = tablesTypeInfo.size();
        int index = 0;
        for (String key : tablesTypeInfo.keySet()) {
            index++;
            if (size != index) {
                builder.append("			" + tableName + "." + key + " ,\r\n");
            } else {
                builder.append("			" + tableName + "." + key + "\r\n");
            }
        }
        builder.append("	</sql>\r\n\r\n");

        builder.append("	<!-- 新增数据 -->\r\n");
        builder.append("	<insert id=\"insert\" parameterType=\"" + packageName + ".entity." + entityClassName + "\">\r\n");
        builder.append("		INSERT INTO\r\n");
        builder.append("			" + tableName + " (\r\n");
        int insertIndex = 0;
        // 生成数据库字段
        for (String key : tablesTypeInfo.keySet()) {
            insertIndex++;
            if (size != insertIndex) {
                builder.append("				" + key + " ,\r\n");
            } else {
                builder.append("				" + key + "\r\n");
            }
        }
        builder.append("			) VALUES (\r\n");
        // 生成实体字段
        int insertEntieyIndex = 0;
        for (String key : tablesTypeInfo.keySet()) {
            insertEntieyIndex++;
            String entityCol = MySQLTableComment.replaceUnderlineAndfirstTo(key, "_");
            if (size != insertEntieyIndex) {
                builder.append("				#{" + entityCol + "} ,\r\n");
            } else {
                builder.append("				#{" + entityCol + "}\r\n");
            }
        }
        builder.append(" 			)\r\n");
        if ("varchar(32)".equalsIgnoreCase(keyType)) {
            builder.append("		<selectKey resultType=\"java.lang.String\" keyProperty=\"" + keyNameEntity + "\">\r\n");
        } else if ("bigint".equalsIgnoreCase(keyType) || "bigint(20) unsigned".equalsIgnoreCase(keyType)) {
            builder.append("		<selectKey resultType=\"java.lang.Long\" keyProperty=\"" + keyNameEntity + "\">\r\n");
        } else {
            builder.append("		<selectKey resultType=\"java.lang.Integer\" keyProperty=\"" + keyNameEntity + "\">\r\n");
        }

        builder.append("			SELECT last_insert_id();\r\n");
        builder.append("		</selectKey>\r\n");
        builder.append("	</insert>\r\n\r\n");
        // 生成修改
        builder.append("	<!-- 修改数据 -->\r\n");
        builder.append("	<update id=\"updateByPrimaryKey\" parameterType=\"" + packageName + ".entity." + entityClassName + "\">\r\n");
        builder.append("		UPDATE " + tableName + "\r\n");
        builder.append("		<trim prefix=\"set\" suffixOverrides=\",\">\r\n");
        for (String key : tablesTypeInfo.keySet()) {
            String value = tablesTypeInfo.get(key);
            String entityType = MySQLTableComment.sqlType2JavaType(value);
            String entityCol = MySQLTableComment.replaceUnderlineAndfirstTo(key, "_");
            if ("String".equals(entityType)) {
                builder.append("			<if test=\"" + entityCol + " != null and " + entityCol + " != '' \">\r\n");
            } else {
                builder.append("			<if test=\"" + entityCol + " != null \">\r\n");
            }

            builder.append("				" + key + " = #{" + entityCol + "},\r\n");

            builder.append("			</if>\r\n");
        }
        builder.append("		</trim>\r\n");
        builder.append("		WHERE " + keyNameDb + " = #{" + keyNameEntity + "}\r\n");
        builder.append("	</update>\r\n\r\n");

        // 生成查询list
        builder.append("	<!-- 查询数据集合 -->\r\n");
        builder.append("	<select id=\"selectAll\" parameterType=\"" + packageName + ".entity." + entityClassName + "\" resultMap=\"BaseResultMap\" resultType=\"java.util.List\">\r\n");
        builder.append("			SELECT\r\n");
        builder.append("		<include refid=\"queryColumns\" />\r\n");
        builder.append("			FROM " + tableName + " WHERE 1=1\r\n");
        builder.append("		<include refid=\"dynamicWhere\" />\r\n");
        builder.append("	</select>\r\n\r\n");

        // 根据id查询单个实体
        builder.append("	<!-- 查询单条数据 -->\r\n");
        if ("varchar(32)".equalsIgnoreCase(keyType)) {
            builder.append("	<select id=\"selectByPrimaryKey\" resultMap=\"BaseResultMap\" parameterType=\"java.lang.String\" resultType=\"" + packageName + ".entity." + entityClassName + "\">\r\n");
        } else if ("bigint".equalsIgnoreCase(keyType) || "bigint(20) unsigned".equalsIgnoreCase(keyType)) {
            builder.append("	<select id=\"selectByPrimaryKey\" resultMap=\"BaseResultMap\" parameterType=\"java.lang.Long\" resultType=\"" + packageName + ".entity." + entityClassName + "\">\r\n");
        } else {
            builder.append("	<select id=\"selectByPrimaryKey\" resultMap=\"BaseResultMap\" parameterType=\"java.lang.Integer\" resultType=\"" + packageName + ".entity." + entityClassName + "\">\r\n");
        }
        builder.append("		SELECT\r\n");
        builder.append("		<include refid=\"queryColumns\" />\r\n");
        builder.append("		FROM " + tableName + " WHERE " + keyNameDb + " = #{" + keyNameEntity + "}\r\n");
        builder.append("	</select>\r\n\r\n");

        // 生成删除
        builder.append("	<!-- 删除数据 -->\r\n");
        if ("varchar(32)".equalsIgnoreCase(keyType)) {
            builder.append("	<delete id=\"deleteByPrimaryKey\" parameterType=\"java.lang.String\">\r\n");
        } else if ("bigint".equalsIgnoreCase(keyType) || "bigint(20) unsigned".equalsIgnoreCase(keyType)) {
            builder.append("	<delete id=\"deleteByPrimaryKey\" parameterType=\"java.lang.Long\">\r\n");
        } else {
            builder.append("	<delete id=\"deleteByPrimaryKey\" parameterType=\"java.lang.Integer\">\r\n");
        }
        builder.append("		DELETE FROM \r\n");
        builder.append("		" + tableName + "\r\n			WHERE\r\n");
        builder.append("		" + keyNameDb + " = #{" + keyNameEntity + "}\r\n");
        builder.append("	</delete>\r\n\r\n");

        builder.append("</mapper>");
        String str = CreateFile.fileLinesWrite(fileName, builder.toString(), false);
        if ("write".equals(str)) {
            System.out.println("创建成功：" + fileName);
        }
    }

    public static void main(String[] args) {
        String tableName = "oauth_user";
        int subNum = 0;
        String packageName = "com.open.web.ecprojectappresourcemanagement.services.user.domain.entity";
        // 生成mpper.xml
        GenerateMybatis.getMybatisFile(tableName, subNum, packageName);
    }
}
