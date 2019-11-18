package com.fd.service;

import com.fd.bo.ClubSiteBo;
import com.fd.pojo.ClubSite;

import java.util.List;

public interface ClubSiteService {
    List<ClubSite> findClubSiteByClubId(Integer clubId);

    Integer findCountByClubId(Integer clubId);

    void addAddress(ClubSiteBo clubSiteBo);
}
