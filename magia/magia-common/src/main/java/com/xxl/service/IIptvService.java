package com.xxl.service;

import com.xxl.common.api.R;
import com.xxl.entity.po.GetUserInfoRsp;
import com.xxl.entity.po.LoginRsp;
import com.xxl.entity.vo.*;


public interface IIptvService {

    R authenticateService(String username, String password);

    Object commonAuthenticateService(String username, String password);

    R getLiveCategoriesService(String username, String password);

    R getVodCategoriesService(String username, String password);

    R getLiveStreamService(String username, String password, String category_id);

    R getVodStreamService(String username, String password, String category_id);
}
