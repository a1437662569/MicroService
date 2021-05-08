package com.xxl.controller;

import com.xxl.common.api.R;
import com.xxl.common.enums.CommonConsts;
import com.xxl.common.util.HttpUtil;
import com.xxl.common.util.JsonUtil;
import com.xxl.common.util.StringUtil;
import com.xxl.entity.po.GetUserInfoRsp;
import com.xxl.entity.po.LoginRsp;
import com.xxl.entity.vo.*;
import com.xxl.service.*;
import com.xxl.util.UserLoginToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(CommonConsts.MAGIA)
@Slf4j
public class MagiaController {

    @Autowired
    RedisService redisService;

    @Autowired
    IMagiaService magiaService;

    @Autowired
    private HttpServletRequest request;


    /**
     * @param reqStr 获取验证码的请求参数
     * @return 发送验证码的结果
     */
    @PostMapping(value = "/v1/getEmailVerifyCode", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getEmailVerifyCodeCon(@RequestBody String reqStr) {
        // 标识开始时间
        String returnStr = null;
        String ipAddr = null;
        GetEmailVerifyCodeReq getEmailVerifyCodeReq = null;
        try {
            getEmailVerifyCodeReq = (GetEmailVerifyCodeReq) JsonUtil.writeJSON2Object(reqStr, GetEmailVerifyCodeReq.class);
            ipAddr = HttpUtil.getIpAddr(request);
            getEmailVerifyCodeReq.setIpAddr(ipAddr);
            R result = magiaService.getEmailVerifyCodeService(getEmailVerifyCodeReq);
            returnStr = JsonUtil.writeObject2JSON(result);
        } catch (Exception e) {
            log.error(StringUtil.printExcetion(e));
        }
        return returnStr;
    }

    /**
     * @param reqStr 注册的请求参数
     * @return 注册的结果
     */
    @PostMapping(value = "/v1/register", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String registerCon(@RequestBody String reqStr) {
        // 标识开始时间
        String returnStr = null;
        String ipAddr = null;
        RegisterReq registerReq = null;
        try {
            registerReq = (RegisterReq) JsonUtil.writeJSON2Object(reqStr, RegisterReq.class);
            ipAddr = HttpUtil.getIpAddr(request);
            registerReq.setIpAddr(ipAddr);
            R result = magiaService.registerService(registerReq);
            returnStr = JsonUtil.writeObject2JSON(result);
        } catch (Exception e) {
            log.error(StringUtil.printExcetion(e));
        }
        return returnStr;
    }


    /**
     * @param reqStr 邮箱找回密码的请求参数
     * @return 重置密码的结果
     */
    @PostMapping(value = "/v1/emailReset", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String emailResetCon(@RequestBody String reqStr) {
        // 标识开始时间
        String returnStr = null;
        String ipAddr = null;
        EmailResetReq emailResetReq = null;
        try {
            emailResetReq = (EmailResetReq) JsonUtil.writeJSON2Object(reqStr, EmailResetReq.class);
            ipAddr = HttpUtil.getIpAddr(request);
            emailResetReq.setIpAddr(ipAddr);
            R result = magiaService.emailResetService(emailResetReq);
            returnStr = JsonUtil.writeObject2JSON(result);
        } catch (Exception e) {
            log.error(StringUtil.printExcetion(e));
        }
        return returnStr;
    }

    /**
     * @param reqStr 登录接口请求参数
     * @return 返回登录结果
     */
    @PostMapping(value = "/v1/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String loginCon(@RequestBody String reqStr) {
        // 标识开始时间
        String returnStr = null;
        String ipAddr = null;
        LoginReq loginReq = null;
        try {
            loginReq = (LoginReq) JsonUtil.writeJSON2Object(reqStr, LoginReq.class);
            ipAddr = HttpUtil.getIpAddr(request);
            loginReq.setIpAddr(ipAddr);
            R<LoginRsp> result = magiaService.loginService(loginReq);
            returnStr = JsonUtil.writeObject2JSON(result);
        } catch (Exception e) {
            log.error(StringUtil.printExcetion(e));
        }
        return returnStr;
    }

    /**
     * @param reqStr 重置密码的请求参数
     * @return 重置密码的结果
     */
    @UserLoginToken
    @PostMapping(value = "/v1/passwordReset", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String passwordResetCon(@RequestBody String reqStr) {
        // 标识开始时间
        String returnStr = null;
        String ipAddr = null;
        PasswordResetReq passwordResetReq = null;
        try {
            passwordResetReq = (PasswordResetReq) JsonUtil.writeJSON2Object(reqStr, PasswordResetReq.class);
            ipAddr = HttpUtil.getIpAddr(request);
            passwordResetReq.setIpAddr(ipAddr);
            R result = magiaService.passwordResetService(passwordResetReq);
            returnStr = JsonUtil.writeObject2JSON(result);
        } catch (Exception e) {
            log.error(StringUtil.printExcetion(e));
        }
        return returnStr;
    }


    /**
     * @param reqStr 获取用户详情请求参数
     * @return 用户的基本详情信息
     */
    @UserLoginToken
    @PostMapping(value = "/v1/getUserInfo", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getUserInfoCon(@RequestBody String reqStr) {
        // 标识开始时间
        String returnStr = null;
        String ipAddr = null;
        GetUserInfoReq getUserInfoReq = null;
        try {
            getUserInfoReq = (GetUserInfoReq) JsonUtil.writeJSON2Object(reqStr, GetUserInfoReq.class);
            ipAddr = HttpUtil.getIpAddr(request);
            getUserInfoReq.setIpAddr(ipAddr);
            R<GetUserInfoRsp> result = magiaService.getUserInfoService(getUserInfoReq);
            returnStr = JsonUtil.writeObject2JSON(result);
        } catch (Exception e) {
            log.error(StringUtil.printExcetion(e));
        }
        return returnStr;
    }

    /**
     * @param reqStr 加入免费试用的请求参数
     * @return 假如免费试用的结果
     */
    @UserLoginToken
    @PostMapping(value = "/v1/join", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String joinCon(@RequestBody String reqStr) {
        // 标识开始时间
        String returnStr = null;
        String ipAddr = null;
        JoinReq joinReq = null;
        try {
            joinReq = (JoinReq) JsonUtil.writeJSON2Object(reqStr, JoinReq.class);
            ipAddr = HttpUtil.getIpAddr(request);
            joinReq.setIpAddr(ipAddr);
            R result = magiaService.joinService(joinReq);
            returnStr = JsonUtil.writeObject2JSON(result);
        } catch (Exception e) {
            log.error(StringUtil.printExcetion(e));
        }
        return returnStr;
    }


    @PostMapping(value = "/sendEmail", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String sendEmailCon(@RequestBody String reqStr) {
        // 标识开始时间
        String returnStr = null;
        String ipAddr = null;
        SendEmailReq sendEmailReq = null;
        try {
            sendEmailReq = (SendEmailReq) JsonUtil.writeJSON2Object(reqStr, SendEmailReq.class);
            ipAddr = HttpUtil.getIpAddr(request);
            sendEmailReq.setIpAddr(ipAddr);
            R result = magiaService.sendEmailService(sendEmailReq);
            returnStr = JsonUtil.writeObject2JSON(result);
        } catch (Exception e) {
            log.error(StringUtil.printExcetion(e));
        }
        return returnStr;
    }

}
