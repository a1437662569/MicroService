package com.xxl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxl.model.UserBcoinWithdrawModel;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public interface UserBcoinWithdrawMapper extends BaseMapper<UserBcoinWithdrawModel> {
    @Select("select user_id from t_user_bcoin_withdraw")
    List<UserBcoinWithdrawModel> getUserId();
}
