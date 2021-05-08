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
public class GetMsgNumVo {

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
     * 用户的userToken，当前可以为空
     */
    private String token;


}
