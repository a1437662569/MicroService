package com.xxl.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * 对应mongodb中的liveStreams，表示的是直播节目信息
 */
@Document(collection = "channel_in_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Series {
    //节目序号，用于页面排序
    private Integer num;
    //电视剧名
    private String name;
    // 电视剧ID
    private Integer series_id;
    // 电视剧封面地址
    private String cover;
    // 电视剧情节（葡语）
    private String plot;
    // 电视剧演员
    private String cast;
    // 电视剧导演
    private String director;
    // 电视剧类型
    private String genre;
    // 上架时间（标准格式）
    private String releaseDate;
    // 最新修改时间（时间戳）
    private String last_modified;
    // 评分（10分制）
    private String rating;
    // 评分（5分制）
    private Float rating_5based;
    // 背景大图地址
    private String[] backdrop_path;
    // YouTube预告片
    private String youtube_trailer;
    // 播放次数
    private String episode_run_time;
    // 所在的目录ID
    private String category_id;
}
