package com.xxl.service;

import com.xxl.common.api.R;
import com.xxl.common.enums.URIConsts;
import com.xxl.common.po.MsgNumPO;
import com.xxl.common.po.MsgPO;
import com.xxl.common.vo.DeleteMsgVo;
import com.xxl.common.vo.GetMsgNumVo;
import com.xxl.common.vo.GetMsgVo;
import com.xxl.common.vo.UpdateMsgStatusVo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Box服务提供者API
 */
@RestController
@RequestMapping(URIConsts.API_BOX)
public interface BoxMessageInfoAPI {

    /**
     * @param getMsgVo 查询消息的请求参数信息
     * @return 返回最终的结果
     */
    @PostMapping(value = "select", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    R<MsgPO> selectBoxMessageInfo(@RequestBody GetMsgVo getMsgVo);

    /**
     * @param updateMsgStatusVo 更新消息的请求参数信息
     * @return 返回最终的结果
     */
    @PostMapping(value = "update", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    R<Integer> updateBoxMessageInfo(@RequestBody UpdateMsgStatusVo updateMsgStatusVo);

    /**
     * @param getMsgNumVo 查询消息数量的请求参数信息
     * @return 返回最终的结果
     */
    @PostMapping(value = "count", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    R<MsgNumPO> selectBoxMessageInfoCount(@RequestBody GetMsgNumVo getMsgNumVo);

    /**
     * @param deleteMsgVo 删除消息数量的请求参数信息
     * @return 返回最终的结果
     */
    @PostMapping(value = "delete", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    R<Integer> deleteBoxMessageInfo(@RequestBody DeleteMsgVo deleteMsgVo);
}
