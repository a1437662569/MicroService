package com.xxl.controller;

import com.sun.xml.internal.bind.v2.TODO;
import com.xxl.common.api.R;
import com.xxl.common.enums.CommonConsts;
import com.xxl.common.util.HttpUtil;
import com.xxl.common.util.JsonUtil;
import com.xxl.common.util.StringUtil;
import com.xxl.entity.po.GetUserInfoRsp;
import com.xxl.entity.po.LoginRsp;
import com.xxl.entity.vo.*;
import com.xxl.service.IIptvService;
import com.xxl.service.IMagiaService;
import com.xxl.service.RedisService;
import com.xxl.util.UserLoginToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * channel_in_category:保存直播分类信息
 * vod_category：保存点播分类信息
 * channel_in_category：保存直播分类下的频道信息
 */
@RestController
//@RequestMapping(CommonConsts.MAGIA)
@Slf4j
public class IptvController {

    @Autowired
    RedisService redisService;

    @Autowired
    IIptvService iIptvService;

    @Autowired
    private HttpServletRequest request;


    //    @PostMapping(value = "/player_api.php", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @RequestMapping(value = "/player_api.php", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/player_api.php", method = RequestMethod.GET)
    public Object iptvCon(HttpServletRequest httpRequest) {
        // 标识开始时间
        String returnStr = null;
        String ipAddr = null;
        try {
            String username = httpRequest.getParameter("username");
            String password = httpRequest.getParameter("password");
            String action = httpRequest.getParameter("action");
            String category_id = httpRequest.getParameter("category_id");
            String stream_id = httpRequest.getParameter("stream_id");
            String vod_id = httpRequest.getParameter("vod_id");
            String series_id = httpRequest.getParameter("series_id");

            // 根据请求参数的不同来分别做处理
            // 如果请求参数中的username和password不为空，但是action为空，则表示走密码认证接口
            // http://127.0.0.1:8080/player_api.php?username=81034&password=123456
            if (!StringUtil.paramValid(username, password) && StringUtil.isEmpty(action)) {
                //首先调用公共方法，进行密码认证和授权校验
                Object authResult = iIptvService.commonAuthenticateService(username, password);
                if (null != authResult) {
                    return new ResponseEntity(HttpStatus.UNAUTHORIZED);
                }
                // 如果用户名密码是正确的，则组装返回参数
                R result = iIptvService.authenticateService(username, password);
                returnStr = JsonUtil.writeObject2JSON(result.getData());
            }

            //如果请求参数中的username和password和action不为空，且action为get_live_categories表示获取live直播节目的目录列表
            //http://127.0.0.1:8080/player_api.php?username=81034&password=123456&action=get_live_categories
            if (!StringUtil.paramValid(username, password, action) && CommonConsts.GET_LIVE_CATEGORIES_STR.equalsIgnoreCase(action)) {
                Object authResult = iIptvService.commonAuthenticateService(username, password);
                if (null != authResult) {
                    return new ResponseEntity(HttpStatus.UNAUTHORIZED);
                }
                R result = iIptvService.getLiveCategoriesService(username, password);
                returnStr = JsonUtil.writeObject2JSON(result.getData());
            }

            //如果请求参数中的username和password和action和category_id不为空，且action为get_live_streams表示获取特定live直播目录下的节目列表
            if (!StringUtil.paramValid(username, password, action, category_id) && CommonConsts.GET_LIVE_STREAMS_STR.equalsIgnoreCase(action)) {
                Object authResult = iIptvService.commonAuthenticateService(username, password);
                if (null != authResult) {
                    return new ResponseEntity(HttpStatus.UNAUTHORIZED);
                }
                R result = iIptvService.getLiveStreamService(username, password, category_id);
                returnStr = JsonUtil.writeObject2JSON(result.getData());
            }

            // TODO: 2021/5/6 这个需求暂时不做：如果请求参数中的username和password和action和stream_id不为空，且action为get_short_epg获取特定live直播节目的节目预告

            //如果请求参数中的username和password和action不为空，且action为get_vod_categories表示获取vod电影的目录列表
            //http://127.0.0.1:8080/player_api.php?username=81034&password=123456&action=get_vod_categories
            if (!StringUtil.paramValid(username, password, action) && CommonConsts.GET_VOD_CATEGORIES_STR.equalsIgnoreCase(action)) {
                Object authResult = iIptvService.commonAuthenticateService(username, password);
                if (null != authResult) {
                    return new ResponseEntity(HttpStatus.UNAUTHORIZED);
                }
                //  这里是要获取所有的点播节目目录。
                R result = iIptvService.getVodCategoriesService(username, password);
                returnStr = JsonUtil.writeObject2JSON(result.getData());
            }

            //如果请求参数中的username和password和action和category_id不为空，且action为get_vod_streams表示获取vod电影的目录列表
            if (!StringUtil.paramValid(username, password, action, category_id) && CommonConsts.GET_VOD_STREAMS_STR.equalsIgnoreCase(action)) {
                Object authResult = iIptvService.commonAuthenticateService(username, password);
                if (null != authResult) {
                    return new ResponseEntity(HttpStatus.UNAUTHORIZED);
                }
                // 这里是获取category_id点播目录下的所有节目单
                // TODO: 2021/5/8  需要等坤远那边处理数据结构正确以后再测试看下效果
                R result = iIptvService.getVodStreamService(username, password, category_id);
                returnStr = JsonUtil.writeObject2JSON(result.getData());
            }

            //获取特定vod电影的介绍
            if (!StringUtil.paramValid(username, password, action, vod_id) && CommonConsts.GET_VOD_INFO_STR.equalsIgnoreCase(action)) {
                Object authResult = iIptvService.commonAuthenticateService(username, password);
                if (null != authResult) {
                    return new ResponseEntity(HttpStatus.UNAUTHORIZED);
                }
                // TODO: 2021/5/7 这里是获取vod_id对应的电影的介绍
            }

            // 获取series电视剧的目录列表
            if (!StringUtil.paramValid(username, password, action) && CommonConsts.GET_SERIES_CATEGORIES_STR.equalsIgnoreCase(action)) {
                Object authResult = iIptvService.commonAuthenticateService(username, password);
                if (null != authResult) {
                    return new ResponseEntity(HttpStatus.UNAUTHORIZED);
                }
                // TODO: 2021/5/7 这里是获取所有的series目录列表
            }

            //获取特定series电视剧目录下的电视剧列表
            if (!StringUtil.paramValid(username, password, action, category_id) && CommonConsts.GET_SERIES_STR.equalsIgnoreCase(action)) {
                Object authResult = iIptvService.commonAuthenticateService(username, password);
                if (null != authResult) {
                    return new ResponseEntity(HttpStatus.UNAUTHORIZED);
                }
                // TODO: 2021/5/7 这里是获取特定series电视剧目录下的电视剧列表 category_id
            }

            // 获取特定series电视剧的介绍
            if (!StringUtil.paramValid(username, password, action, series_id) && CommonConsts.GET_SERIES_INFO_STR.equalsIgnoreCase(action)) {
                Object authResult = iIptvService.commonAuthenticateService(username, password);
                if (null != authResult) {
                    return new ResponseEntity(HttpStatus.UNAUTHORIZED);
                }
                // TODO: 2021/5/7 这里是获取特定series电视剧的介绍 series_id
            }


//            getEmailVerifyCodeReq = (GetEmailVerifyCodeReq) JsonUtil.writeJSON2Object(reqStr, GetEmailVerifyCodeReq.class);
            ipAddr = HttpUtil.getIpAddr(request);
//            getEmailVerifyCodeReq.setIpAddr(ipAddr);
//            R result = magiaService.getEmailVerifyCodeService(getEmailVerifyCodeReq);
//            returnStr = JsonUtil.writeObject2JSON(result);
        } catch (Exception e) {
            log.error(StringUtil.printExcetion(e));
        }
        return returnStr;
    }


}
