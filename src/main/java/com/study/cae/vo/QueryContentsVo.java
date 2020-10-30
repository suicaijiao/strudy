package com.study.cae.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @description
 * @author: suicaijiao
 * @create: 2020-05-28 13:53
 **/
@Getter
@Setter
public class QueryContentsVo {

    /**
     * 系统编号，自增主键
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 标题图片路径
     */
    private String picturePath;

    /**
     * 新闻内容
     */
    private String content;

    /**
     * 类型id
     */
    private Integer typeId;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 状态 0-启用 1-禁用
     */
    private Integer status;

    /**
     * 新闻浏览次数
     */
    private Integer viewedCount;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 内容外链网址
     */
    private String keyword;

    /**
     * 创建时间
     */
    private Date sysCreate;

    /**
     * 修改时间
     */
    private Date sysModified;

    /**
     * 摘要
     */
    private String excerpts;

    /**
     * 创建人
     */
    private String created;
}
