package com.xxl.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 应用表
 *
 * @author executor
 * @email 2605766001@qq.com
 * @date 2021-03-08 11:36:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_app")
public class AppDetailBO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增长
     */
    @TableId(type = IdType.AUTO)
    private Long appId;
    /**
     * 应用名
     */
    private String appName;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 应用包名称
     */
    private String appPackagename;
    /**
     * 应用拼音
     */
    private String appPinyin;
    /**
     * 应用图标
     */
    private String appIcon;
    /**
     * 应用大图
     */
    private String appLongIcon;
    /**
     * 应用分类
     */
    private Integer appType;
    /**
     * 应用分类（子类）
     */
    private Integer appTag;
    /**
     * 枚举值：0：已下架
     * 1：新增待审核
     * 2：审核已通过
     * 3：审核未通过
     * 4：已上架
     */
    private String status;
    /**
     * 费用
     */
    private Float cost;
    /**
     * apk包大小
     */
    private String appSize;
    /**
     * 最新版本号
     */
    private String latestVersion;
    /**
     * 下载次数
     */
    private Integer downloadTimes;
    /**
     * 平台类型
     */
    private String platform;
    /**
     * 应用最小sdk
     */
    private String appMinsdk;
    /**
     * 排序号（序号越大越靠前）
     */
    private Integer sortNumber;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后更新时间
     */
    private Date updateTime;
    /**
     * 策略作用范围
     */
    private Integer strategyScope;
    /**
     * 固定显示位置排序值,按类型区分
     */
    private Integer typeSort;
    /**
     * 是否为热修复版本，0为正常应用，1为热修复应用
     */
    private Integer hotFix;

    /**
     * 排行类型：
     * 1：下载排行
     * 2：最新上架
     * 3：推荐
     */
    private Integer topType;
    /**
     * 推荐星级（0-5星）
     */
    private Integer recommendStar;
    /**
     * 一级分类
     */
    private Integer supertype;
    /**
     * 二级分类
     */
    private Integer childtype;



}
