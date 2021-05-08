package com.xxl.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * 对应mongodb中的category，表示的是分类信息
 */
@Document(collection = "series_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeriesCategory {

    /**
     * 目录ID
     */
    private String category_id;

    /**
     * 目录名
     */
    private String category_name;

    /**
     * 是否有成人锁（有为1，没有为0）
     */
    private Integer parent_id = 0;

}
