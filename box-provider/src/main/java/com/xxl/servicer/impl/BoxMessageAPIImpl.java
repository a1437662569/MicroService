package com.xxl.servicer.impl;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.xxl.common.api.R;
import com.xxl.common.enums.CommonConsts;
//import com.xxl.common.enums.R;
import com.xxl.common.enums.URIConsts;
import com.xxl.common.po.BoxMessageInfoPO;
import com.xxl.common.po.MsgNumPO;
import com.xxl.common.po.MsgPO;
import com.xxl.common.util.JsonUtil;
import com.xxl.common.util.LogUtils;
import com.xxl.common.util.ParamUtil;
import com.xxl.common.util.StringUtil;
import com.xxl.common.vo.DeleteMsgVo;
import com.xxl.common.vo.GetMsgNumVo;
import com.xxl.common.vo.GetMsgVo;
import com.xxl.common.vo.UpdateMsgStatusVo;
import com.xxl.model.BoxMessageInfo;
import com.xxl.service.BoxMessageInfoAPI;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@DubboService
@RestController
@Slf4j
public class BoxMessageAPIImpl implements BoxMessageInfoAPI {


    @Resource
    private MongoTemplate mongoTemplate;

    @Autowired
    private HttpServletRequest request;


    /**
     * 修改狀態信息的方法
     *
     * @return
     */
    @Override
    public R<Integer> updateBoxMessageInfo(UpdateMsgStatusVo updateMsgStatusVo) {
        long start = System.currentTimeMillis();
        R result = R.error();
        try {
            String userId = updateMsgStatusVo.getUserId();
            String messageId = updateMsgStatusVo.getMessageId();
            String messageType = updateMsgStatusVo.getMessageType();
            String status = updateMsgStatusVo.getStatus();
            String appId = updateMsgStatusVo.getAppId();

            // 必选参数校验
            if (StringUtil.paramValid(userId, appId, messageType, status)) {
                log.error(CommonConsts.REQUEST_PARAM_ERROR + updateMsgStatusVo.toString());
                result = R.paramError();
            }

            // messageType字段定义：消息类型 1系统消息 2活动消息
            // 如果是系统消息，messageType为1的时候，messageId字段为空
            // 如果是活动消息，messageType为2的时候，messageId字段不为空
            Query query = new Query();
            if (CommonConsts.NUMBER_ONE_STR.equals(messageType)) {
                query = new Query(Criteria.where(CommonConsts.USER_ID_STR).is(userId).and(CommonConsts.APP_ID_STR).is(appId).and(CommonConsts.MESSAGE_TYPE_STR).is(messageType).and(CommonConsts.STATUS_STR).ne(status));
            } else if ("2".equals(messageType)) {
                // 如果是2，则表示一次只需要更新一条信息
                query = new Query(Criteria.where(CommonConsts.MESSAGE_ID_STR).is(messageId));
            }
            // 设置更新哪些字段的值
            Update update = new Update();
            update.set(CommonConsts.STATUS_STR, status);
            update.set(CommonConsts.UPDATE_TIME_STR, new Date());

            // 调用方法进行更新操作
            UpdateResult updateResult = mongoTemplate.updateMulti(query, update, BoxMessageInfo.class);
            result = new R<Integer>().ok((int) updateResult.getModifiedCount());
        } catch (Exception e) {
            log.error(CommonConsts.LOG_ERROR_STR,
                    StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
            result = R.error();
        }

        // 记录返回信息
        try {
            LogUtils.getBussinessLogger().info(CommonConsts.LOG_FORMAT, ParamUtil.getIpAddr(request),
                    System.currentTimeMillis() - start, URIConsts.API_BOX + "update",
                    JsonUtil.writeObject2JSON(updateMsgStatusVo), ParamUtil.writeObject2JSON(result));
        } catch (Exception e) {
            LogUtils.getExceptionLogger().error(StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
        }
        return result;
    }

    /**
     * 查询消息盒子未读信息的数量
     *
     * @param getMsgNumVo
     * @return
     */
    @Override
    public R<MsgNumPO> selectBoxMessageInfoCount(GetMsgNumVo getMsgNumVo) {
        long start = System.currentTimeMillis();
        R result = R.error();
        try {
            //请求参数信息获取
            String userId = getMsgNumVo.getUserId();
            String appId = getMsgNumVo.getAppId();

            // 必选参数校验
            if (StringUtil.paramValid(userId, appId)) {
                log.error(CommonConsts.REQUEST_PARAM_ERROR + getMsgNumVo.toString());
                result = R.paramError();
            }

            // 添加3个月之内的逻辑
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -3);
            Date formNow3Month = calendar.getTime();

            // 查询到系统消息的数量
            long sysNum = mongoTemplate.count(new Query(
                    Criteria.where
                            (CommonConsts.USER_ID_STR).is(userId)
                            .and(CommonConsts.APP_ID_STR).is(appId)
                            .and(CommonConsts.MESSAGE_TYPE_STR).is(CommonConsts.NUMBER_ONE_STR)
                            .and(CommonConsts.STATUS_STR).is(CommonConsts.NUMBER_ZERO_STR)
                            .and(CommonConsts.CREATE_TIME_STR).gte(formNow3Month)
            ), BoxMessageInfo.class);

            // 查询活动类消息的数量
            long actNum = mongoTemplate.count(new Query(
                    Criteria.where
                            (CommonConsts.USER_ID_STR).is(userId)
                            .and(CommonConsts.APP_ID_STR).is(appId)
                            .and(CommonConsts.MESSAGE_TYPE_STR).is(CommonConsts.NUMBER_TWO_STR)
                            .and(CommonConsts.STATUS_STR).is(CommonConsts.NUMBER_ZERO_STR)
                            .and(CommonConsts.CREATE_TIME_STR).gte(formNow3Month)
            ), BoxMessageInfo.class);

            // 设置返回结果
            MsgNumPO msgNumPO = new MsgNumPO();
            msgNumPO.setSysNum(sysNum);
            msgNumPO.setActNum(actNum);

            result = new R<MsgNumPO>().ok(msgNumPO);
        } catch (Exception e) {
            log.error(CommonConsts.LOG_ERROR_STR,
                    StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
            result = R.error();
        }
        // 记录返回信息
        try {
            LogUtils.getBussinessLogger().info(CommonConsts.LOG_FORMAT, ParamUtil.getIpAddr(request),
                    System.currentTimeMillis() - start, URIConsts.API_BOX + "count",
                    JsonUtil.writeObject2JSON(getMsgNumVo), ParamUtil.writeObject2JSON(result));
        } catch (Exception e) {
            LogUtils.getExceptionLogger().error(StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
        }
        return result;
    }

    /**
     * @param deleteMsgVo 删除消息数量的请求参数信息
     * @return 删除数量
     */
    @Override
    public R<Integer> deleteBoxMessageInfo(DeleteMsgVo deleteMsgVo) {
        long start = System.currentTimeMillis();
        R result = R.error();
        try {
            String userId = deleteMsgVo.getUserId();
            String messageType = deleteMsgVo.getMessageType();
            String appId = deleteMsgVo.getAppId();

            // 必选参数校验
            if (StringUtil.paramValid(userId, appId, messageType)) {
                log.error(CommonConsts.REQUEST_PARAM_ERROR + deleteMsgVo.toString());
                result = R.paramError();
            }

            // 组装参数进行删除
            DeleteResult removeResult = mongoTemplate.remove(new Query(Criteria.where(CommonConsts.USER_ID_STR).is(userId).and(CommonConsts.APP_ID_STR).is(appId).and(CommonConsts.MESSAGE_TYPE_STR).is(messageType)), BoxMessageInfo.class);
            long deletedCount = removeResult.getDeletedCount();
            result = new R<Integer>().ok((int) deletedCount);
        } catch (Exception e) {
            log.error(CommonConsts.LOG_ERROR_STR,
                    StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
            result = R.error();
        }
        // 记录返回信息
        try {
            LogUtils.getBussinessLogger().info(CommonConsts.LOG_FORMAT, ParamUtil.getIpAddr(request),
                    System.currentTimeMillis() - start, URIConsts.API_BOX + "delete",
                    JsonUtil.writeObject2JSON(deleteMsgVo), ParamUtil.writeObject2JSON(result));
        } catch (Exception e) {
            LogUtils.getExceptionLogger().error(StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
        }
        return result;
    }


    @Override
    public R<MsgPO> selectBoxMessageInfo(GetMsgVo getMsgVo) {
        long start = System.currentTimeMillis();
        R result = R.error();
        try {
            log.info(getMsgVo.toString());
            // 请求参数获取
            String userId = getMsgVo.getUserId();
            Integer pageNum = getMsgVo.getPageNum();
            Integer pageSize = getMsgVo.getPageSize();
            String appId = getMsgVo.getAppId();
            String messageType = getMsgVo.getMessageType();

            // 必选参数校验
            if (StringUtil.paramValid(userId, appId)) {
                log.error(CommonConsts.REQUEST_PARAM_ERROR + getMsgVo.toString());
                result = R.paramError();
            }

            // 分页查询的判断逻辑
            if (pageNum <= CommonConsts.NUMBER_ZERO) {
                pageNum = CommonConsts.NUMBER_ONE;
            }
            pageNum = pageNum - CommonConsts.NUMBER_ONE;
            if (pageSize <= CommonConsts.NUMBER_ZERO) {
                pageSize = CommonConsts.NUMBER_TEN;
            }

            // 3个月之内的数据的限制
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -3);
            Date formNow3Month = calendar.getTime();

            // 分页查询+根据创建时间降序排序
            Sort sort = Sort.by(Sort.Direction.DESC, CommonConsts.CREATE_TIME_STR);
            Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
            // 分页查询结果
            List<BoxMessageInfo> boxMessageInfoList = mongoTemplate.find(new Query(Criteria.where(CommonConsts.USER_ID_STR).is(userId).and(CommonConsts.APP_ID_STR).is(appId).and(CommonConsts.MESSAGE_TYPE_STR).is(messageType).and(CommonConsts.CREATE_TIME_STR).gte(formNow3Month)).with(pageable), BoxMessageInfo.class);
            List<BoxMessageInfoPO> collect = boxMessageInfoList.stream().map(i -> {
                BoxMessageInfoPO boxMessageInfoPO = new BoxMessageInfoPO();
                BeanUtils.copyProperties(i, boxMessageInfoPO);
                return boxMessageInfoPO;
            }).collect(Collectors.toList());
            // 查询总数量
            int totalCount = (int) mongoTemplate.count(new Query(Criteria.where(CommonConsts.USER_ID_STR).is(userId).and(CommonConsts.APP_ID_STR).is(appId).and(CommonConsts.MESSAGE_TYPE_STR).is(messageType).and(CommonConsts.CREATE_TIME_STR).gte(formNow3Month)), BoxMessageInfo.class);

            // 返回结果
            MsgPO msgPO = new MsgPO();
            msgPO.setTotalSize(totalCount);
            msgPO.setMsgList(collect);

            result = new R<MsgPO>().ok(msgPO);
        } catch (Exception e) {
            log.error(CommonConsts.LOG_ERROR_STR,
                    StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
            result = R.error();
        }

        // 记录返回信息
        try {
            LogUtils.getBussinessLogger().info(CommonConsts.LOG_FORMAT, ParamUtil.getIpAddr(request),
                    System.currentTimeMillis() - start, URIConsts.API_BOX + "select",
                    JsonUtil.writeObject2JSON(getMsgVo), ParamUtil.writeObject2JSON(result));
        } catch (Exception e) {
            LogUtils.getExceptionLogger().error(StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
        }
        return result;
    }
}
