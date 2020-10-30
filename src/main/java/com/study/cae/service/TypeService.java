package com.study.cae.service;

import com.study.cae.dto.OperationTypeDto;
import com.study.cae.entity.Type;

import java.util.List;
import java.util.Map;

/**
 * @description
 * @author: suicaijiao
 * @create: 2020-05-23 08:50
 **/
public interface TypeService {


    /**
     * 新增类别
     *
     * @param type
     * @return
     */
//    boolean insertType(OperationTypeDto type);

    /**
     * 查询实体
     *
     * @param id
     * @return
     */
    Type selectByPrimaryKey(Integer id);

    /**
     * 修改
     *
     * @param type
     * @return
     */
    boolean updateById(OperationTypeDto type);

    /**
     * 根据父节点查询类别层级区分
     *
     * @param parentId
     * @return
     */
    List<Map<String, Object>> listToTreeByParentId(Integer parentId);

    /**
     * 返回所有类别层级区分
     *
     * @return
     */
    List<Map<String, Object>> listToTree();

    /**
     * 单条删除
     *
     * @param id
     * @return
     */
    boolean deleteByPrimaryKey(Integer id);

    /**
     * 修改状态
     *
     * @param id
     * @return
     */
    boolean updateStatusById(Integer id);

    /**
     * 根据 父节点id查询
     *
     * @param parentId
     * @return
     */
    List<Type> selectByParentId(Integer parentId);

    /**
     * 根据名称查询
     *
     * @param typeName
     * @return
     */
    Type getByTypeName(String typeName);

    /**
     * 读取本地导航信息
     *
     * @return
     */
    List getNavFileList();


}
