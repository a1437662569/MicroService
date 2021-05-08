package com.xxl.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;


@RefreshScope
@Component
@Data
public class TokenInfo {
    @Value("${token.alive.time:7200}")
//    private String tokenAliveTime;
    private String tokenAliveTime;

    @Value("${initPassword:123456}")
    private String initPassword;

}

