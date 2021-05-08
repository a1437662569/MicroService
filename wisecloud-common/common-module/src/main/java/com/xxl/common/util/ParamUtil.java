package com.xxl.common.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxl.common.api.R;
import com.xxl.common.enums.APICodeMsgEnum;
import com.xxl.common.enums.CommonConsts;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class ParamUtil {
    public static final String LOG_FORMAT = "[{\"ip\":\"{}\",\"executeTime\":\"{}\",\"uri\":\"{}\",\"data\":{\"request\":{},\"response\":{}}}]";
    private static ObjectMapper objectMapper;
    public static final List<String> BODY_METHOND_LIST = Arrays.asList("POST", "PUT", "PATCH");
    public static final List<String> URL_METHOND_LIST = Arrays.asList("GET", "DELETE");
    static {
        // 初始化
        objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();

            // 本地IP处理
            if ("127.0.0.1".equals(ip)) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                if (null != inet) {
                    ip = inet.getHostAddress();
                }
            }
        }

        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割 15是IP长度
        if (null != ip && 15 < ip.length()) {
            int comma = ip.indexOf(',');
            if (comma > 0) {
                ip = ip.substring(0, comma);
            }
        }

        return ip;
    }

    public static String writeObject2JSON(Object obj) {
        try {
            if ("String".equals(obj.getClass().getSimpleName())) {
                return (String) obj;
            }
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            log.error("{}", string2Json(e.toString()));
        }
        return null;
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

    public static boolean isOK(R r) {
        return null != r && APICodeMsgEnum.SUCCESS.getCode().equals(r.getReturnCode());
    }

    public static boolean isNotNullOK(R r) {
        return isOK(r) && null != r.getData();
    }

    public static boolean isNullOK(R r) {
        return isOK(r) && null == r.getData();
    }

    public static boolean isEmptyList(List list) {
        return null == list || list.size() == 0;
    }

    public static boolean isNotEmptyList(List list) {
        return !isEmptyList(list);
    }

    public static boolean isEmptyCollection(Collection collection) {
        return null == collection || collection.isEmpty();
    }

    public static boolean isEmptyCollection(Collection... collections) {
        for (Collection item : collections) {
            if (isEmptyCollection(item)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 校验范围,左闭右开
     *
     * @param compareInteger
     * @param startInteger
     * @param endInteger
     * @return
     */
    public static boolean inRange(Integer compareInteger, Integer startInteger, Integer endInteger) {
        return compareInteger.compareTo(startInteger) >= 0 && compareInteger.compareTo(endInteger) < 0;
    }

    /**
     * 校验不在范围内,左闭右开
     *
     * @param compareInteger
     * @param startInteger
     * @param endInteger
     * @return
     */
    public static boolean notInRange(Integer compareInteger, Integer startInteger, Integer endInteger) {
        return inRange(compareInteger, startInteger, endInteger);
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
     * 数据为空时不可进行集合长度变更的操作!!!!
     *
     * @param originalStr
     * @return Collections.emptyList() or NotEmptyArrayList
     */
    public static List<String> splitStr2ListByComma(String originalStr) {
        if (StringUtil.isEmpty(originalStr)) {
            return Collections.emptyList();
        }
        return new ArrayList<>(Arrays.asList(originalStr.split(CommonConsts.COMMA)));
    }

    public static List<String> splitStr2List(String originalStr, String separator) {
        if (StringUtil.isEmpty(originalStr)) {
            return Collections.emptyList();
        }
        return new ArrayList<>(Arrays.asList(originalStr.split(separator)));
    }

    public static boolean allTrue(boolean... flag) {
        for (boolean b : flag) {
            if (!b) return false;
        }
        return true;
    }
}
