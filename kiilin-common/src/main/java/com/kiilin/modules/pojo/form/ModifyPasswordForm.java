package com.kiilin.modules.pojo.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 修改密码的表单
 *
 * @author wagk
 */
@Data
public class ModifyPasswordForm {

    private String userId;

    @NotBlank(message = "原密码不能为空")
    private String password;

    @NotBlank(message = "新密码不能为空")
    private String newPassword;


}
