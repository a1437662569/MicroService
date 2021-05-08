package com.xxl.util;

import com.xxl.common.util.MD5Parse;
import com.xxl.common.util.StringUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * token处理类
 *
 * @author xiahsunhui
 * @version 2017年05月16日
 * @see TokenUtil
 * @since
 */
public class TokenUtil {
    // 简单点，先用map缓存
    private static ConcurrentHashMap<String, Map<String, Long>> tokenMap = new ConcurrentHashMap<>();

    /**
     * 校验token是否有效
     *
     * @param name
     * @param token
     * @return
     */
//    public static boolean validToken(String name, String token,Long aliveTimeMillis) {
    public static boolean validToken(String name, String token, Long aliveTimeMillis) {
        if (StringUtil.isEmpty(name) || StringUtil.isEmpty(token)) {
            return false;
        }
        if (tokenMap.get(name) != null && tokenMap.get(name).get(token) != null) {
            //
            long lasterTime = tokenMap.get(name).get(token);

            long nowTime = System.currentTimeMillis();

            tokenMap.remove(name);

            if ((nowTime - lasterTime) > (aliveTimeMillis)) {
                return false;
            } else // 更新 token时间
            {
                Map<String, Long> timeMap = new HashMap<String, Long>();

                timeMap.put(token, nowTime);

                tokenMap.put(name, timeMap);

                return true;
            }
        }
        return false;
    }

    /**
     * 调用登出接口时清掉缓存
     *
     * @param name
     */
    public static void removeToken(String name) {
        if (!StringUtil.isEmpty(name)) {
            tokenMap.remove(name);
        }

    }


    /**
     * 生成token，并存放到内存当中
     *
     * @param name
     * @param password
     * @return
     */
    public static String createToken(String name, String password) {
        String token = MD5Parse.parseStr2md5(name + password + new Date().getTime());

        Map<String, Long> timeMap = new HashMap<String, Long>();

        timeMap.put(token, System.currentTimeMillis());

        tokenMap.put(name, timeMap);

        return token;
    }


}
