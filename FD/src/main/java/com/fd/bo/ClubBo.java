package com.fd.bo;

import com.fd.pojo.ClubSite;
import lombok.Data;

import java.util.List;

@Data
public class ClubBo {
    private Integer id;
    private String name;
    private String managerName;
    private String managerPhone;
    private String area;
    private String address;
    private Integer motionType;
    private Integer capacity;
    //private List<ClubSite> clubSites;
}
