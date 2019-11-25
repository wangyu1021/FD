package com.fd.bo;

import lombok.Data;

import java.util.Date;

@Data
public class ConfigBo {
    private String id;
    private String code;
    private String content;
    private String extra;
    private Date updateTime;
}
