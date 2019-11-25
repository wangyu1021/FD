package com.fd.service.impl;

import com.fd.bo.TeamBo;
import com.fd.bo.TeamConditionBo;
import com.fd.dao.TeamDao;
import com.fd.pojo.Reject;
import com.fd.pojo.Team;
import com.fd.pojo.TeamAudit;
import com.fd.service.TeamService;
import com.fd.util.SID;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {
    @Resource
    private TeamDao teamDao;

    public List<Team> findAllTeam() {
        return teamDao.findAllTeam();
    }

    public List<TeamAudit> findAllTeamAudit() {
        return teamDao.findAllTeamAudit();
    }

    public Reject findRejectByAuditId(Integer id) {
        return teamDao.findRejectByAuditId(id);
    }

    public List<Team> findTeamByCondition(TeamConditionBo teamConditionBo) {
        return teamDao.findTeamByCondition(teamConditionBo);
    }

    public Team findTeamById(String id) {
        return teamDao.findTeamById(id);
    }

    public void updateStatus(String id, Integer status) {
        teamDao.updateStatus(id,status);
    }

    public void addTeam(TeamBo teamBo) {
        Team team = new Team();
        BeanUtils.copyProperties(teamBo,team);
        team.setId(SID.getOrderNo()+"");
        team.setCreateTime(new Date());
        teamDao.addTeam(team);
    }

    public void updateTeamAuditStatus(Integer id, Integer status) {
        teamDao.updateTeamAuditStatus(id,status);
    }

    public void addReject(Integer auditId, String reason) {
        teamDao.addReject(auditId,reason);
    }

    public Integer findLastNum() {
        return teamDao.findCount()-teamDao.findTodayNum();
    }

    public Integer findTodayNum() {
        return teamDao.findTodayNum();
    }
}
