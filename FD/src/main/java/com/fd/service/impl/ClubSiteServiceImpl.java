package com.fd.service.impl;

import com.fd.bo.ClubSiteBo;
import com.fd.dao.ClubSiteDao;
import com.fd.pojo.ClubSite;
import com.fd.service.ClubService;
import com.fd.service.ClubSiteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClubSiteServiceImpl implements ClubSiteService {
    @Resource
    private ClubSiteDao clubSiteDao;

    @Resource
    private ClubService clubService;

    public List<ClubSite> findClubSiteByClubId(Integer clubId) {
        return clubSiteDao.findClubSiteByClubId(clubId);
    }

    public Integer findCountByClubId(Integer clubId) {
        return clubSiteDao.findCountByClubId(clubId);
    }

    public void addAddress(ClubSiteBo clubSiteBo) {
        clubSiteDao.addAddress(clubSiteBo);
        Integer count=clubSiteDao.findCountByClubId(clubSiteBo.getClubId());
        clubService.updateSiteNum(clubSiteBo.getClubId(),count);
    }
}
