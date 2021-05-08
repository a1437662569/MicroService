package com.xxl.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "vodInfo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VodInfo {

    private Info info;
    private MovieData movie_data;


}
