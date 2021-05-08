package com.xxl.mapper.master;


import com.xxl.entity.Account;
import org.springframework.stereotype.Repository;

@Repository
public interface WAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Account record);

    int insertSelective(Account record);

    Account selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);

    void updateByEmailSelective(Account updateAccount);

    void updateByUserIdSelective(Account updateAccount);
}