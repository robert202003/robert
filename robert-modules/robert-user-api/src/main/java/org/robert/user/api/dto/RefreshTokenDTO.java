package org.robert.user.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RefreshTokenDTO {

    @NotBlank(message = "参数错误")
    private String client_secret;

    @NotBlank(message = "参数错误")
    private String client_id;

    @NotBlank(message = "参数错误")
    private String refresh_token;

    private String grant_type = "refresh_token";

}
