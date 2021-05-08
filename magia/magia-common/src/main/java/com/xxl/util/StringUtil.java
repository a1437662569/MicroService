package com.xxl.util;


import com.xxl.common.enums.CommonConsts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串处理通用类
 *
 * @author xiaohui
 * @version 2016年10月29日
 * @see StringUtil
 * @since
 */
public final class StringUtil {
    /**
     * 日志
     */
    private final static Logger logger = LogManager.getLogger(StringUtil.class);

    /**
     * 数字常量
     */
    public static final int NUMBER = 1;

    public static final int TEXT = 2;

    public static final int CHAR = 3;

    public static final int REGEXP = 4;

    public static final int DATE = 5;

    public static final int DOUBLE = 6;

    public static final int NUM_AND_CHAR = 7;

    public static final int CHAR_TWO = 8;

    public static final int NOT_CHECK_LEN = -1;

    /**
     * 小数占位格式
     */
    public static final String FORMAT_STYLE = "0.###";

    public static final String FORMAT_STYLE_LEN_TWO = "0.##";

    /**
     * 查询限制
     */
    public static final String LIMITQUERY = "20";

    /**
     * 数字格式校验
     */
    public static final String RATIONAL_NUMBERS_REGEXP = "^(-?\\d+)(\\.\\d+)?$";

    /**
     * key
     */
    public static final String ThreeDesKey = "2b494e53756c664c2f44465245733572";

    public static final String HuaWeiPassWordDesKey = "2b494e53756c664c2f44465245733572";

    /**
     * 180000
     */
    public static final long TOKENTIME = 0x1b7740L;

    public static final long HEARTIME = 60000L;

    public static final String SOURCE_SYSTEM = "HQ";

    /**
     * 字母+数字校验
     */
    public static final String NUMBERS_AND_CHAR_REGEXP = "^[A-Za-z0-9]+$";

    /**
     * 字母+数字校验
     */
    public static final String LEGALCHAR_REGEXP = "^\\w+$";

    public StringUtil() {
    }

    /**
     * 校验是否数字或字母
     *
     * @param str 需要校验的字符串
     * @return 校验结果
     * @see
     */
    public static boolean isNumAndChar(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }

        Pattern pattern = Pattern.compile("^[A-Za-z0-9]+$");
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    /**
     * 根据指定数字拼接加0到指定位数
     *
     * @param index  数字
     * @param maxLen 最大长度
     * @return 处理结果
     * @see
     */
    public static String getIndexStr(int index, int maxLen) {
        String indexStr = String.valueOf(index);
        for (int len = indexStr.length(); len < maxLen; len++) {
            indexStr = (new StringBuilder()).append("0").append(indexStr).toString();
        }

        return indexStr;
    }

    /**
     * 判断字符串是否小数
     *
     * @param str 字符串
     * @return 检验结果
     * @see
     */
    public static boolean isDouble(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }

        Pattern pattern = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    /**
     * 判断字符串是否数字
     *
     * @param str 字符串
     * @return 校验结果
     * @see
     */
    public static boolean isInteger(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }

        Pattern pattern = Pattern.compile("(^(0|[1-9]\\d*)$|^(-([1-9]\\d*))$)");
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    /**
     * 校验字符串是否是由字母和数字组成
     *
     * @param str 字符串
     * @return 校验结果
     * @see
     */
    public static boolean isLegalChar(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }

