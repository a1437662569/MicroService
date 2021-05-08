package com.xxl.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Document(collection = "box")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoxMessageInfo {

    private String userId;
    private String messageId;
    private String messageType;
    private String type;
    private String appId;
    private Object content;
    private String status="0";
    //("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "EEE, dd MMM yyyy HH:mm:ss zzz", "yyyy-MM-dd"))

    //    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    //    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
