package com.fd.pojo;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Club {
    /**
     * 编号
     */
    private Integer id;
    /**
     * 俱乐部名称
     */
    private String name;
    /**
     * 店长名字
     */
    private String managerName;
    /**
     * 店长手机号
     */
    private String managerPhone;
    /**
     * 场地数量
     */
    private Integer siteNum;
    /**
     * 团长数量
     */
    private Integer colonelNum;
    /**
     * 所在地区
     */
    private String area;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 使用时间
     */
    private Integer timeId;
    /**
     * 运动类型
     */
    private Integer motionType;
    /**
     * 场地容量
     */
    private Integer capacity;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 状态码
     */
    private Integer status;

}
