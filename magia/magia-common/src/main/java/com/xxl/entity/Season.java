package com.xxl.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


//@Document(collection = "vodStreams")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Season {
    //  播出时间
    private String air_date;
    // 单季的集数
    private Integer episode_count;
    // 节目id
    private Integer id;
    //节目名
    private String name;
    // 不明字段
    private String overview;
    // 第几季
    private Integer season_number;
    // 封面图地址
    private String cover;
    // 封面大图地址
    private String cover_big;


}
