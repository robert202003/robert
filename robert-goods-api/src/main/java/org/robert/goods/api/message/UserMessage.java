package org.robert.goods.api.message;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private String userName;
}
