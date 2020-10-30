package com.study.cae.commons.exception;

import com.study.cae.commons.exception.entity.BusinessException;
import com.study.cae.commons.global.result.ResponseResult;
import com.study.cae.commons.global.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 通过java原生的@Valid注解和spring的@ControllerAdvice和@ExceptionHandler实现全局异常处理
 *
 * @author suicaijioa
 * @date 22020-05-23 08:50
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理所有业务的异常
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseResult bindBusinessExceptionHandler(HttpServletRequest request, BusinessException e) {
        //是否记录错误详情
        if (e.getCause() == null) {
            log.info(getLoggerMessage(request, e));
        } else {
            log.info(getLoggerMessage(request, e), e.getCause());
        }

        //没有错误代码的，默认一个错误代码
        if (StringUtils.isBlank(e.getBizCode())) {
            return ResponseResult.failed(ResultCode.SYS_COM_UNKONW, e.getMessage());
        }
        return ResponseResult.failed(e.getBizCode(), e.getMessage());
    }

    /**
     * 处理接口数据验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult bindExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasErrors()) {
            log.info(getLoggerMessage(request, e));

            List<ObjectError> errors = bindingResult.getAllErrors();
            if (CollectionUtils.isNotEmpty(errors))//400请求参数不正确
            {
                StringBuilder errorMessage = new StringBuilder();
                for (ObjectError objectError : errors) {
                    errorMessage.append(objectError.getDefaultMessage()).append(";");
                }
                return ResponseResult.failed(ResultCode.SYS_COM_PARAM_ERROR_FAILED, errorMessage.toString());
            }
        }
        return null;
    }

    /**
     * 处理接口数据验证异常
     */
    @ExceptionHandler(value = {MissingServletRequestParameterException.class, BindException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseResult bindArgumentExceptionHandler(HttpServletRequest request, Exception e) {
        log.info(getLoggerMessage(request, e));
        return ResponseResult.failed(ResultCode.SYS_COM_PARAM_ERROR_FAILED);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseResult bindArgumentTypeExceptionHandler(HttpServletRequest request, MethodArgumentTypeMismatchException e) {
        log.info(getLoggerMessage(request, e));
        return ResponseResult.failed(ResultCode.SYS_COM_PARAM_ERROR_FAILED);
    }

    /**
     * 处理未知的异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult bindExceptionHandler(HttpServletRequest request, Exception e) {
        log.error(getLoggerMessage(request, e), e);
        return ResponseResult.failed();
    }

    /**
     * 返回格式化错误消息 [异常类型] [请求地址] [异常消息]
     */
    private String getLoggerMessage(HttpServletRequest request, Exception e) {
        return String.format("[%s] [%s] [%s]", e.getClass().getSimpleName(), request.getRequestURI(), e.getMessage());
    }
}