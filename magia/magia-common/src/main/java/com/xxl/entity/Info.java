package com.xxl.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


//@Document(collection = "vodStreams")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Info {

    // 对应的电影评分网页地址
    private String kinopoisk_url;
    // 对应的电影评分编号
    private String tmdb_id;
    // 电影名
    private String name;
    // 原电影名
    private String o_name;
    // 电影封面图
    private String cover_big;
    // 电影封面图
    private String movie_image;
    // 上映时间
    private String releasedate;
    // 播放次数
    private String episode_run_time;
    // 预告片地址
    private String youtube_trailer;
    // 导演
    private String director;
    // 演员（英文）
    private String actors;
    // 演员（葡文）
    private String cast;
    // 情节描述（英文）
    private String description;
    // 情节描述（葡文）
    private String plot;
    // 电影上映距今
    private String age;
    // MPAA评分
    private String mpaa_rating;
    // kinopoist评分次数
    private Integer rating_count_kinopoisk;
    // 国家
    private String country;
    // 类型
    private String genre;
    // 介绍页面大封面
    private String[] backdrop_path;
    // 时长/秒
    private Integer duration_secs;
    // 时长（标准格式）
    private String duration;
    // 空字段
    private String[] video;
    // 空字段
    private String[] audio;
    // 比特率
    private Integer bitrate;
    // 评分
    private String rating;
    //评分（5分制）
    private String rating_5based;
    // 最新修改时间
    private String last_modified;
    // 所在的电视剧目录ID
    private String category_id;


}
