package com.kiilin.modules.pojo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kiilin.common.abstracts.dto.AbstractModel;
import com.kiilin.modules.pojo.enums.dict.SexEnum;
import com.kiilin.modules.pojo.enums.dict.UserStatusEnum;
import com.kiilin.modules.pojo.enums.dict.UserTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户表
 *
 * @author wagk
 * @since 2018-07-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName("sys_user")
@ApiModel("用户表")
public class SysUser extends AbstractModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @TableField("username")
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 登录名
     */
    @TableField("login_name")
    @ApiModelProperty(value = "登录名")
    private String loginName;

    /**
     * 加密盐
     */
    @TableField("salt")
    @ApiModelProperty(value = "加密盐")
    private String salt;

    /**
     * 登录密码
     */
    @TableField("password")
    @JSONField(serialize = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ApiModelProperty(value = "登录密码")
    private String password;

    /**
     * 电子邮箱
     */
    @TableField("email")
    @ApiModelProperty(value = "电子邮箱")
    private String email;

    /**
     * 手机号码
     */
    @TableField("mobile")
    @ApiModelProperty(value = "手机号码")
    private String mobile;

    /**
     * 用户性别：0-女 1-男
     */
    @TableField("sex")
    @ApiModelProperty(value = "用户性别：0-女 1-男")
    private SexEnum sex;

    /**
     * 头像路径
     */
    @TableField("photo")
    @ApiModelProperty(value = "头像路径")
    private String photo;

    /**
     * 个性签名
     */
    @TableField("sign")
    @ApiModelProperty(value = "个性签名")
    private String sign;

    /**
     * 绑定的微信号
     */
    @TableField("wx_openid")
    @ApiModelProperty(value = "绑定的微信号")
    private String wxOpenid;

    /**
     * 用户类型
     */
    @TableField("user_type")
    @ApiModelProperty(value = "用户类型")
    private UserTypeEnum userType;

    /**
     * 最后登陆IP
     */
    @TableField("last_login_ip")
    @ApiModelProperty(value = "最后登陆IP")
    private String lastLoginIp;

    /**
     * 最后登陆时间
     */
    @TableField("last_login_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后登陆时间")
    private Date lastLoginTime;

    /**
     * 状态
     */
    @TableField("status")
    @ApiModelProperty(value = "状态")
    private UserStatusEnum status;

    /**
     * 所属部门
     */
    @TableField("dept_id")
    @ApiModelProperty(value = "所属部门")
    private String deptId;



}
