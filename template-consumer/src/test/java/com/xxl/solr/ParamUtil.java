package com.xxl.solr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cglib.beans.BeanMap;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ParamUtil extends StringUtils {
    private static final Logger logger = LogManager.getLogger(ParamUtil.class);
    private static ObjectMapper objectMapper;
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    public static final String LOG_FORMAT = "[{\"ip\":\"{}\",\"executeTime\":\"{}\",\"uri\":\"{}\",\"data\":{\"request\":{},\"response\":{}}}]";

    static {
        // 初始化
        objectMapper = new ObjectMapper();
        objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }
    public static String getColumsAlias(String columns){
        List<String> strings = Arrays.asList(columns.split(","));
        columns = strings.stream().map(item->{
            List<String> charList = new ArrayList<>(Arrays.asList(item.split("")));
            StringBuffer sb = new StringBuffer();
            sb.append(item + " AS \"");
            for (int i = 0; i <charList.size(); i++) {
                String iString = charList.get(i);
                if(charList.get(i).equals("_") && charList.size()>(i+1)){
                    charList.set(i+1,charList.get(i+1).toUpperCase());
                }
                if(!charList.get(i).equals("_")){
                    sb.append(iString);
                }
            }
            sb.append("\"");
            return sb.toString();
        }).collect(Collectors.joining(","));
        return columns;
    }
    public static <T> Map<String, String> validate(T obj) {
        Map<String, StringBuilder> errorMap = new HashMap<>();
        Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
        if (set != null && set.size() > 0) {
            String property = null;
            for (ConstraintViolation<T> cv : set) {
                //这里循环获取错误信息，可以自定义格式
                property = cv.getPropertyPath().toString();
                if (errorMap.get(property) != null) {
                    errorMap.get(property).append("," + cv.getMessage());
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(cv.getMessage());
                    errorMap.put(property, sb);
                }
            }
        }
        return errorMap.entrySet().stream().collect(Collectors.toMap(k -> k.getKey(), v -> v.getValue().toString()));
    }
    public static String writeObject2JSON(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            logger.error("{}", string2Json(e.toString()));
        }
        return null;
    }

    public static Object writeJSON2Object(String json, Class obj) throws IOException {
        return objectMapper.readValue(json, obj);
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

    public static String getStackTraceInfo(Exception e)
    {
        StringWriter sw = null;
        PrintWriter pw = null;
        try
        {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);// 将出错的栈信息输出到printWriter中
            pw.flush();
            sw.flush();
            return sw.toString();
        }
        catch (Exception ex)
        {
            return "printStackTrace()转换错误";
        }
        finally
        {
            if (sw != null)
            {
                try
                {
                    sw.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
            if (pw != null)
            {
                pw.close();
            }
        }
    }

    /**
     * get请求方式参数转Map
     * @param paramsStr
     * @return
     */
    public static String getString2Map(String paramsStr) {
        if(isEmpty(paramsStr)){
            return "{}";
        }
        paramsStr = string2Json(paramsStr);
        String mapFormat = "\"%s\":\"%s\"";
        List list = new ArrayList<>(Arrays.asList(paramsStr.split("&")));
        String params =(String) list.stream().map(itemKeyValue -> {
            List kvList = new ArrayList<String>(Arrays.asList(itemKeyValue.toString().split("=")));

            return kvList.size() == 2 ?
                    String.format(mapFormat,kvList.get(0),kvList.get(1)) :
                    String.format(mapFormat,kvList.get(0),"");
        }).collect(Collectors.joining(","));
        return "{"+params+"}";
    }
    public static boolean emptyList(List list){
        return null == list || list.size() == 0;
    }

    public static Map<String,Object> beanMap2StringMap(BeanMap beanMap, String dateFormat){
        Map<String,Object> map =(Map<String,Object>) beanMap;
        Map<String,Object> newMap =new HashMap<String,Object>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if(entry.getValue() == null){
                newMap.put(entry.getKey(), null);
            }else{
                if(entry.getValue() instanceof String){
                    newMap.put(entry.getKey(), (String) entry.getValue());
                }else if(entry.getValue() instanceof BigDecimal){
                    newMap.put(entry.getKey(), entry.getValue().toString());
                } else{
                    newMap.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return newMap;
    }

    public static void main(String[] args) {
        System.out.println(getColumsAlias("user_id,param_id_str"));
    }
}