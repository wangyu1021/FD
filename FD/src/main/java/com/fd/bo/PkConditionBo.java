package com.fd.bo;

import lombok.Data;

import java.util.Date;

@Data
public class PkConditionBo {
    private Integer status;
    private String address;
    private Integer motionType;
    private Date timer;
    private String initiatorName;
    private String groupName;
    private String victoryTeam;
}
