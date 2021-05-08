package com.xxl.common.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MsgPO {

    /**
     * 复合参数
     */
    private List<BoxMessageInfoPO> msgList;

    /**
     * 总数量
     */
    private Integer totalSize;

}
