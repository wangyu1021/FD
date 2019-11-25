package com.fd.dao;

import com.fd.bo.TeamConditionBo;
import com.fd.pojo.Reject;
import com.fd.pojo.Team;
import com.fd.pojo.TeamAudit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeamDao {
    List<Team> findAllTeam();

    List<TeamAudit> findAllTeamAudit();

    Reject findRejectByAuditId(Integer id);

    List<Team> findTeamByCondition(TeamConditionBo teamConditionBo);

    Team findTeamById(String id);

    void updateStatus(@Param("id") String id, @Param("status") Integer status);

    void addTeam(Team team);

    void updateTeamAuditStatus(@Param("id") Integer id, @Param("status") Integer status);

    void addReject(@Param("auditId") Integer auditId,@Param("reason") String reason);

    Integer findTodayNum();
    Integer findCount();
}
