package com.fd.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class PkAudit {
    private String id;
    /**
     * 发起战队
     */
    private String initiatorName;
    /**
     * 发起战队编号
     */
    private String initiatorId;
    /**
     * 被挑战战队
     */
    private String groupName;
    /**
     * 被挑战战队编号
     */
    private String groupId;
    /**
     * 运动类型
     */
    private Integer motionType;
    /**
     * pk地址
     */
    private String address;
    /**
     * pk时间
     */
    private Date timer;
    /**
     * 发起时间
     */
    private Date createTime;
    /**
     * 发起队长
     */
    private String initiatorCaptain;
    /**
     * 状态码
     */
    private Integer status;
}
