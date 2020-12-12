package org.robert.goods.api.message;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserMessage implements Serializable {

    private Integer userId;

    private String userName;
}
