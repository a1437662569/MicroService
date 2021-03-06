package com.xxl.util;


import com.xxl.common.enums.CommonConsts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 日期处理功能类
 * 
 * @author xiaohui
 * @version 2016年10月29日
 * @see DateUtil
 * @since
 */
public final class DateUtil
{
	protected final static Logger logger = LogManager.getLogger(DateUtil.class);
    /**
     * 一天的毫秒数
     */
    public static final long MILLISECOND_DAY = 0x5265c00L;

    /**
     * 日期时分秒.SSS
     */
    public static final String YYYYMMDDHHMMSSSSS = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 年-月-日 时:分:秒
     */
    public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 年月日时分秒
     */
    public static final String YYYYMMDDHHMMSS_SHORT = "yyyyMMddHHmmss";

    /**
     * 时间格式
     */
    public static final String HHMMSS = "HH:mm:ss";

    /**
     * 小时
     */
    public static final String HH = "HH";

    /**
     * 小时：分钟
     */
    public static final String HHMM = "HH:mm";

    /**
     * 小时分钟
     */
    public static final String HHMM_SHORT = "HHmm";

    /**
     * 年-月-日 小时
     */
    public static final String YYYYMMDDHH = "yyyy-MM-dd HH";

    /**
     * 年月日小时
     */
    public static final String YYYYMMDDHH_SHORT = "yyyyMMddHH";

    /**
     * 年月日小时分钟
     */
    public static final String YYYYMMDDHHMM_SHORT = "yyyyMMddHHmm";

    /**
     * 年月
     */
    public static final String YYYYMM = "yyyyMM";

    /**
     * 年月日
     */
    public static final String YYYYMMDD_SHORT = "yyyyMMdd";

    /**
     * 年-月-日
     */
    public static final String YYYYMMDD = "yyyy-MM-dd";

    /**
     * 年-月-日 时：分：秒 格式校验
     */
    public static final String YYYYMMDDHHMMSS_REGEXP = "^(?x:(?:[0-9]{1,4}(?<!^0?0?0?0))-(?:0?[1-9]|1[0-2])-(?:0?[1-9]|1[0-9]|2[0-8]|(?:(?<=-(?:0?[13578]|1[02])-)(?:29|3[01]))|(?:(?<=-(?:0?[469]|11)-)(?:29|30))|(?:(?<=(?:(?:[0-9]{0,2}(?!0?0)(?:[02468]?(?<![13579])[048]|[13579][26]))|(?:(?:[02468]?[048]|[13579][26])00))-0?2-)(?:29))))\\s(((0?[0-9])|([1][0-9])|([2][0-3]))\\:([0-5]?[0-9])((\\:([0-5]?[0-9]))))$";

    /**
     * 年-月-日 格式校验
     */
    public static final String YYYYMMDD_REGEXP = "^(?x:(?:[0-9]{1,4}(?<!^0?0?0?0))-(?:0?[1-9]|1[0-2])-(?:0?[1-9]|1[0-9]|2[0-8]|(?:(?<=-(?:0?[13578]|1[02])-)(?:29|3[01]))|(?:(?<=-(?:0?[469]|11)-)(?:29|30))|(?:(?<=(?:(?:[0-9]{0,2}(?!0?0)(?:[02468]?(?<![13579])[048]|[13579][26]))|(?:(?:[02468]?[048]|[13579][26])00))-0?2-)(?:29))))";

    /**
     * yyyy-MM-dd'T'HH:mm:ss'Z' 格式
     */
    public static final String UTC_SHORT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     * UTC时间格式校验
     */
    public static final Pattern UTCPattern = Pattern.compile(
        "(?x:(?:[0-9]{1,4}(?<!^0?0?0?0))-(?:0?[1-9]|1[0-2])-(?:0?[1-9]|1[0-9]|2[0-8]|(?:(?<=-(?:0?[13578]|1[02])-)(?:29|3[01]))|(?:(?<=-(?:0?[469]|11)-)(?:29|30))|(?:(?<=(?:(?:[0-9]{0,2}(?!0?0)(?:[02468]?(?<![13579])[048]|[13579][26]))|(?:(?:[02468]?[048]|[13579][26])00))-0?2-)(?:29))))T(((0?[0-9])|([1][0-9])|([2][0-3])):([0-5]?[0-9])((:([0-5]?[0-9]))))((\\.([0-9]{1,3})))Z");

