package com.fd.service.impl;

import com.fd.bo.ConfigBo;
import com.fd.dao.AdminDao;
import com.fd.pojo.Config;
import com.fd.pojo.Operation;
import com.fd.service.AdminService;
import com.fd.util.SID;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminDao adminDao;

    public void addOperation(Operation operation) {
        adminDao.insertOperation(operation);
    }

    public List<Operation> findAllOperation() {
        return adminDao.findAllOperation();
    }

    public List<Config> findAllConfig() {
        return adminDao.findAllConfig();
    }

    public Config findConfigById(String id) {
        return adminDao.findConfigById(id);
    }

    public void updateConfigById(ConfigBo configBo) {
        configBo.setUpdateTime(new Date());
        adminDao.updateConfigById(configBo);
    }

    public void deleteConfigById(String id) {
        adminDao.deleteConfigById(id);
    }

    public void addConfig(ConfigBo configBo) {
        Config config=new Config();
        BeanUtils.copyProperties(configBo,config);
        config.setCreateTime(new Date());
        config.setUpdateTime(new Date());
        config.setId(String.valueOf(SID.getOrderNo()));
        adminDao.addConfig(config);
    }
}
