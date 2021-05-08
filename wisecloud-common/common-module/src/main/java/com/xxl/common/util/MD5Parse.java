package com.xxl.common.util;


import com.xxl.common.enums.CommonConsts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5处理类
 * @author xiaohui
 * @version 2016年10月29日
 * @see MD5Parse
 * @since
 */
public class MD5Parse
{	
	protected final static Logger logger = LogManager.getLogger(MD5Parse.class);
    /**
     * 字符串MD5加密
     * @param str 字符串
     * @return MD5值
     * @see
     */
    public static String parseStr2md5(String str)
    {
        byte[] resultByteArray = null;
        byte[] inputByteArray = null;
        try
        {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            inputByteArray = str.getBytes();
            messageDigest.update(inputByteArray);
            resultByteArray = messageDigest.digest();
        }
        catch (NoSuchAlgorithmException e)
        {
            logger.error(CommonConsts.LOG_ERROR_STR, StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
        }
        return byteArrayToHex(resultByteArray);
    }

    private static String byteArrayToHex(byte[] byteArray)
    {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
            'E', 'F'};
        char[] resultCharArray = new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray)
        {
            resultCharArray[index++ ] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++ ] = hexDigits[b & 0xf];
        }
        return new String(resultCharArray);
    }
}
