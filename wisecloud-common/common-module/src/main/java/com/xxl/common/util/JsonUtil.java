package com.xxl.common.util;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxl.common.enums.CommonConsts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Json通用处理类
 *
 * @author xiaohui
 * @version 2016年10月29日
 * @see JsonUtil
 * @since
 */
public final class JsonUtil {
    private static final Logger logger = LogManager.getLogger(JsonUtil.class);
    private static ObjectMapper objectMapper;

    public JsonUtil() {
    }

    /**
     * 将对象转成字符串Json
     *
     * @param obj 对象
     * @return Json串
     * @throws JsonProcessingException
     * @see
     */
    public static String writeObject2JSON(Object obj) {

        try {
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            logger.error(CommonConsts.LOG_ERROR_STR, StringUtil.string2Json(e.toString()));
        }
        return null;
    }

    /**
     * 将Json串转成对象
     *
     * @param json json串
     * @param obj  对象class
     * @return 对象
     * @throws IOException
     * @see
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Object writeJSON2Object(String json, Class obj) throws IOException {
        return objectMapper.readValue(json, obj);
    }

    /**
     * 将json串转成对象
     *
     * @param json           json串
     * @param collectionClass 集合对象
     * @param elementClasses 对象数组
     * @return
     * @throws IOException
     * @see
     */
    @SuppressWarnings("rawtypes")
    public static Object writeJSON2List(String json, Class collectionClass, Class elementClasses[]) throws IOException {
        com.fasterxml.jackson.databind.JavaType jt = objectMapper.getTypeFactory()
                .constructParametricType(collectionClass, elementClasses);
        return objectMapper.readValue(json, jt);
    }

    static {
        // 初始化
        objectMapper = new ObjectMapper();
        objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    public static Map sortMap(Map<String, String> map) {
        Map sortMap = map.entrySet().stream().sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        return sortMap;
    }

    public static List<Map> list2Map(List<?> list) {
        List<Map> collect = list
                .stream()
                .map(item -> {
                    Map map = JSON.parseObject(JSON.toJSONString(item), Map.class);
                    return map;
                })
                .collect(Collectors.toList());
		return collect;
	}

}