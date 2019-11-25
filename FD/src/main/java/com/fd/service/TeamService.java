package com.fd.service;

import com.fd.bo.TeamBo;
import com.fd.bo.TeamConditionBo;
import com.fd.pojo.Reject;
import com.fd.pojo.Team;
import com.fd.pojo.TeamAudit;

import java.util.List;

public interface TeamService {
    List<Team> findAllTeam();

    List<TeamAudit> findAllTeamAudit();

    Reject findRejectByAuditId(Integer id);

    List<Team> findTeamByCondition(TeamConditionBo teamConditionBo);

    Team findTeamById(String id);

    void updateStatus(String id, Integer status);

    void addTeam(TeamBo teamBo);

    void updateTeamAuditStatus(Integer id, Integer status);

    void addReject(Integer auditId, String reason);

    Integer findLastNum();

    Integer findTodayNum();
}
