package com.fd.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Config {
    private String id;
    private String code;
    private String content;
    private String extra;
    private Date createTime;
    private Date updateTime;
}
