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
public class EpisodeDetailInfo {

  private String movie_image;
  private String plot;
  private String releasedate;
  private float rating;
  private float duration_secs;
  private String duration;
  private EpisodeDetailVideo video;
  private EpisodeDetailAudio audio;
  private float bitrate;


}
