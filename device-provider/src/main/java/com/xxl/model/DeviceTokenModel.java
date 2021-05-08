package com.xxl.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备token表
 * 
 * @author executor
 * @email 2605766001@qq.com
 * @date 2020-10-20 13:35:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_device_token")
public class DeviceTokenModel implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id",type = IdType.ASSIGN_ID)
	private Long id;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 应用ID
	 */
	private String appId;
	/**
	 * 设备token
	 */
	private String deviceToken;
	/**
	 * 状态0禁言1启用，默认启用
	 */
	private Integer state;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;

}
