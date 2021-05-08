package com.xxl.service;

import com.xxl.model.UserBcoinWithdrawModel;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

public interface UserBCoinWithdrawRepository extends SolrCrudRepository<UserBcoinWithdrawModel,Long> {
    @Query("*:*")
    List<UserBcoinWithdrawModel> getAll();
}
