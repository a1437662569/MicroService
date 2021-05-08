package com.xxl.config;

import cn.hutool.core.map.MapUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Map;

@RefreshScope
@Component
@Data
@ConfigurationProperties(prefix = "list-or-map")
public class ListMapNacosConfiguration {
    //URL
    private final String URL_DEFAULT_KEY = "DEFAULT";
    //下载次数
    private final int TIMES_DEFAULT_MIN_VALUE = 5000;
    private final Integer TIMES_DEFAULT_APPID = 0;
    /**
     * APK默认下载次数(默认值5000)
     **/
    private Map<Integer, Integer> apkMinDownloadTimesMap;
    /**
     * APK默认下载地址
     */
    private Map<String, String> apkDownloadDefaultURLMap;

    public String getApkDownloadDefaultURL(String packageName) {
        if (MapUtil.isNotEmpty(apkDownloadDefaultURLMap)) {
            if (apkDownloadDefaultURLMap.containsKey(packageName)) {
                return apkDownloadDefaultURLMap.get(packageName);
            } else if (apkDownloadDefaultURLMap.containsKey(URL_DEFAULT_KEY)) {
                return apkDownloadDefaultURLMap.get(URL_DEFAULT_KEY);
            }
        }
        return null;
    }

    public Integer getApkMinDownloadTimes(int appId, int current) {
        int min = TIMES_DEFAULT_MIN_VALUE;
        if (MapUtil.isNotEmpty(apkMinDownloadTimesMap)) {
            if (apkMinDownloadTimesMap.containsKey(appId)) {
                min = apkMinDownloadTimesMap.get(appId);
            } else if (apkMinDownloadTimesMap.containsKey(TIMES_DEFAULT_APPID)) {
                min = apkMinDownloadTimesMap.get(TIMES_DEFAULT_APPID);
            }
        }
        return current >= min ? 0 : min;
    }
}
