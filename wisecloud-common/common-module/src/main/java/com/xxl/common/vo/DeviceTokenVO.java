package com.xxl.common.vo;

import com.xxl.common.util.DateUtil;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
@Builder
public class DeviceTokenVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	private Long id;
	/**
	 * 用户ID
	 */
	@NotEmpty
	private String userId;
	/**
	 * 应用ID
	 */
	@NotEmpty
	private String appId;
	/**
	 * 设备token
	 */
	@NotEmpty
	private String deviceToken;
	/**
	 * 状态0禁言1启用，默认启用
	 */
	private Integer state=1;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;

}
