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
public class LiveStreams {
    //节目序号，用于页面排序
    private Integer num;
    //节目名
    private String name;
    //节目类型 直播对应live
    private String stream_type = "live";
    //节目的ID
    private String stream_id;
    //icon的地址
    private String stream_icon;
    //节目预告的频道ID
    private String epg_channel_id;
    //上架时间（时间戳）
    private String added;
    //是否是成人内容
    private String is_adult = "0";
    //所在目录的ID
    private String category_id;
    //自定义ID
    private String custom_sid;
    //是否存档
    private Integer tv_archive;
    //视频地址（测试无效）
    private String direct_source;
    //存档时间
    private Integer tv_archive_duration;


}
