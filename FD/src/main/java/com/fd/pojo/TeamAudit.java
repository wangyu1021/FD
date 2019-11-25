package com.fd.pojo;

import lombok.Data;

@Data
public class TeamAudit {
    private Integer id;
    /**
     * 战队名字
     */
    private String name;
    /**
     * 战队logo
     */
    private String logo;
    /**
     * 战队队长
     */
    private String captain;
    /**
     * 队长手机号
     */
    private String captainPhone;
    /**
     * 描述
     */
    private String description;
    /**
     * 所在地区
     */
    private String address;
    /**
     * 运动类型
     */
    private Integer motionType;
    /**
     * 状态码
     */
    private Integer status;
}
