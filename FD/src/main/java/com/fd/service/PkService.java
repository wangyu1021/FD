package com.fd.service;

import com.fd.bo.PkBo;
import com.fd.bo.PkConditionBo;
import com.fd.bo.StateBo;
import com.fd.pojo.Pk;
import com.fd.pojo.PkAudit;
import com.fd.pojo.PkReject;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface PkService {
    List<Pk> findAllPk();

    List<PkAudit> findAllPkAudit();

    Pk findPkById(String id);

    void updatePkStatus(StateBo stateBo);

    void updatePkAuditStatus(StateBo stateBo);

    void addVictoryTeam(String id, String victoryTeam);

    void addReason(String auditId, String reason);

    void addPk(PkBo pkBo);


    PkReject findRejectByAuditId(String auditId);

    List<Pk> findPkByCondition(PkConditionBo conditionBo);

    Integer findTodayPk() ;

    Integer findLastDayPk();
}
