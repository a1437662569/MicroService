package com.xxl.jwt;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.xxl.common.api.R;
import com.xxl.common.enums.CommonConsts;
import com.xxl.service.IMagiaService;
import com.xxl.util.JwtUtils;
import com.xxl.util.PassToken;
import com.xxl.util.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import com.auth0.jwt.JWT;

/**
 * 这个拦截器的主要作用是为了做JWT的认证
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    IMagiaService magiaService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader(CommonConsts.PARAM_USER_TOKEN);
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();

        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            // 如果是使用了@UserLoginToken注解的，那么需要开始做JWT校验了
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    returnErrorMessage(httpServletResponse, R.TOKEN_ERROR());
                    return false;
                }
                // 获取 token 中的 user id
//                String userId;
//                try {
//                    userId = JWT.decode(token).getAudience().get(0);
//                } catch (JWTDecodeException j) {
//                    throw new RuntimeException("401");
//                }

                R checkTokenResult = JwtUtils.verifyToken(token);
                if (!CommonConsts.NUMBER_ZERO_STR.equals(checkTokenResult.getReturnCode())) {
                    returnErrorMessage(httpServletResponse, checkTokenResult);
                    return false;
                }
                return true;
            }
        }
        return true;
    }

    private void returnErrorMessage(HttpServletResponse response, R result) throws Exception {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(result);
        out.flush();
    }

}