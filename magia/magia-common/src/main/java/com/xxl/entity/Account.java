package com.xxl.entity;

import com.xxl.common.vo.BaseVO;
import com.xxl.entity.vo.BaseVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account implements BaseVO {
    private int id;
    private String email;
    private String password;
    private String otherPwd;
    private String dns;
    private String parentalPwd;
    private String playlistUrl;
    private String mac;
    private Date authInvalidTime;
    private Integer userId;
    private Date createTime;
    private Date updateTime;
    private String creater;
    private String updater;

    @Override
    public String getKey() {
        return null;
    }
}
