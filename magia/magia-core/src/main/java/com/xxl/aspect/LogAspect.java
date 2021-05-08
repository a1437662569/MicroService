package com.xxl.aspect;

import com.alibaba.fastjson.JSON;
import com.xxl.bo.LogBO;
import com.xxl.bo.LogDataBO;
import com.xxl.common.api.R;
import com.xxl.common.enums.CommonConsts;
import com.xxl.common.util.JsonUtil;
import com.xxl.common.util.LogUtils;
import com.xxl.common.util.ParamUtil;
import com.xxl.common.util.StringUtil;
import com.xxl.util.ThreeDES;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 切面的目的：
 * 1、部分接口请求参数和响应参数中的data里面的数据需要做加密
 * 2、部分接口请求参数和响应参数的数据都不需要加密
 * 3、所有接口的请求日志都需要通过明文的方式打印出来
 * <p>
 * 实现的思路：
 * 1、将特殊不需要加密的接口单独做处理，其他的全部默认走加密方式
 * 2、打印日志的时候，也要做特殊处理将加密的数据解密
 * 3、返回的时候，也需要做特殊处理将加密的接口里面的data里面的数据加密
 */

@Aspect
@Slf4j
@Component
public class LogAspect {
    private final List<String> ACTIVE_URI = Stream.of("/DeviceManager/device/check.status", "/DeviceManager/device/check.action").collect(Collectors.toList());


    @Pointcut("execution(* com.xxl.controller.*.*(..))")
    public void onlineController() {
    }

    @Around("onlineController()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        long startTime = System.currentTimeMillis();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        Object[] args = pjp.getArgs();
        String stringData = "";
        // result的值就是被拦截方法的返回值
        Object result = null;
        try {
            /**
             * 不需要走加密传输的接口，这里直接放行
             */
            if ("/Magia/test".equals(uri)) {
                result = pjp.proceed();
            }
            // 如果是GET和DELETE请求，不需要走加解密
            else if (ParamUtil.URL_METHOND_LIST.contains(method)) {
                result = pjp.proceed();
            }
            /**
             * 需要做加密的接口，需要先将请求参数解密以后，然后再放行
             */
            else {
                // 获取请求报文
                String src = (String) pjp.getArgs()[0];
                // 解密
                stringData = ThreeDES.decryptThreeDESECB(src, CommonConsts.DEFAULT_KEY);
                // 解密后放行
                Object[] resultArgs = new Object[]{stringData};
                result = pjp.proceed(resultArgs);
            }
        } catch (Exception e) {
            result = CommonConsts.JSON_SYSTEM_ERROR;
            log.error("报错接口:{},报错信息:{}", request.getRequestURI(), StringUtil.printExcetion(e));
        }

        /**
         * 这里主要是组装日志格式，将相关参数保存到日志里面去
         * [{"ip":"{}","executeTime":"{}","uri":"{}","action":"{}","sn":"{}","desSn":"{}","appId":"{}","appVersion":"{}","appLanguage":"{}","data":{"request":{},"response":{}}}]
         */
        Map<String, String> headMap = new HashMap<String, String>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            headMap.put(key, value);
        }
        LogBO logBO = LogBO.builder()
                .ip(ParamUtil.getIpAddr(request))
                .executeTime(System.currentTimeMillis() - startTime)
                .uri(uri)
                .build();

        /**
         * 这里就是要将请求参数和响应参数在日志里面打印出来了
         */
        try {
            //获取请求参数集合并进行遍历拼接
            Map<String, Object> requestMap = new LinkedHashMap<>();
            if (ParamUtil.BODY_METHOND_LIST.contains(method) && args.length > 0) {
                if ("/Magia/test".equals(uri)) {
                    Object object = pjp.getArgs()[0];
                    requestMap.putAll(JSON.parseObject(JSON.toJSONString(object)));
                } else {
                    requestMap.putAll(JSON.parseObject(stringData));
                }
            } else if (ParamUtil.URL_METHOND_LIST.contains(method) && StringUtil.isNotEmpty(queryString)) {
                requestMap.putAll(Arrays.stream(queryString.split("&")).map(s -> s.split("=")).collect(Collectors.toMap(s -> s[0], s -> s.length == 2 ? s[1] : null)));
                requestMap.put("urlParams", queryString);
            }
            requestMap.put("requestHeaders", headMap);
            LogDataBO logDataBO = LogDataBO.builder()
                    .request(requestMap)
                    .response(result)
                    .build();
            logBO.setData(logDataBO);
            log.info("[{}]", JSON.toJSONString(logBO));
            LogUtils.getBussinessLogger().info("[{}]", JSON.toJSONString(logBO));
        } catch (Exception e) {
            log.error(StringUtil.printExcetion(e));
        }

        /**
         * 打印完日志后，如果是不需要加密的接口，直接返回业务逻辑里面的result
         */
        if ("/Magia/test".equals(uri)) {
            return result;
        } else if (ParamUtil.URL_METHOND_LIST.contains(method)) {
            return result;
        }
        /**
         * 打印完日志以后，如果是需要加密的接口，还需要将业务逻辑里面的result里面的data里面的数据进行加密，然后再返回
         */
        else {
            R jsonRsp = (R) JsonUtil.writeJSON2Object((String) result,
                    R.class);
            R rResult = new R();
            BeanUtils.copyProperties(jsonRsp, rResult);
            if (null != jsonRsp.getData()) {
                rResult.setData(ThreeDES.encryptThreeDESECB(
                        JsonUtil.writeObject2JSON(jsonRsp.getData()), CommonConsts.DEFAULT_KEY));
            }
            return JsonUtil.writeObject2JSON(rResult);
        }
    }


}
