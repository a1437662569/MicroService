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
public class EpisodeDetailTags {

    private String language;
    private String handler_name;


}
