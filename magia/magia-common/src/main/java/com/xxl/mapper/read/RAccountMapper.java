package com.xxl.mapper.read;


import com.xxl.entity.Account;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RAccountMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Account record);

    int insertSelective(Account record);

    Account selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);

    Account selectByEmail(String email);

    Account selectByUserId(int userId);

    /**
     * @return 查询出所有加入了免费试用的用户的邮箱
     */
    List<String> selectEmailForFreeTrail();

    /**
     * @return 查询所有邮箱不为空的用户的邮箱
     */
    List<String> selectEmailForAll();
}