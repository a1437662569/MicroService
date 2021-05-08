package com.xxl.common.util;


import com.xxl.common.enums.CommonConsts;
import com.xxl.common.enums.RedisEnum;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * 字符串处理通用类
 *
 * @author xiaohui
 * @version 2016年10月29日
 * @see StringUtil
 * @since
 */
public final class StringUtil {
    public static String redisTaskLog(RedisEnum redisEnum, String info) {
        return String.format(CommonConsts.TASK_KEY_MSG, redisEnum.getKey(), redisEnum.getMsg(), info);
    }

    public static String getString2Map(String paramsStr) {
        if (StringUtil.isEmpty(paramsStr)) {
            return "{}";
        }
        paramsStr = string2Json(paramsStr);
        String mapFormat = "\"%s\":\"%s\"";
        List list = new ArrayList<>(Arrays.asList(paramsStr.split("&")));
        String params = (String) list.stream().map(itemKeyValue -> {
            List kvList = new ArrayList<String>(Arrays.asList(itemKeyValue.toString().split("=")));

            return kvList.size() == 2 ?
                    String.format(mapFormat, kvList.get(0), kvList.get(1)) :
                    String.format(mapFormat, kvList.get(0), "");
        }).collect(Collectors.joining(","));
        return "{" + params + "}";
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
            e.printStackTrace();
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
     * 判断字符串是否为null或者空字符串
     *
     * @param string
     * @return
     * @see
     */
    public static boolean isEmpty(String string) {
        return (null == string) || (string.trim().length() == 0);
    }

    public static boolean isEmpty(String... strings) {
        for (String item : strings) {
            if (isEmpty(item)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    /**
     * 对比double值
     *
     * @param first  第一个值
     * @param second 第二个值
     * @return first > second结果
     * @see
     */
    public static boolean compareDouble(double first, double second) {
        return first <= second;
    }

    /**
     * 判断定长数组是否为空
     *
     * @param string 数组
     * @param len    数组长度
     * @return 是否为空
     * @see
     */
    public static boolean isArrayEmpty(String[] string, int len) {
        if (string.length == len) {
            for (int i = 0; i < string.length; i++) {
                if (null == string[i] || string[i].trim().length() == 0) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 判断数组不为空
     *
     * @param arry 数组
     * @return 校验结果
     * @see
     */
    public static boolean isNotArrayEntity(String[] arry) {
        return null != arry && 0 != arry.length;
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
     * @param dcClientIdsStr
     * @return
     * @see
     */

    public static Integer[] StrArr2IntrArr(String[] dcClientIdsStr) {
        Integer[] num = new Integer[dcClientIdsStr.length];
        for (int i = 0; i < num.length; i++) {
            num[i] = Integer.parseInt(dcClientIdsStr[i]);
        }
        return num;
    }

    /**
     * @param dcClientIdArr
     * @param refClientIdStrArr
     * @return 将dcClientIdArr中去掉refClientIdsStrArr中的元素，然后返回最新的数组
     */
    public static String[] removeSecondArray(String[] dcClientIdArr, String[] refClientIdStrArr) {
        // ##########得到两个数组中相同元素的个数##########
        Set<String> first = new HashSet<>();
        for (String max : dcClientIdArr) {
            first.add(max);
        }

        Set<String> second = new HashSet<>();
        for (String min : refClientIdStrArr) {
            second.add(min);
        }
        Iterator<String> it = second.iterator();
        while (it.hasNext()) {
            String value = it.next();
            if (first.contains(value)) {
                first.remove(value);
            }
        }
        // 遍历set
        String[] result = new String[first.size()];
        Iterator<String> firstSet = first.iterator();
        int index = 0;
        while (firstSet.hasNext()) {
            result[index] = firstSet.next();
            index++;

        }
        return result;
    }

    /**
     * 将字符串数组转换为String
     *
     * @param newRefClientId
     * @return
     */
    public static String ArrayToString(String[] newRefClientId) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < newRefClientId.length; i++) {
            if (i == (newRefClientId.length - 1)) {
                sb.append(newRefClientId[i]);
            } else {
                sb.append(newRefClientId[i] + ",");
            }

        }
        String s = sb.toString();
        return s;
    }

    public static String exception2JSON(Exception e) {
        return string2Json(getStackTraceInfo(e));
    }

    public static String string2Json(String s) {
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

    public static String printExcetion(Exception e) {
        return string2Json(getStackTraceInfo(e));
    }

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


    public static String emailChangeFun(String email)
    {
        // 传递过来的邮箱不能为空
        if (!StringUtil.isEmpty(email))
        {
            // 只有email的后缀为@gmail.com的时候才处理
            if (email.endsWith(CommonConsts.GMAIL))
            {
                // 正常情况下，传递过来的邮箱只会有一个@gmail.com的后缀
                String[] emailInfoList = email.split(CommonConsts.GMAIL);
                // 故emailInfoList[0]中一定是邮箱的前缀
                String emailBefore = emailInfoList[0];
                // 如果邮箱前面的不为空，则需要做逻辑处理
                if (!StringUtil.isEmpty(emailBefore))
                {
                    // 将emailBefore中的英文点号和加号替换为空字符串
                    emailBefore = emailBefore.replace(CommonConsts.POINT, CommonConsts.EMPTY_STR);
                    emailBefore = emailBefore.replace(CommonConsts.PLUS, CommonConsts.EMPTY_STR);
                    // 如果走到了这一步，说明已经被替换了，那么此时email就可以重新拼装了
                    email = emailBefore + CommonConsts.GMAIL;
                }
            }
        }
        return email;
    }

    public static String getRandomVerifiCode()
    {
        String str = "0123456789";
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++ )
        {
            char ch = str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }
        return sb.toString();
    }

    public static String objToString(Object object) {
        return null != object ? String.valueOf(object) : null;
    }


}
