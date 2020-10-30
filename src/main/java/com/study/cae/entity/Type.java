package com.study.cae.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description 类型表
 * @author: suicaijiao
 * @create: 2020-05-23 08:30
 **/
@Getter
@Setter
public class Type {

    /**
     * 系统编号，自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 是否删除，0：未删除；1：已删除
     */
    private Integer isDelete;

    /**
     * 修改时间
     */
    private Date sysModified;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sysCreate;

    /**
     * SEO描述
     */
    private String description;

    /**
     * 访问地址
     */
    private String url;

    /**
     * 根节点id
     */
    private Integer rootId;

    /**
     * 是否为多内容
     */
    private Integer isMulti;


}
