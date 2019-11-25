package com.fd.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Operation {
    private String name;
    private String ip;
    private String operation;
    private Date createTime;
}
