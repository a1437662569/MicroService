package com.xxl.common.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;

import java.util.Date;
import java.util.regex.Pattern;

public class DateUtil {
    /**
     * 一天的毫秒数
     */
    public static final long MILLISECOND_DAY = 0x5265c00L;

    /**
     * 日期时分秒.SSS
     */
    public static final String YYYYMMDDHHMMSSSSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String YYYYMMDDHHMMSSSSS_ZONE = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

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

    public String now2String() {
        return DateTime.now().toString(YYYYMMDDHHMMSS);
    }

    public String now2String(String formatStr) {
        return DateTime.now().toString(formatStr);
    }

    public String date2String(Date date, String formatStr) {
        return new DateTime(date).toString(formatStr);
    }

    public String date2String(DateTime date, String formatStr) {
        return date.toString(formatStr);
    }


}
