package com.xxl.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xxl.model.UserBcoinWithdrawModel;

import java.util.List;

public interface UserBcoinWithdrawService extends IService<UserBcoinWithdrawModel> {

    List<UserBcoinWithdrawModel> getDevData();

    List<UserBcoinWithdrawModel> getTestData();
}
