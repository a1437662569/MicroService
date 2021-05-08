package com.xxl.entity;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


//@Document(collection = "vodStreams")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EpisodeDetailDisposition {

    @SerializedName("default")
    public Float default1;
    public Float dub;
    public Float original;
    public Float comment;
    public Float lyrics;
    public Float karaoke;
    public Float forced;
    public Float hearing_impaired;
    public Float visual_impaired;
    public Float clean_effects;
    public Float attached_pic;
    public Float timed_thumbnails;


}
