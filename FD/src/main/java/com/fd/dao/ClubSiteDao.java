package com.fd.dao;

import com.fd.bo.ClubSiteBo;
import com.fd.pojo.ClubSite;

import java.util.List;

public interface ClubSiteDao {
    List<ClubSite> findClubSiteByClubId(Integer clubId);

    Integer findCountByClubId(Integer clubId);

    void addAddress(ClubSiteBo clubSiteBo);
}
