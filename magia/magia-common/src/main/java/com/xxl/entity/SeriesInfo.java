package com.xxl.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


/**
 * 对应mongodb中的liveStreams，表示的是直播节目信息
 */
@Document(collection = "seriesInfo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeriesInfo {

    private List<Season> seasons;

    private Info info;

    private Episode episodes;
}
