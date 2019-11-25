package com.fd.service.impl;

import com.fd.bo.PkBo;
import com.fd.bo.PkConditionBo;
import com.fd.bo.StateBo;
import com.fd.dao.PkDao;
import com.fd.pojo.Pk;
import com.fd.pojo.PkAudit;
import com.fd.pojo.PkReject;
import com.fd.service.PkService;
import com.fd.util.SID;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

@Service
public class PkServiceImpl implements PkService {
    @Resource
    private PkDao pkDao;

    public List<Pk> findAllPk() {
        return pkDao.findAllPk();
    }

    public List<PkAudit> findAllPkAudit() {
        return pkDao.findAllPkAudit();
    }

    public Pk findPkById(String id) {
        return pkDao.findPkById(id);
    }

    public void updatePkStatus(StateBo stateBo) {
        pkDao.updatePkStatus(stateBo);
    }

    public void updatePkAuditStatus(StateBo stateBo) {
        pkDao.updatePkAuditStatus(stateBo);
    }

    public void addVictoryTeam(String id, String victoryTeam) {
        pkDao.addVictoryTeam(id, victoryTeam);
    }

    public void addReason(String auditId, String reason) {
        pkDao.addReason(auditId,reason);
    }

    public void addPk(PkBo pkBo) {
        pkBo.setId(String.valueOf(SID.getOrderNo()));
        pkDao.addPk(pkBo);
    }



    public PkReject findRejectByAuditId(String auditId) {
        return pkDao.findRejectByAuditId(auditId);
    }

    public List<Pk> findPkByCondition(PkConditionBo conditionBo) {
        Pk pk =new Pk();
        BeanUtils.copyProperties(conditionBo,pk);
        return pkDao.findPkByCondition(pk);
    }

    public Integer findTodayPk() {
        return pkDao.findTodayPk();
    }

    public Integer findLastDayPk() {
        Integer num=pkDao.findCount()-pkDao.findTodayPk();
        return num;
    }
}
