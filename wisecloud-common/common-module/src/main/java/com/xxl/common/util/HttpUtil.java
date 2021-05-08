package com.xxl.common.util;


import com.xxl.common.enums.CommonConsts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * http处理通用类
 * 
 * @author xiaohui
 * @version 2016年10月29日
 * @see HttpUtil
 * @since
 */
public class HttpUtil
{
	protected final static Logger logger = LogManager.getLogger(HttpUtil.class);
    /**
     * 获取请求端的IP地址
     * 
     * @param request
     *            request对象
     * @return IP地址
     * @see
     */
    public static String getIpAddr(HttpServletRequest request)
    {
    	String ip = request.getHeader("Cf-Connecting-Ip");
		if (null == ip || 0 == ip.length() || CommonConsts.UNKNOWN.equalsIgnoreCase(ip))
	    {
	        ip = request.getHeader("x-forwarded-for");
	    }
        if (null == ip || 0 == ip.length() || CommonConsts.UNKNOWN.equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (null == ip || 0 == ip.length() || CommonConsts.UNKNOWN.equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (null == ip || 0 == ip.length() || CommonConsts.UNKNOWN.equalsIgnoreCase(ip))
        {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (null == ip || 0 == ip.length() || CommonConsts.UNKNOWN.equalsIgnoreCase(ip))
        {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (null == ip || 0 == ip.length() || CommonConsts.UNKNOWN.equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }
        if (null == ip || 0 == ip.length() || CommonConsts.UNKNOWN.equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();

            // 本地IP处理
            if ("127.0.0.1".equals(ip))
            {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try
                {
                    inet = InetAddress.getLocalHost();
                }
                catch (UnknownHostException e)
                {
                    logger.error(CommonConsts.LOG_ERROR_STR, StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
                }

                if (null != inet)
                {
                    ip = inet.getHostAddress();
                }
            }
        }

        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割 15是IP长度
        if (null != ip && 15 < ip.length())
        {
            int comma = ip.indexOf(',');
            if (comma > 0)
            {
                ip = ip.substring(0, comma);
            }
        }

        return ip;
    }
}
