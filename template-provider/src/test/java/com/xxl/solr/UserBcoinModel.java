package com.xxl.solr;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户资金表
 * 
 * @author executor
 * @email 2605766001@qq.com
 * @date 2020-07-23 14:03:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_user_bcoin")
public class UserBcoinModel implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id",type = IdType.AUTO)
	private Integer id;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 上级用户ID
	 */
	private String inviterUserId;
	/**
	 * 邀请码
	 */
	private String inviteCode;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 总推广人数
	 */
	private Integer totalIncrease;
	/**
	 * 累计提现金额
	 */
	private BigDecimal cumulativeWithdrawal;
	/**
	 * 下级累计消费金额（不含BCoin）
	 */
	private BigDecimal lowerCumulativeConsumption;
	/**
	 * 可用BCoin余额
	 */
	private BigDecimal avaliableCoin;
	/**
	 * 冻结金额
	 */
	private BigDecimal freezeCoin;
	/**
	 * 累计获得返利
	 */
	private BigDecimal cumulativeRebate;
	/**
	 * 累计实际货币消费
	 */
	private BigDecimal cumulativeConsumption;
	/**
	 * 累计bcoin消费
	 */
	private BigDecimal cumulativeCoinConsumption;
	/**
	 * 上级关联关系列表
左补零6位32进制
4级分隔符_
	 */
	private String relations;
	/**
	 * 0无效1有效
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 邀请时间
	 */
	private Date inviteTime;

}
