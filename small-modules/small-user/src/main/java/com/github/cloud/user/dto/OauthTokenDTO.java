package com.github.cloud.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class OauthTokenDTO {

    @NotBlank(message = "{USER_AA}")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "参数错误")
    private String client_secret;

    @NotBlank(message = "参数错误")
    private String client_id;

    private String grant_type = "password";

    private String scope;
}
