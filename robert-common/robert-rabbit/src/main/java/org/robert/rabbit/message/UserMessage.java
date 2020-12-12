package org.robert.rabbit.message;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserMessage implements Serializable {

    private Integer userId;

    private String userName;
}
