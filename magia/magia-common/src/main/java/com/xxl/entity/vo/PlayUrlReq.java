package com.xxl.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayUrlReq implements Serializable {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 播放媒体标识
     */
    private String mediaCode;

    /**
     * 播放串过期时间，时间戳
     */
    private Long expires;

    /**
     * 业务类型，点播：vod，直播：live
     */
    private String business;
}