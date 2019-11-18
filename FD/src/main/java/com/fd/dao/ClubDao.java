package com.fd.dao;

import com.fd.bo.ClubBo;
import com.fd.bo.ClubConditionBo;
import com.fd.bo.StatusBo;
import com.fd.pojo.Club;
import com.fd.pojo.ClubSite;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClubDao {
    List<Club> findAllClub();

    void addClub(Club club);

    List<Club> findClubByCondition(ClubConditionBo clubConditionBo);

    Club findClubById(Integer id);

    void updateClubById(ClubBo clubBo);

    void deleteClubById(Integer id);

    void updateStatus(StatusBo statusBo);

    void updateSiteNum(@Param("clubId")Integer clubId, @Param("num") Integer num);

    void updateColonelNum(@Param("clubId") Integer clubId,@Param("num") int num);

    String findStatus(String id);

    List<ClubSite> findClubSiteById(String id);
}
