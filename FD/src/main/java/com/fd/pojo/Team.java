package com.fd.pojo;

import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;

@Data
public class Team {
    private String id;
    /**
     * 战队logo
     */
    private String logo;
    /**
     * 战队名
     */
    private String name;
    /**
     * 队长
     */
    private String captain;
    /**
     * 战队所在地区
     */
    private String address;
    /**
     * 运动类型
     */
    private Integer motionType;
    /**
     * 队长手机号
     */
    private String captainPhone;
    /**
     * 队员人数
     */
    private Integer playerNum;
    /**
     * 胜场
     */
    private Integer victory;
    /**
     * 败场
     */
    private Integer lose;
    /**
     * 成长值
     */
    private Integer groupNum;
    /**
     * pk次数
     */
    private Integer pkNum;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 状态
     */
    private Integer status;
}
