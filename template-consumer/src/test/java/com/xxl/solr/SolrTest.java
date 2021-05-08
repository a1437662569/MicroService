package com.xxl.solr;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.*;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.GroupParams;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.beans.BeanMap;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class SolrTest {
    private final static String SOLR_URL = "http://192.168.1.124:8984/solr/shortVideo/";
    private static final Logger logger = LogManager.getLogger(SolrTest.class);

    public static HttpSolrClient createSolrServer() {
        HttpSolrClient solr = null;
        solr = new HttpSolrClient.Builder(SOLR_URL).build();
        return solr;
    }

    @Test
    public void tes1t() {
        List<String> zones = new ArrayList<>();

        for (String i : TimeZone.getAvailableIDs()) {
            if (i.startsWith("Canada")) {
                zones.add(i);
            }
        }
        System.out.println(zones);
        String[] tzIds = TimeZone.getAvailableIDs();
        String al = "";
        for (String timeZoneId : tzIds) {
            if (timeZoneId.startsWith("Brazil")) {
               al = timeZoneId;
            }
        }
        System.out.println(al);

        System.out.println(TimeZone.getTimeZone(al));

        // 当前系统默认时区的时间：
        Calendar calendar = new GregorianCalendar();
        System.out.print("时区：" + calendar.getTimeZone().getID() + "  ");
        System.out.println("时间：" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
        // 美国洛杉矶时区

        // 时区转换
        Calendar calendar1 = StringUtils.isEmpty(al) ? Calendar.getInstance() : Calendar.getInstance(TimeZone.getTimeZone(al));

        System.out.print("时区：" + calendar1.getTimeZone().getID() + "  ");
        System.out.println("时间：" + calendar1.get(Calendar.HOUR_OF_DAY) + ":" + calendar1.get(Calendar.MINUTE));

        System.out.println(calendar1.get(calendar1.DAY_OF_WEEK));

    }

    @Test
    public void addDocs() {
        BigDecimal bigDecimal0 = new BigDecimal("0");
        Date now = new Date();

        UserBcoinModel userBcoinModel = UserBcoinModel.builder()
                .userId("userId")
                .cumulativeWithdrawal(bigDecimal0)
                .createTime(now)
                .updateTime(now)
                .avaliableCoin(bigDecimal0)
                .cumulativeCoinConsumption(bigDecimal0)
                .totalIncrease(110)
                .freezeCoin(new BigDecimal("126.32"))
                .cumulativeRebate(bigDecimal0)
                .lowerCumulativeConsumption(bigDecimal0)
                .cumulativeConsumption(bigDecimal0)
                .status(1)
                .id(26170)
                .build();

        BeanMap beanMap = BeanMap.create(userBcoinModel);
        Map<String, Object> newMap = ParamUtil.beanMap2StringMap(beanMap, null);

        String a = "{\"cumulativeWithdrawal\":1335,\"createTime\":\"2020-07-28T19:46:31.080Z\",\"avaliableCoin\":123.1,\"cumulativeCoinConsumption\":0,\"updateTime\":\"2020-07-28T19:46:31.080Z\",\"userId\":\"22223950\",\"totalIncrease\":0,\"id\":26169,\"freezeCoin\":0,\"cumulativeRebate\":0,\"lowerCumulativeConsumption\":0,\"cumulativeConsumption\":0,\"status\":1}";
        Map map = JSONObject.parseObject(a);
        List<Map<String, Object>> docsList = new ArrayList<>();
        docsList.add(newMap);
        HttpSolrClient solr = null;
        try {
            solr = createSolrServer();
            List<SolrInputDocument> docs = docsList.stream().map(itemDoc -> {
                SolrInputDocument document = new SolrInputDocument();
                itemDoc.keySet().forEach(k -> document.addField(k, itemDoc.get(k)));
                return document;
            }).collect(Collectors.toList());
            solr.add(docs);
            UpdateResponse commit = solr.commit();
            logger.info("更新文档,状态:{},耗时:{},文档数:{}", commit.getStatus(), commit.getQTime(), docsList.size());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != solr) solr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void queryDocs() {
        HttpSolrClient solr = null;
        try {
            solr = createSolrServer();
            SolrQuery solrQuery = new SolrQuery();
            solrQuery.set("q", "*:*")
                    .set(CommonParams.WT, "json")
                    .set(CommonParams.ROWS, 5)
                    .set(CommonParams.START, 0);

            QueryResponse query = solr.query(solrQuery);

            SolrDocumentList list = query.getResults();
            Class clazz = UserBcoinModel.class;

        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
        } finally {
            try {
                if (null != solr) solr.close();
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("HttpSolrClient销毁异常," + e.getMessage());
            }
        }
    }

    @Test
    public void queryGroupDocs() {
        HttpSolrClient solr = null;
        try {
            solr = createSolrServer();
            SolrQuery solrQuery = new SolrQuery();

            // solrQuery.set("q","inviterUserId:1068174 OR inviterUserId:1068176 OR inviterUserId:null");
            /*solrQuery.set(CommonParams.WT, "json")
                    .set(CommonParams.Q, "*:*")
                    .set(GroupParams.GROUP, true)
                    .set(GroupParams.GROUP_FIELD, "inviterUserId")
                    .set(GroupParams.GROUP_LIMIT, 5)
                    .set(GroupParams.GROUP_SORT, "createTime asc")
                    .set(CommonParams.ROWS, 5)
                    .set(CommonParams.START, 0);*/
            String lessThan = "updateTime:[2000-05-30T06:06:11Z TO 2020-04-30T06:06:11Z]";
            String moreThan = "updateTime:[2020-04-30T06:06:11Z TO 2029-04-30T06:06:11Z]";
            String [] groupCondition = new String[]{lessThan,moreThan};
            solrQuery.set(CommonParams.WT, "json")
                    .set(CommonParams.Q, "programType:cut")
                    .set(GroupParams.GROUP, true)
                    .set(GroupParams.GROUP_FIELD, "updateTime")
                    .set(GroupParams.GROUP_QUERY, groupCondition)
                    //.set(GroupParams.GROUP_QUERY, lessThan)
                    //.set(GroupParams.GROUP_QUERY, moreThan)
                    .set(GroupParams.GROUP_LIMIT, 5)
                    .set(CommonParams.ROWS, 5)
                    .set(CommonParams.START, 0);

            Class clazz = UserBcoinModel.class;
            QueryResponse query = solr.query(solrQuery);
            Map<String, List> all = new HashMap<>();
            if (query != null) {

                GroupResponse groupResponse = query.getGroupResponse();
                List<GroupCommand> groupCommandList = groupResponse.getValues();

                for (GroupCommand groupCommand : groupCommandList) {
                    logger.info("GroupCommand Name:{},Num of Groups Found:{},Num of documents Found:{}",
                            groupCommand.getName(), groupCommand.getNGroups(), groupCommand.getMatches()
                    );
                    System.out.println(groupCommand.getName());
                    List<Group> groups = groupCommand.getValues();

                    if("updateTime".equals(groupCommand.getName()))
                        continue;
                    for (Group group : groups) {
                        SolrDocumentList solrDocumentList = group.getResult();
                        // solrDocumentList.get(index)
                        logger.info("group value:{},Num of Documents in this group:{},start:{},Max score:{}",
                                group.getGroupValue(), solrDocumentList.getNumFound(), solrDocumentList.getStart(), solrDocumentList.getMaxScore()
                        );
                        List<Object> collect = solrDocumentList.stream().map(itemDoc -> {
                            try {
                                Object obj = clazz.newInstance();
                                itemDoc.getFieldNames().forEach(itemKey -> {
                                    try {
                                        Object fieldValue = itemDoc.getFieldValue(itemKey);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                });
                                return obj;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return null;
                        }).collect(Collectors.toList());
                        all.put(group.getGroupValue(), collect);
                    }

                }
            }
            System.out.println(JSON.toJSON(all));
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
        } finally {
            try {
                if (null != solr) solr.close();
            } catch (Exception e) {
                
                logger.error("HttpSolrClient销毁异常," + e.getMessage());
            }
        }
    }

    @Test
    public void delDocs() throws IOException, SolrServerException {
        HttpSolrClient solrServer = createSolrServer();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(String.valueOf(i + 1));
        }
        solrServer.deleteById(list);
        solrServer.commit();
    }

    /*  public static void main(String[] args) {
          List list = new ArrayList<>();
          for (int i = 0; i < 10; i++) {
              Map map = new HashMap();
              map.put("id",i+1);
              map.put("userId",i+1+ UUID.randomUUID().toString().substring(0,5));
              list.add(map);
          }
          //addDocs(list);
          Map map = new HashMap();
          //map.put("q","id:[113 TO 115]");
          map.put("sort","id asc");
          List list1 = queryDocs(map, UserBcoinModel.class);
          System.out.println(list1);

      }*/
    @Test
    public void test() {
        List<String> list = new ArrayList<String>();
        list.add("10");
        list.add("13");
        list.add("12");

        String upRelations = list.stream().map(item -> StringUtils.leftPad(item.trim(), 10, "0")).collect(Collectors.joining(","));
        System.out.println(upRelations);
    }

    @Test
    public void test1(){
        System.out.println(Objects.nonNull(null));
        System.out.println(Objects.equals("1",1));
    }
}
