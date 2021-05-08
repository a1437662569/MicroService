package com.xxl.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_user_bcoin_withdraw")
@SolrDocument(collection = "bcoin_withdraw")
public class UserBcoinWithdrawModel implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    @Id
    @Field
    private Integer id;
    /**
     * 发起提现操作的用户ID
     */
    @Field
    private String userId;
    /**
     * 编码uuid
     */
    private String code="1a";
    /**
     * 审核操作人
     */
    @Field
    private String operator;
    /**
     * 提现状态：
     * 0：发起提现申请
     * 1：审核中
     * 2：审核不通过
     * 3：审核通过，处理提现
     * 4：提现成功
     * 5：提现失败
     * 9：提现异常
     */
    private Integer type;

    /**
     * 提现BCoin数量
     */
    private BigDecimal withdrawQuantity;
    /**
     * 汇率，BCoin:兑换币种USD/BRL
     */
    private String exchangeRate;
    /**
     * 提款账号
     */
    private String account;
    /**
     * 提现金额
     */
    private BigDecimal exchangedAmount;
    /**
     * 提现币种,USD/BRL
     */
    private String currency;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 状态：0无效，1有效
     */
    private Integer status;

}