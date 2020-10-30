package com.study.cae.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @author: suicaijiao
 * @create: 2020-05-24 10:37
 **/
@Getter
@Setter
public class TypeTreeVo {

    /**
     * 系统编号，自增主键
     */
    private Integer id;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 父节点id
     */
    private Integer parentId;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态 0-启用 1-禁用
     */
    private Integer status;

    /**
     * 下级目录集合
     */
    private List<TypeTreeVo> childrenList = new ArrayList<>();

}
