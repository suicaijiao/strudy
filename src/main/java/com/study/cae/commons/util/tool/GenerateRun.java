package com.study.cae.commons.util.tool;

import java.io.File;
import java.io.IOException;

/**   
* @Description:调用所有执行方法
* @author suicaijiao  
* @date 2019年6月14日  
*/
public class GenerateRun {

	public static void main(String[] args) throws IOException {
		String tableName = "users";
		int subNum = 0;
		String packageName="com.community.cae";
		// 生成实体类
		String entityName = GenerateEntity.getEntityFile(tableName, subNum,packageName);
		// 生成DAO类
		GenerateMapper.getMapperFile(tableName, subNum,packageName);
		// 生成service类
		GenerateService.getServiceFile(tableName, subNum,packageName);
		// 生成serviceImpl类
		GenerateServiceImpl.getServiceImpFile(tableName, subNum,packageName);
		// 生成mpper.xml
		GenerateMybatis.getMybatisFile(tableName, subNum,packageName);
		
		System.out.println("生成成功");
		
		java.awt.Desktop.getDesktop().open(new File("D:\\"+entityName));
		
	}
}
