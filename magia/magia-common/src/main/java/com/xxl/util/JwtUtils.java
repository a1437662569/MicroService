package com.xxl.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xxl.common.api.R;
import com.xxl.common.enums.CommonConsts;
import com.xxl.entity.Account;

import java.util.Calendar;
import java.util.Date;

/**
 * 实现JWT的工具类，magia中不打算使用原始的session或者token的方式，而是直接使用JWT的方式
 * 来实现认证
 */
public class JwtUtils {

    /**
     * 签发对象：这个用户的id
     * 签发时间：现在
     * 有效时间：12小时
     * 载荷内容：暂时设计为：这个人的名字，这个人的昵称
     * 加密密钥：这个人的id加上一串字符串
     */
    public static String createToken(Account account) {
        // token是需要有失效时间的，暂时设置为12个小时
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.HOUR_OF_DAY, 12);
        Date expiresDate = nowTime.getTime();
        // 生成JWT的时候，将用户的userId保存进去了，其他的信息后续如果需要保存的话可以再添加
        // 其中秘钥是一定要保密的
        return JWT.create().withAudience(account.getUserId().toString())
                .withIssuedAt(new Date())    //发行时间
                .withExpiresAt(expiresDate)  //有效时间
                .sign(Algorithm.HMAC256(CommonConsts.DEFAULT_KEY));
    }

    /**
     * 校验token的合法性
     */
    public static R verifyToken(String token) {
        R result = R.ok();
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(CommonConsts.DEFAULT_KEY)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            //效验失败
            //这里抛出的异常是我自定义的一个异常，你也可以写成别的
            result = R.TOKEN_ERROR();
        }
        return result;
    }

    /**
     * 获取签发对象
     */
    public static String getAudience(String token) throws Exception {
        String audience = null;
        try {
            audience = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            //这里是token解析失败
            throw new Exception();
        }
        return audience;
    }


    /**
     * 通过载荷名字获取载荷的值
     */
    public static Claim getClaimByName(String token, String name) {
        return JWT.decode(token).getClaim(name);
    }


    public static void main(String[] args) {
        Account account = new Account();
        account.setUserId(10000);

        String token2 = JwtUtils.createToken(account);
        System.out.println(token2);

    }
}

