package com.fd.service;

import com.fd.bo.ClubBo;
import com.fd.bo.ClubConditionBo;
import com.fd.bo.StatusBo;
import com.fd.pojo.Club;
import com.fd.pojo.ClubSite;

import java.util.List;

public interface ClubService {
    List<Club> findAllClub();

    void addClub(ClubBo clubBo);

    List<Club> findClubByCondition(ClubConditionBo clubConditionBo);

    Club findClubById(Integer id);

    void updateClubById(ClubBo clubBo);

    void deleteClubById(Integer id);

    void updateStatus(StatusBo statusBo);

    void updateSiteNum(Integer clubId,Integer num);

    void updateColonelNum(Integer clubId, int size);

    String findStauts(String id);

    List<ClubSite> findClubSiteById(String id);

    Integer findLastClubNum();

    Integer findTodayClubNum();
}
