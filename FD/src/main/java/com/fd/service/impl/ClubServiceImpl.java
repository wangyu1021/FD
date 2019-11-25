package com.fd.service.impl;

import com.fd.bo.ClubBo;
import com.fd.bo.ClubConditionBo;
import com.fd.bo.StatusBo;
import com.fd.dao.ClubDao;
import com.fd.pojo.Club;
import com.fd.pojo.ClubSite;
import com.fd.service.ClubService;
import com.fd.service.ClubSiteService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ClubServiceImpl  implements ClubService {
    @Resource
    private ClubDao clubDao;

    @Resource
    private ClubSiteService clubSiteService;

    public List<Club> findAllClub() {
        List<Club> list=clubDao.findAllClub();
        return list;
    }

    public void addClub(ClubBo clubBo) {
        Club club=new Club();
        BeanUtils.copyProperties(clubBo,club);
        club.setCreateTime(new Date());
        club.setTimeId(0);
        club.setColonelNum(0);
        club.setSiteNum(0);
        clubDao.addClub(club);
    }

    public List<Club> findClubByCondition(ClubConditionBo clubConditionBo) {
        List<Club> list=clubDao.findClubByCondition(clubConditionBo);
        return list;
    }

    public Club findClubById(Integer id) {
        return clubDao.findClubById(id);
    }

    public void updateClubById(ClubBo clubBo) {
        clubDao.updateClubById(clubBo);
    }

    public void deleteClubById(Integer id) {
        clubDao.deleteClubById(id);
    }

    public void updateStatus(StatusBo statusBo) {
        clubDao.updateStatus(statusBo);
    }
    public void updateSiteNum(Integer clubId,Integer num){
        clubDao.updateSiteNum(clubId,num);
    }

    public void updateColonelNum(Integer clubId, int size) {
        clubDao.updateColonelNum(clubId,size);
    }

    public String findStauts(String id) {
        return clubDao.findStatus(id);
    }

    public List<ClubSite> findClubSiteById(String id) {
        return clubDao.findClubSiteById(id);
    }

    public Integer findLastClubNum() {
        return clubDao.findNum()-clubDao.findTodayClubNum();
    }

    public Integer findTodayClubNum() {
        return clubDao.findTodayClubNum();
    }
}
