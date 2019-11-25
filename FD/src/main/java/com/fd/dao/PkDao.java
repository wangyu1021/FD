package com.fd.dao;

import com.fd.bo.PkBo;
import com.fd.bo.StateBo;
import com.fd.pojo.Pk;
import com.fd.pojo.PkAudit;
import com.fd.pojo.PkReject;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface PkDao {
    List<Pk> findAllPk();

    List<PkAudit> findAllPkAudit();

    Pk findPkById(String id);

    void updatePkStatus(StateBo stateBo);

    void updatePkAuditStatus(StateBo stateBo);

    void addVictoryTeam(@Param("id") String id,@Param("victoryTeam") String victoryTeam);

    void addReason(@Param("auditId") String auditId,@Param("reason") String reason);

    void addPk(PkBo pkBo);

    List<Pk> findPkByCondition(Pk pk);

    PkReject findRejectByAuditId(String auditId);

    Integer findTodayPk();

    Integer findCount();
}