    /**
     * 时：分：秒 格式校验
     */
    public static final String HHMMSS_REGEXP = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";

    public DateUtil()
    {}

    /**
     * 获取日期毫秒数
     * 
     * @param date
     * @return
     * @see
     */
    public static Date getDateMinus(int date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(5, calendar.get(5) - date);
        return calendar.getTime();
    }

    /**
     * 获取指定格式系统日期
     * 
     * @param formatStr
     *            格式
     * @return
     * @see
     */
    public static String getSysDate(String formatStr)
    {
        String dateStr = null;
        try
        {
            SimpleDateFormat sfTemp = new SimpleDateFormat(formatStr);
            dateStr = sfTemp.format(new Date());
        }
        catch (Exception e)
        {
            logger.error(CommonConsts.LOG_ERROR_STR, StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
        }

        return dateStr;
    }

    /**
     * 获取指定格式日期新增秒后的日期
     * 
     * @param formatStr
     * @param future
     * @return
     * @see
     */
    public static String getDate4future(String formatStr, Long future)
    {
        String dateStr = null;
        try
        {
            Date date = new Date(System.currentTimeMillis() + future.longValue() * 1000L);
            SimpleDateFormat sfTemp = new SimpleDateFormat(formatStr);
            dateStr = sfTemp.format(date);
        }
        catch (Exception e)
        {
            logger.error(CommonConsts.LOG_ERROR_STR, StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
        }

        return dateStr;
    }

    /**
     * 获取指定格式和日期的格式化后的日期
     * 
     * @param formatStr
     *            格式
     * @param date
     *            日期
     * @return 格式化后的日期
     * @see
     */
    public static String getSysDate(String formatStr, Date date)
    {
        String dateStr = null;
        try
        {
            SimpleDateFormat sfTemp = new SimpleDateFormat(formatStr);
            dateStr = sfTemp.format(date);
        }
        catch (Exception e)
        {
            logger.error(CommonConsts.LOG_ERROR_STR, StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
        }

        return dateStr;
    }

    /**
     * 日期时间大小比较
     * 
     * @param beginTime
     *            开始时间
     * @param endTime
     *            结束时间
     * @param formatStr
     *            格式
     * @return 开始时间>=结束时间的结果
     * @see
     */
    public static boolean dateComparison(String beginTime, String endTime, String formatStr)
    {
        if (beginTime == null || endTime == null)
        {
            return false;
        }
        if (format(beginTime, formatStr).getTime() >= format(endTime, formatStr).getTime())
        {
            return true;
        }

        return false;
    }

    /**
     * 日期比较
     * 
     * @param beginTime
     *            开始时间
     * @param endTime
     *            结束时间
     * @return 开始时间>=结束时间的结果
     * @see
     */
    public static boolean dateComparison(Date beginTime, Date endTime)
    {
        if (beginTime == null || endTime == null)
        {
            return false;
        }
        if (beginTime.getTime() >= endTime.getTime())
        {
            return true;
        }

        return false;
    }

    /**
     * 格式化日期，指定格式格式化2次
     * 
     * @param dateStr
     *            日期字符串
     * @param formatDate
     *            格式1
     * @param formatStr
     *            格式2
     * @return 格式化后
     * @see
     */
    public static String formatStr(String dateStr, String formatDate, String formatStr)
    {
        Date dd = format(dateStr, formatDate);
        String str = formatter(dd, formatStr);
        return str;
    }

    /**
     * 将日期字符串指定格式再格式化
     * 
     * @param dateStr
     *            日期字符串
     * @param formatStr
     *            格式
     * @return 格式化结果
     * @see
     */
    public static Date format(String dateStr, String formatStr)
    {
        SimpleDateFormat sf = null;
        Date dd = null;
        try
        {
            sf = new SimpleDateFormat(formatStr);
            sf.setLenient(false);
            dd = sf.parse(dateStr);
        }
        catch (Exception e)
        {
            dd = null;
        }
        return dd;
    }

    /**
     * 将日期字符串转换成日历格式
     * 
     * @param s
     *            日期字符串YYYY-MM-DD HH:mm:ss
     * @return 转换后的对象
     * @see
     */
    public static Calendar toCalendar(String s)
    {
        Calendar c = getStartOf(Calendar.getInstance());
        c.set(Integer.parseInt(s.substring(0, 4)), Integer.parseInt(s.substring(5, 7)) - 1,
            Integer.parseInt(s.substring(8, 10)), Integer.parseInt(s.substring(11, 13)),
            Integer.parseInt(s.substring(14, 16)), Integer.parseInt(s.substring(17, 19)));
        return c;
    }

    /**
     * 获取年月日格式日历对象
     * 
     * @param calendar
     *            日历对象
     * @return 返回处理后的对象
     * @see
     */
    public static Calendar getStartOf(Calendar calendar)
    {
        return new GregorianCalendar(calendar.get(1), calendar.get(2), calendar.get(5));
    }

    /**
     * 初始化一个日历对象
     * 
     * @return 返回一个初始化的日历对象
     * @see
     */
    public static Calendar getCurrentDateTime()
    {
        return Calendar.getInstance();
    }

    /**
     * 校验日期格式
     * 
     * @param date
     *            日期
     * @param pattern
     *            检验对象
     * @return 检验结果
     * @see
     */
    public static boolean isMatch(String date, Pattern pattern)
    {
        if (date == null || "".equals(date))
            return false;
        else
            return pattern.matcher(date).matches();
    }

    /**
     * 获取一个指定日期减去指定月数，时分秒都为0的日期
     * 
     * @param m
     *            需要减去的月份
     * @param date
     *            需要处理的日期
     * @return 处理结果
     * @see
     */
    public static Date getDate(int m, Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(1), calendar.get(2) - m, calendar.get(5), 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * 获取指定累加月的日期
     * 
     * @param m
     *            指定的月数
     * @param date
     *            指定的时间
     * @return 处理结果
     * @see
     */
    public static Date getLastDate(int m, Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(2, m);
        return calendar.getTime();
    }

    /**
     * 返回一个指定天的日期
     * 
     * @param m
     *            天数
     * @param date
     *            指定日期
     * @return 返回处理结果
     * @see
     */
    public static Date getDayOffDate(int m, Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, m);
        return calendar.getTime();
    }

    /**
     * 获取一个指定日期，时间为0的日期
     * 
     * @param date
     *            日期
     * @return 处理后的结果
     * @see
     */
    public static Date getDate(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(1), calendar.get(2), calendar.get(5), 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * 获取一个指定日期累加指定天数后的日期
     * 
     * @param date
     *            日期
     * @param num
     *            天数
     * @return 处理结果
     * @see
     */
    public static Date getDate(Date date, int num)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(1), calendar.get(2), calendar.get(5) + num, 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * 返回一个UTC时间转换格式后的日期
     * 
     * @param dateStr
     *            UTC时间
     * @param formatStr
     *            格式
     * @return 处理结果
     * @see
     */
    public static Date getDateByUTC(String dateStr, String formatStr)
    {
        SimpleDateFormat sf = null;
        Date dd = null;
        try
        {
            sf = new SimpleDateFormat(formatStr);
            sf.setLenient(false);
            dd = sf.parse(dateStr);
            dd.setTime(dd.getTime() + getZoneOffset());
        }
        catch (Exception e)
        {
            dd = null;
        }
        return dd;
    }

    /**
     * 获取时区
     * 
     * @return
     * @see
     */
    public static long getZoneOffset()
    {
        Calendar cal = Calendar.getInstance();
        int zoneOffset = cal.get(15);
        int dstOffset = cal.get(16);
        return (long)(zoneOffset + dstOffset);
    }

    /**
     * 获取一个时间的小时数
     * 
     * @param time
     *            时间
     * @return 返回小时数
     * @see
     */
    public static String getHourTime(String time)
    {
        Pattern p = Pattern.compile(
            "^(?x:(?:[0-9]{1,4}(?<!^0?0?0?0))-(?:0?[1-9]|1[0-2])-(?:0?[1-9]|1[0-9]|2[0-8]|(?:(?<=-(?:0?[13578]|1[02])-)(?:29|3[01]))|(?:(?<=-(?:0?[469]|11)-)(?:29|30))|(?:(?<=(?:(?:[0-9]{0,2}(?!0?0)(?:[02468]?(?<![13579])[048]|[13579][26]))|(?:(?:[02468]?[048]|[13579][26])00))-0?2-)(?:29))))\\s(((0?[0-9])|([1][0-9])|([2][0-3]))\\:([0-5]?[0-9])((\\:([0-5]?[0-9]))))$");
        Matcher m = p.matcher(time);
        if (m.matches())
        {
            Pattern pattern = Pattern.compile("\\s");
            String strs[] = pattern.split(time);
            String temp = null;
            try
            {
                temp = strs[1];
            }
            catch (ArrayIndexOutOfBoundsException e)
            {}
            return temp;
        }
        else
        {
            return null;
        }
    }

    /**
     * 对比两个时间都大于第一个时间
     * 
     * @param firstTime
     *            第一个时间
     * @param hourTime
     *            第二个时间
     * @param hourTime2
     *            第三个时间
     * @return
     * @see
     */
    public static boolean dateIsBetWeen(String firstTime, String hourTime, String hourTime2)
    {
        return dateComparison(firstTime, hourTime, "HH:mm:ss")
               && dateComparison(hourTime2, firstTime, "HH:mm:ss");
    }

    /**
     * 获取当前时间cal.add(11, -1);
     * 
     * @return
     * @see
     */
    public static Calendar getCurrentTimeB4AnHour()
    {
        Calendar cal = Calendar.getInstance();
        cal.add(11, -1);
        return cal;
    }

    /**
     * 将制定时分秒格式字符串转换成秒
     * 
     * @param time
     *            时间
     * @return 转换后结果
     * @see
     */
    public static Long transformHourSecond(String time)
    {
        Long millisecond = Long.valueOf(0L);
        if (null == time) return millisecond;
        Pattern p = Pattern.compile("^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$");
        Matcher m = p.matcher(time);
        if (!m.matches())
        {
            return millisecond;
        }
        else
        {
            String times[] = time.split(":");
            millisecond = Long.valueOf(
                (Long.parseLong(times[0]) * 3600L + Long.parseLong(times[1]) * 60L
                 + Long.parseLong(times[2])) * 1000L);
            return millisecond;
        }
    }

    /**
     * 将指定时间毫秒数转换成yyyyMMddHHmmss格式
     * 
     * @param time
     *            时间毫秒数
     * @return 转换后的结果
     * @see
     */
    public static String getTimeString(long time)
    {
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(date);
    }

    /**
     * 返回指定日期的时间字符串
     * 
     * @param date
     *            日期
     * @return 处理后的结果HH:mm:ss
     * @see
     */
    public static String getTimeString(Date date)
    {
        String dateStr = null;
        try
        {
            dateStr = formatter(date, "yyyy-MM-dd HH:mm:ss");
            dateStr = dateStr.substring(11, 19);
        }
        catch (Exception e)
        {
            logger.error(CommonConsts.LOG_ERROR_STR, StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
        }

        return dateStr;
    }

    /**
     * 返回指定日期和格式的字符串
     * 
     * @param date
     *            日期
     * @param format
     *            格式
     * @return
     * @see
     */
    public static String formatter(Date date, String format)
    {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(date);
    }
    
	public static String getFilePath() {
		DateFormat df = uploadFormatter.get();
		if (df == null) {
			df = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss/");
			uploadFormatter.set(df);
		}
		return df.format(new Date());
	}

	private static ThreadLocal<DateFormat> uploadFormatter = new ThreadLocal<DateFormat>();
}
