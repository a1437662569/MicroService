package com.xxl.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


//@Document(collection = "vodStreams")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EpisodeDetailAudio {

    private String index;
    private String codec_name;
    private String codec_long_name;
    private String codec_type;
    private String codec_time_base;
    private String codec_tag_string;
    private String codec_tag;
    private String sample_fmt;
    private String sample_rate;
    private float channels;
    private String channel_layout;
    private float bits_per_sample;
    private String dmix_mode;
    private String ltrt_cmixlev;
    private String ltrt_surmixlev;
    private String loro_cmixlev;
    private String loro_surmixlev;
    private String r_frame_rate;
    private String avg_frame_rate;
    private String time_base;
    private float start_pts;
    private String start_time;
    private float duration_ts;
    private String duration;
    private String bit_rate;
    private String nb_frames;
    private EpisodeDetailDisposition disposition;
    private EpisodeDetailTags tags;
    private List<EpisodeDetailSideData> side_data_list;

}
