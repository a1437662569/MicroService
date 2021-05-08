package com.xxl.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * 对应mongodb中的vodStreams，表示的是点播节目信息
 */
@Document(collection = "movie_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VodStreams {
    //节目序号，用于页面排序
    private Integer num;
    //电影名
    private String name;
    //节目类型 直播对应vod
    private String stream_type = "movie";
    //电影的id
    private String stream_id;
    //电影的封面
    private String stream_icon;
    //评分（10分制）
    private String rating;
    //评分（5分制）
    private Float rating_5based;
    //上架时间（时间戳）
    private String added;
    //是否是成人内容
    private String is_adult = "0";
    //所在目录的ID
    private String category_id;
    // 视频格式
    private String container_extension;
    //自定义ID
    private String custom_sid;
    //视频地址（测试无效）
    private String direct_source;


}
