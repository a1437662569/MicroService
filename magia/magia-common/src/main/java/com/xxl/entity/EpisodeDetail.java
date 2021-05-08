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
public class EpisodeDetail {

    private String id;
    private Integer episode_num;
    private String title;
    private String container_extension;
    private EpisodeDetailInfo info;
    private String custom_sid;
    private String added;
    private Float season;
    private String direct_source;
}
