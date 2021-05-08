/*
 * 文件名：LogUtils.java 版权：©Copyright by www.sowell-tech.cn 描述： 修改人：xiaohui 修改时间：2016年11月7日 修改内容：
 */
package com.xxl.util;

import com.xxl.common.enums.CommonConsts;
import org.apache.logging.log4j.Level;

/**
 * 日志类处理对象
 * 
 * @author xiaohui
 * @version 2016年11月7日
 * @see LogUtils
 * @since
 */
public class LogUtils
{
    /**
     * 获取BIZ日志对象 使用方法.0：logger.log(LogUtils.returnBizLevel(), "a message");
     * 
     * @return 日志对象
     * @see
     */
    public static Level returnBizLevel()
    {
        Level level = Level.getLevel(CommonConsts.BIZ);

        if (null == level)
        {
            // 自定义一个BIZ日志类型，优先级350
            level = Level.forName(CommonConsts.BIZ, 350);
        }

        return level;
    }
}
