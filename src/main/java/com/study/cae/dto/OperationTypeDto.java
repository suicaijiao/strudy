package com.study.cae.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @description
 * @author: suicaijiao
 * @create: 2020-05-24 09:14
 **/
@ApiModel(value = "新增类别", description = "类别信息 ")
@Getter
@Setter
public class OperationTypeDto {

    /**
     * id
     */
    @ApiModelProperty(value = "编号", example = "编号")
    private Integer id;


    @ApiModelProperty(value = "启用状态", example = "状态 0-启用 1-禁用")
    private Integer status;


    @ApiModelProperty(value = "SEO描述", example = "网站相关信息")
    private String description;

    private String typeName;

    private String rootId = "";
}
