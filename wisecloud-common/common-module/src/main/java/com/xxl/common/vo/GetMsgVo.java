package com.xxl.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GetMsgVo {

    /**
     * 用户唯一ID
     */
    @NotEmpty
    private String userId;

    /**
     * appId的值
     */
    @NotEmpty
    private String appId;

    /**
     * 每页显示条数，可以为空，默认为10
     */
    private Integer pageSize = 10;

    /**
     * 当前第几页，可以为空，默认为1
     */
    private Integer pageNum = 1;

    /**
     * 用户的userToken，当前可以为空
     */
    private String token;

    /**
     * 消息类型 1系统消息 2活动消息
     */
    @NotEmpty
    private String messageType = "1";


//    private String status;
//
//    private String messageType;
//
//    private String content;
}
