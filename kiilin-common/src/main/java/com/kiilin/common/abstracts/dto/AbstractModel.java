package com.kiilin.common.abstracts.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * 实体类的抽象
 *
 * @author wangkai
 */
@Data
public class AbstractModel implements Serializable {

    /**
     * id-uuid
     */
    @TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id-uuid")
    @TableField(value = "id", fill = FieldFill.INSERT)
    private String id;


    /**
     * 创建者
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建者")
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 修改者
     */
    @TableField(value = "update_by", fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "修改者")
    private String updateBy;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    /**
     * 删除标识：0-未删除，1-删除
     */
    @TableLogic
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "删除标识")
    private Boolean delFlag;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;


//    /**
//     * 页数
//     */
//    @JsonIgnore
//    @JSONField(serialize = false)
//    @TableField(exist = false)
//    @ApiModelProperty(value = "页数")
//    private Integer page = 1;
//
//    /**
//     * 行数
//     */
//    @JsonIgnore
//    @JSONField(serialize = false)
//    @TableField(exist = false)
//    @ApiModelProperty(value = "行数")
//    private Integer rows = 10;


    /**
     * 将对象处理为json
     *
     * @return
     */
    public String toJson() {
        return JSON.toJSONString(this, false);
    }

    /**
     * 将json字符串处理为对象
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> T toObject(String json, Class<T> cls) {
        return JSON.parseObject(json, cls);
    }

}
