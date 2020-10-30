package com.study.cae.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @description
 * @author: suicaijiao
 * @create: 2020-05-24 14:09
 **/
@Getter
@Setter
public class QueryTypeVo {

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
     * 父节点名称
     */
    private String parentName;

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
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date sysCreate;

    /**
     * 修改时间
     */
    private Date sysModified;

}
