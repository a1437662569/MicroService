package com.xxl.common.exception;

import com.xxl.common.api.R;
import com.xxl.common.enums.APICodeMsgEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice(basePackages = "com.xxl.*")
public class ControllerParamValidatedException {

    @Value("${paramValidErrorDataFlag:false}")
    private boolean errorDataFlag;

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R hand(MethodArgumentNotValidException e) {
        List<FieldError> fieldError = e.getBindingResult().getFieldErrors();
        Map<String, String> collect = fieldError.stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        log.error("Controller请求参数检验失败,类详情:{},错误详情:{}", e.getParameter().getMethod().toString(), collect);
        return R.builder().returnCode(APICodeMsgEnum.PARAM_ERROR.getCode()).errorMessage(APICodeMsgEnum.PARAM_ERROR.getMsg()).data(errorDataFlag ? collect : null).build();
    }
}
