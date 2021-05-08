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
public class Episode {

    private List<EpisodeDetail> episodeNumber;
}
