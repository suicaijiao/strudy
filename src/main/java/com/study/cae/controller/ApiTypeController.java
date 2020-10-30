package com.study.cae.controller;

import com.study.cae.commons.global.result.ResponseResult;
import com.study.cae.commons.global.result.ResultCode;
import com.study.cae.dto.OperationTypeDto;
import com.study.cae.entity.Type;
import com.study.cae.service.TypeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @description
 * @author: suicaijiao
 * @create: 2020-05-30 15:35
 **/
@RestController
@RequestMapping("api/type")
public class ApiTypeController {

    @Autowired
    private TypeService typeService;

    /**
     * 根据父节点id查询
     *
     * @return
     */
    @GetMapping("/nav")
    public ResponseResult selectByParentId() {
        List result = typeService.getNavFileList();
        return ResponseResult.success(result);
    }

    @GetMapping("/{id}")
    public ResponseResult selectById(@PathVariable Integer id) {
        Type type = typeService.selectByPrimaryKey(id);
        return ResponseResult.success(type);
    }


    /**
     * 根据父节点id查询树结构
     *
     * @param parentId
     * @return
     */
    @GetMapping("/{parentId}/tree")
    public ResponseResult selectByParentIdTree(@PathVariable("parentId") Integer parentId) {
        List<Map<String, Object>> result = typeService.listToTreeByParentId(parentId);
        return ResponseResult.success(result);
    }


    /**
     * 根据typeName查询
     *
     * @param typeName
     * @return
     */
    @GetMapping("/type-name")
    public ResponseResult getByTypeName(String typeName) {
        if (StringUtils.isBlank(typeName)) {
            return ResponseResult.failed(ResultCode.SYS_COM_PARAM_NULL_FAILED, "请求参数不允许为空");
        }
        Type result = typeService.getByTypeName(typeName);
        return ResponseResult.success(result);
    }

    @ApiResponses({
            @ApiResponse(code = 1000, message = "系统异常"),
            @ApiResponse(code = 1030, message = "请求参数不正确"),
            @ApiResponse(code = 1031, message = "请求参数为空")
    })
    @ApiOperation(value = "修改类别")
    @PutMapping
    public ResponseResult updateType(@Validated() @RequestBody OperationTypeDto typeDto) {
        if (typeDto.getId() == null) {
            return ResponseResult.failed(ResultCode.SYS_COM_PARAM_NULL_FAILED, "请求参数错误");
        }
        boolean result = typeService.updateById(typeDto);
        if (result) {
            return ResponseResult.success(result);
        }
        return ResponseResult.failed("修改失败");
    }


}
