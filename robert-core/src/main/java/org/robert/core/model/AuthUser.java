package org.robert.core.model;

import lombok.Data;

@Data
public class AuthUser {

    private Long userId;

    private String userName;

    private String app_id;

    private String client_secret;

    private String token;

    private String userType;

    private Long orgId;

}
