package com.xxl.service;

import com.xxl.common.api.R;
import com.xxl.entity.po.GetUserInfoRsp;
import com.xxl.entity.po.LoginRsp;
import com.xxl.entity.vo.*;


public interface IMagiaService {

    /**
     * @param getEmailVerifyCodeReq 获取邮箱验证码请求参数
     * @return 获取验证码的结果
     */
    R getEmailVerifyCodeService(GetEmailVerifyCodeReq getEmailVerifyCodeReq);

    /**
     * @param registerReq 注册的请求参数
     * @return 注册的结果
     */
    R registerService(RegisterReq registerReq);

    /**
     * @param emailResetReq 邮箱账号找回密码接口的请求参数
     * @return 重置密码的结果
     */
    R emailResetService(EmailResetReq emailResetReq);

    /**
     * @param loginReq 登录的请求参数
     * @return 登录的最终结果
     */
    R<LoginRsp> loginService(LoginReq loginReq);

    /**
     * @param passwordResetReq 重置密码的请求参数
     * @return 重置密码的结果
     */
    R passwordResetService(PasswordResetReq passwordResetReq);

    /**
     * @param getUserInfoReq 查询用户基本信息的请求参数
     * @return 查询结果
     */
    R<GetUserInfoRsp> getUserInfoService(GetUserInfoReq getUserInfoReq);

    /**
     * @param joinReq 加入免费试用的请求参数
     * @return 加入免费试用的结果
     */
    R joinService(JoinReq joinReq);

    /**
     * @param sendEmailReq 手动发送邮件的请求参数信息
     * @return 发送结果
     */
    R sendEmailService(SendEmailReq sendEmailReq);
}
