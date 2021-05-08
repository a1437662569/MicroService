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
public class MovieData {
    // 视频ID
    private String stream_id;
    // 电影名
    private String name;
    // 上架时间
    private String added;
    // 目录ID
    private String category_id;
    // 视频格式
    private String container_extension;
    // 自定义ID
    private String custom_sid;
    // 视频地址，测试后发现无效
    private String direct_source;


}
