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
public class EpisodeDetailVideo {

    private String index;
    private String codec_name;
    private String codec_long_name;
    private String profile;
    private String codec_type;
    private String codec_time_base;
    private String codec_tag_string;
    private String codec_tag;
    private float width;
    private float height;
    private float coded_width;
    private float coded_height;
    private float has_b_frames;
    private String sample_aspect_ratio;
    private String display_aspect_ratio;
    private String pix_fmt;
    private float level;
    private String chroma_location;
    private float refs;
    private String is_avc;
    private String nal_length_size;
    private String r_frame_rate;
    private String avg_frame_rate;
    private String time_base;
    private float start_pts;
    private String start_time;
    private float duration_ts;
    private String duration;
    private String bit_rate;
    private String bits_per_raw_sample;
    private String nb_frames;
    private EpisodeDetailDisposition disposition;
    private EpisodeDetailTags tags;


}
