package com.xxl.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxl.mapper.UserBcoinWithdrawMapper;
import com.xxl.model.UserBcoinWithdrawModel;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.GroupParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service("userBcoinWithdrawService")
public class UserBcoinWithdrawServiceImpl extends ServiceImpl<UserBcoinWithdrawMapper, UserBcoinWithdrawModel> implements UserBcoinWithdrawService {
    @Autowired
    UserBcoinWithdrawMapper userBcoinWithdrawMapper;
    @Autowired
        @Qualifier("redisTemplate1")
    private RedisTemplate redisTemplate;

    @Autowired
    @Qualifier("redisTemplate2")
    private RedisTemplate redisTemplate2;

    @Autowired
    UserBCoinWithdrawRepository userBCoinWithdrawRepository;

    @Autowired
    SolrClient solrClient;

    //    @DS("master")
    @Override
    public List<UserBcoinWithdrawModel> getDevData(){
        /**
         *         QueryWrapper<UserBcoinWithdrawModel> queryWrapper = new QueryWrapper().select("user_id");
         * List list = userBcoinWithdrawMapper.selectMaps(queryWrapper);
         *         List<UserBcoinWithdrawModel> userId = userBcoinWithdrawMapper.getUserId();
         *         List<UserBcoinWithdrawModel> all = userBCoinWithdrawRepository.getAll();
         */
        Random random = new Random();
        int randomNum = random.nextInt(9999);
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.set(CommonParams.WT, "json")
                .set(CommonParams.Q, "programType:cut")
                .set(GroupParams.GROUP, true)
                .set(GroupParams.GROUP_FIELD, "updateTime")
                //.set(GroupParams.GROUP_QUERY, "updateTime:[1900-01-01T00:00:00Z TO 2020-05-30T00:00:00Z}")
                .set(GroupParams.GROUP_QUERY, new String[]{"updateTime:[1900-01-01T00:00:00Z TO " + "2021-04-01T00:00:00Z" + "}"
                        , "updateTime:[" + "2021-04-01T00:00:00Z" + " TO 2999-12-31T23:59:59Z]"})
                .set(GroupParams.GROUP_LIMIT, 10)
                .set(CommonParams.SORT, "random_" + randomNum + " asc")
        //.set(GroupParams.GROUP_SORT, "createTime asc")
        ;
        try {
            QueryResponse bcoin_withdraw = solrClient.query("shortVideo", solrQuery);
            System.out.println(bcoin_withdraw);
        }catch (Exception e){
            e.printStackTrace();
        }
/*

        redisTemplate2.opsForValue().set("key2","2库value:"+new Date());
        System.out.println(redisTemplate2.opsForValue().get("key2"));

        redisTemplate2.opsForValue().set("redisTemplate的key2","2库value:"+new Date());
        System.out.println(redisTemplate2.opsForValue().get("redisTemplate的key2"));




        redisTemplate.opsForValue().set("key0","0value:"+new Date());
        System.out.println(redisTemplate.opsForValue().get("key0"));

        redisTemplate.opsForValue().set("redisTemplate的key0","0库value:"+new Date());
        System.out.println(redisTemplate.opsForValue().get("redisTemplate的key0"));

*/


        return null;
    }
    @DS("slave_1")
    @Override
    public List<UserBcoinWithdrawModel> getTestData() {
        return userBcoinWithdrawMapper.selectList(null);
    }
}