        Pattern pattern = Pattern.compile("^\\w+$");
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    /**
     * 校验是否中文
     *
     * @param str 字符串
     * @return 校验结果
     * @see
     */
    public static boolean isChina(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }

        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]+");
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    /**
     * 校验字符串是否equals
     *
     * @param str
     * @param str1
     * @return
     * @see
     */
    public static boolean equals(String str, String str1) {
        return getString(str).equals(getString(str1));
    }

    /**
     * 获取一个null 转换成空字符串
     *
     * @param str 字符串
     * @return 转换结果
     * @see
     */
    public static String getString(String str) {
        return null != str ? str : "";
    }

    /**
     * 将double转换成字符串
     *
     * @param str 字符串
     * @return 转换结果
     * @see
     */
    public static String getString(Double str) {
        return null != str ? String.valueOf(str) : "";
    }

    /**
     * 将long转换成字符串
     *
     * @param str 字符串
     * @return 转换结果
     * @see
     */
    public static String getString(Long str) {
        return null != str ? String.valueOf(str) : "";
    }

    /**
     * 将integer转换成数字
     *
     * @param str 字符串
     * @return 转换结果
     * @see
     */
    public static String getString(Integer str) {
        return null != str ? String.valueOf(str) : "";
    }

    /**
     * 将字符串中的标签转成 转义字符
     *
     * @param origine 字符串
     * @return 转义后的字符串
     * @see
     */
    public static String convert2Html(String origine) {
        String outStr = null;
        if (null != origine) {
            String tmp = replace(origine, ">", "&gt;");
            String tmp2 = replace(tmp, "<", "&lt;");
            String tmp3 = replace(tmp2, " ", "&nbsp;");
            String tmp4 = replace(tmp3, "\r\n", "<br>");
            outStr = tmp4;
        } else {
            outStr = "";
        }
        return outStr;
    }

    /**
     * 将指定字符串中的字符进行替换
     *
     * @param str 字符串
     * @param old 需要替换的字符
     * @param rep 替换后的字符
     * @return 处理结果
     * @see
     */
    public static String replace(String str, String old, String rep) {
        if (null == str || null == old || null == rep) {
            return "";
        }

        int index = str.indexOf(old);
        if (index < 0 || "".equals(old)) {
            return str;
        }

        StringBuffer strBuf = new StringBuffer(str);

        for (; index >= 0; index = strBuf.toString().indexOf(old)) {
            strBuf.delete(index, index + old.length());
            strBuf.insert(index, rep);
        }

        return strBuf.toString();
    }

    /**
     * 获取随机数
     *
     * @param index 次数
     * @return 结果
     * @see
     */
    public static String getRandom(int index) {
        int randomIndex = -1;
        StringBuffer randomID = new StringBuffer("");
        Double medianRandom = null;
        char randomElement[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        for (int i = 0; i < index; i++) {
            medianRandom = Double.valueOf(Math.random() * 998D);
            randomIndex = medianRandom.intValue() % 10;
            randomID.append(String.valueOf(randomElement[randomIndex]));
        }

        return randomID.toString();
    }

    /**
     * 获取随机数
     *
     * @param index 次数
     * @return 结果
     * @see
     */
    public static String getRandomZeroAndOne(int index) {
        int randomIndex = -1;
        StringBuffer randomID = new StringBuffer("");
        Double medianRandom = null;
        char randomElement[] = {'0', '1', '0', '1', '1', '0', '1', '0', '1', '1'};
        for (int i = 0; i < index; i++) {
            medianRandom = Double.valueOf(Math.random() * 998D);
            randomIndex = medianRandom.intValue() % 10;
            randomID.append(String.valueOf(randomElement[randomIndex]));
        }

        return randomID.toString();
    }

    /**
     * 判断字符串是null、""、"null"
     *
     * @param string 字符串
     * @return 判断结果
     * @see
     */
    public static boolean isNil(String string) {
        return null == string || 0 == string.trim().length() || "null".equals(string);
    }

    /**
     * 检查校验
     *
     * @param regex  校验格式
     * @param value  值
     * @param isNull 是否为空
     * @return 处理结果
     * @see
     */
    public static boolean checkValidate(String regex, String value, boolean isNull) {
        if (!isNull && isNil(value)) {
            return true;
        }

        if (isNil(value)) {
            return false;
        } else {
            return checkValidate(regex, value);
        }
    }

    /**
     * 指定格式校验字符串
     *
     * @param regex 校验格式
     * @param value 字符串
     * @return 处理结果
     * @see
     */
    public static boolean checkValidate(String regex, String value) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(value);
        return m.matches();
    }

    /**
     * 检查字符串日期格式
     *
     * @param str       字符串
     * @param formatStr 日期格式
     * @return
     * @see
     */
    public static boolean checkDate(String str, String formatStr) {
        if (str == null || formatStr == null) return false;
        return DateUtil.format(str, formatStr) != null;
    }

    /**
     * 数据格式化
     *
     * @param value           double值
     * @param dataFormatStyle 格式
     * @return 处理结果
     * @see
     */
    public static String dataFormat(double value, String dataFormatStyle) {
        DecimalFormat df = new DecimalFormat(dataFormatStyle);
        return df.format(value);
    }

    /**
     * 获取字符串Java Oracle Length
     *
     * @param value 字符串
     * @return 长度
     * @see
     */
    public static int getJava2OracleLength(String value) {
        if (null == value) {
            return 0;
        }

        if (value.length() == value.getBytes().length) {
            return value.length();
        }

        int len = 0;
        char charArr[] = value.toCharArray();

        for (int i = 0; i < charArr.length; i++) {
            if (String.valueOf(charArr[i]).matches("[^x00-xff]")) {
                len += 3;
            } else {
                len++;
            }
        }

        return len;
    }

    /**
     * 获取字符串Java Oracle Length
     *
     * @param value 字符串
     * @return 长度
     * @see
     */
    public static int getLengthInOracle(String value) {
        int length = 0;
        try {
            if (!isNil(value)) {
                length = value.getBytes("UTF-8").length;
            }
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }

        return length;
    }

    /**
     * float数据格式化
     *
     * @param value           float值
     * @param dataFormatStyle 格式
     * @return 处理结果
     * @see
     */
    public static String dataFormat(float value, String dataFormatStyle) {
        DecimalFormat df = new DecimalFormat(dataFormatStyle);
        return df.format(value);
    }

    /**
     * 获取序列号
     *
     * @return 序列号
     * @see
     */
    public static String getSerialNumer() {
        int randomInt = (new Random()).nextInt(0xf423f);
        String serialNumer = (new StringBuilder()).append(
                DateUtil.getSysDate(DateUtil.YYYYMMDDHHMMSS_SHORT, new Date())).append(
                randomInt).toString();
        return serialNumer;
    }

    /**
     * 判断字符串不为null或空字符串
     *
     * @param foo
     * @return
     * @see
     */
    public static final boolean isValid(String foo) {
        return foo != null && foo.length() > 0;
    }

    /**
     * 检查sql格式
     *
     * @param sql sql字符串
     * @return 检查结果
     * @see
     */
    public static boolean checkRiskSql(String sql) {
        sql = sql.trim().toLowerCase();
        if ((sql.startsWith("delete") || sql.startsWith("update"))
                && (!sql.contains("where") || sql.indexOf("=") == -1)) {
            logger.error("checkRiskSql Error:" + sql);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断字符串是否为null或者空字符串
     *
     * @param string
     * @return
     * @see
     */
    public static boolean isEmpty(String string) {
        return (null == string) || (string.trim().length() == 0);
    }


    /**
     * 判断传入参数是否为空
     *
     * @param params
     * @return
     * @see
     */
    public static boolean paramValid(String... params) {
        if (null == params) {
            return true;
        }

        for (String param : params) {
            if (StringUtil.isEmpty(param)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 用下划线拼接传入参数
     *
     * @param params
     * @return
     * @see
     */
    public static String printVal(String... params) {
        StringBuilder str = new StringBuilder("");
        if (null == params) {
            return str.toString();
        }

        for (String param : params) {
            str.append(isEmpty(param) ? "NULL" : param).append(CommonConsts.DOWN_LINE);
        }

        return str.toString();
    }

    public static String string2Json(String s) {
        if (isEmpty(s)) {
            return s;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '\"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String getStackTraceInfo(Exception e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);// 将出错的栈信息输出到printWriter中
            pw.flush();
            sw.flush();
            return sw.toString();
        } catch (Exception ex) {
            return "printStackTrace()转换错误";
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (pw != null) {
                pw.close();
            }
        }

    }

//    public static boolean isEmail(String string) {
//        if (string == null)
//            return false;
//        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
//        Pattern p;
//        Matcher m;
//        p = Pattern.compile(regEx1);
//        m = p.matcher(string);
//        if (m.matches())
//            return true;
//        else
//            return false;
//    }

    /**
     * 验证邮箱是否合法
     */
    public static boolean isEmail(String email) {
        Pattern emailPattern = Pattern.compile(
                "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher matcher = emailPattern.matcher(email);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        String email = "1147_54.545@qq.com";
        boolean email1 = isEmail(email);
        System.out.println(email1);
    }

}
